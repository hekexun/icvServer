package com.service.codec;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.common.JT808Const;
import com.util.DigitUtil;
import com.vo.PackageData;
import com.vo.PackageData.MsgBody;
import com.vo.PackageData.MsgHead;
import com.vo.req.LocationMsg;
import com.vo.req.LocationMsg.LocationInfo;
/**
 * ClassName: MsgDecoder 
 * @Description: 消息解码器
 */
@Component
@Scope("prototype")
public class MsgDecoder {

	/**
	 * @Description: 将byte[]解码成业务对象
	 * @param bs
	 * @return PackageData  
	 */
	public PackageData bytes2PackageData(byte[] bs) {
		//先把数据包反转义一下
		List<Byte> listbs = new ArrayList<Byte>();
		for (int i = 0; i < bs.length-1 ; i++) {//之前i定义从1开始，不知道为什么。hkx628恢复到0
            //如果当前位是0x7d，判断后一位是否是0x02或0x01，如果是，则反转义
            if ((bs[i] == (byte)0x7d) && (bs[i + 1] == (byte) 0x02)) {
            	listbs.add((byte) JT808Const.MSG_DELIMITER);
                i++;
            } else if ((bs[i] == (byte) 0x7d) && (bs[i + 1] == (byte) 0x01)) {
            	listbs.add((byte) 0x7d);
                i++;
            } else {
            	listbs.add(bs[i]);
            }
		}
		byte[] newbs = new byte[listbs.size()];
        for (int i = 0; i < listbs.size(); i++) {
        	newbs[i] = listbs.get(i);
        }
		//将反转义后的byte[]转成业务对象
		PackageData pkg = new PackageData();
		MsgHead msgHead = this.parseMsgHeadFromBytes(newbs);
		pkg.setMsgHead(msgHead);
		byte[] bodybs = DigitUtil.sliceBytes(newbs, 12,12 + msgHead.getBodyLength() - 1);
		MsgBody msgBody = this.parseMsgBodyFromBytes(bodybs);
		pkg.setMsgBody(msgBody);
		return pkg;
	}
	
	//解码消息头
	private MsgHead parseMsgHeadFromBytes(byte[] data) {
		//从0开始后，这里要调节直接
		//首先bodylen
		MsgHead msgHead = new MsgHead();
		msgHead.setHeadId(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 0, 1)));
    	boolean hasSubPack = ((byte) ((data[2] >> 5) & 0x1) == 1) ? true : false;
    	msgHead.setHasSubPack(hasSubPack);
    	int encryptType = ((byte) ((data[2] >> 2) & 0x1)) == 1 ? 1 : 0;
    	msgHead.setEncryptType(encryptType);
    	String bodyLen = DigitUtil.byteToBinaryStr(data[2], 1, 0) + DigitUtil.byteToBinaryStr(data[3], 7, 0);//(data[1], 1, 0) + DigitUtil.byteToBinaryStr(data[2], 7, 0);
    	msgHead.setBodyLength(Integer.parseInt(bodyLen, 2));;
    	msgHead.setTerminalPhone(new String(DigitUtil.bcdToStr(DigitUtil.sliceBytes(data, 4, 9))));
    	msgHead.setHeadSerial(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 10, 11)));
    	return msgHead;
	}
	
	//解码消息体
	private MsgBody parseMsgBodyFromBytes(byte[] data) {
		MsgBody msgBody = new MsgBody();
		msgBody.setBodyId(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 0, 1)));
		msgBody.setBodySerial(DigitUtil.byte4ToInt(DigitUtil.sliceBytes(data, 2, 5)));
		msgBody.setResult(data[6]);
		msgBody.setBodyBytes(data);
    	return msgBody;
	}
	
	//解码基本位置包
	public LocationMsg toLocationMsg(PackageData packageData) throws UnsupportedEncodingException {
		LocationMsg locationMsg = new LocationMsg(packageData);
		LocationInfo locationInfo = new LocationInfo();
		byte[] bodybs = locationMsg.getMsgBody().getBodyBytes();
		//设置终端手机号
		locationInfo.setDevPhone(locationMsg.getMsgHead().getTerminalPhone());
		//设置终端地址
		//locationInfo.setRemoteAddress(locationMsg.getChannel().remoteAddress().toString());
		//电池组状态
		locationInfo.setBMSState(DigitUtil.byteToBinaryStr(bodybs[0],0,0));//电池状态字符第一个字节的第一位;
        //系统故障等级
        locationInfo.setErrorLevel(DigitUtil.byteToBinaryStr(bodybs[0],3,1));
        //soc
		int soc=Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[1]),2)/2;
        locationInfo.setSOC(soc);
        //电池最高单体电压编号
		int num=Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[2]),2);
		locationInfo.setMaxBatteryMonomerNumber(num);
        //电池最高单体电压
		byte[] maxVo=DigitUtil.sliceBytes(bodybs,3,4);
		double MaxBatteryMonomerVoltage=DigitUtil.byte2ToInt(maxVo)*0.00390625;
        locationInfo.setMaxBatteryMonomerVoltage(MaxBatteryMonomerVoltage);
        //电池最低单体电压编号
        locationInfo.setMinBatteryMonomerNumber(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[5]),2));
		//电池最低单体电压
		byte[] minVo=DigitUtil.sliceBytes(bodybs,6,7);
		double MinBatteryMonomerVoltage=DigitUtil.byte2ToInt(minVo)*0.00390625;
        locationInfo.setMinBatteryMonomerVoltage(MinBatteryMonomerVoltage);
        //预充电状态
		locationInfo.setPrechargeState(DigitUtil.byteToBinaryStr(bodybs[8],0,0));
		//高压连接状态
		int highVoltageConnectorsState=Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[8],1,1),2);
		locationInfo.setHighVoltageConnectorsState(highVoltageConnectorsState);
		//BMS自检状态
		locationInfo.setBmsCheckState(DigitUtil.byteToBinaryStr(bodybs[8],3,2));
		//电池SOH
		locationInfo.setSOH(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[9]),2));
		//电池组总电压
		locationInfo.setTotalVoltage(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(bodybs,10,11)));
		//电池组总电流
		locationInfo.setTotal电流(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(bodybs,12,13))-1000);
		//电池最高温度
		locationInfo.setMaxTempreture(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[14]),2)-40);
		//电池最低温度
		locationInfo.setMaxTempreture(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[15]),2)-40);
		//充电线连接状态，第17字节0bit
		locationInfo.setChargeConnectionState(DigitUtil.byteToBinaryStr(bodybs[16],0,0));
		////充电状态，第17字节1,2bit
		locationInfo.setChargeState(DigitUtil.byteToBinaryStr(bodybs[16],2,1));
		//总正接触器状态，第17字节3bit（闭合）
		locationInfo.setPositiveContactorState(DigitUtil.byteToBinaryStr(bodybs[16],3,3));
		//预充电接触器状态，第17字节4bit（闭合）
		locationInfo.setPreChargeConnectorState(DigitUtil.byteToBinaryStr(bodybs[16],4,4));
		//总负接触器状态，第17字节5bit（闭合）
		locationInfo.setNegativeContactorState(DigitUtil.byteToBinaryStr(bodybs[16],5,5));
		//充电机接触器状态，第17字节6bit（闭合）
		locationInfo.setPreChargeContactorState(DigitUtil.byteToBinaryStr(bodybs[16],6,6));
		//电池加热系统工作状态，第18字节0,1bit（工作）
		locationInfo.setHeatingSystemState(DigitUtil.byteToBinaryStr(bodybs[17],1,0));
		//电池冷却系统工作状态，第18字节2,3bit（未工作）
		locationInfo.setCoolingSystemState(DigitUtil.byteToBinaryStr(bodybs[17],3,2));
		//电池湿度状态，第18字节4,5,6,7,bit（7*10=70%）
		locationInfo.setWetnessState(DigitUtil.byteToBinaryStr(bodybs[17],7,4));
		//最大可用持续充电功率，第19字节（150-200=-50KW）
		locationInfo.setMaxContinuousChargePower(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[18]),2));
		//最大可用短时充电功率，第20字节（150-200=-50KW）
		locationInfo.setMaxChargePower(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[19]),2));
		//最大可用持续放电功率，第21字节（150KW）
		locationInfo.setMaxContinuousDischargePower(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[20]),2));
		//最大可用短时放电功率，第22字节（150KW）
		locationInfo.setMaxContinuousDischargePower(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[21]),2));
		//正极绝缘阻值，第23字节（100*10=1000）
		locationInfo.setMaxContinuousDischargePower(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[22]),2));
		//负极绝缘阻值，第24字节（100*10=1000）
		locationInfo.setMaxContinuousDischargePower(Integer.parseInt(DigitUtil.byteToBinaryStr(bodybs[23]),2));
		//绝缘故障，第25字节0,1,2bit（二级故障）
		locationInfo.setInsulationError(DigitUtil.byteToBinaryStr(bodybs[24],0,2));
		//预充电故障，第25字节4bit（正常）
		locationInfo.setPreChargeError(DigitUtil.byteToBinaryStr(bodybs[24],4,4));
		//总正接触器故障，第25字节5bit（正常）
		locationInfo.setPositiveContactorError(DigitUtil.byteToBinaryStr(bodybs[24],5,5));
		//总负接触器故障，第25字节6bit（正常）
		locationInfo.setNegtiveContactorError(DigitUtil.byteToBinaryStr(bodybs[24],6,6));
		//预充接触器故障，第25字节7bit（正常）
		locationInfo.setPreChargeConnectorError(DigitUtil.byteToBinaryStr(bodybs[24],7,7));
		//充电机接触器故障，第26字节0bit（正常）
		locationInfo.setChargeContactorError(DigitUtil.byteToBinaryStr(bodybs[25],0,0));
		//温度过低故障，第26字节1,2bit（正常）
		locationInfo.setLowTempertureError(DigitUtil.byteToBinaryStr(bodybs[25],2,1));
		//温度过高故障，第26字节3,4bit（正常）
		locationInfo.setHighTempertureError(DigitUtil.byteToBinaryStr(bodybs[25],4,3));
		//温度差异故障，第26字节5,6bit（正常）
		locationInfo.setDiffTempertureError(DigitUtil.byteToBinaryStr(bodybs[25],6,5));
		//湿度过大故障，第26字节7bit（正常）
		locationInfo.setHighWetnessError(DigitUtil.byteToBinaryStr(bodybs[25],7,7));
		//充电过流故障，第27字节0,1bit（正常）
		locationInfo.setChargeError(DigitUtil.byteToBinaryStr(bodybs[26],1,0));
		//放点过流故障，第27字节2,3bit（正常）
		locationInfo.setDischargeOvercurrentError(DigitUtil.byteToBinaryStr(bodybs[26],3,2));
		//单体过压故障，第27字节4,5bit（正常）
		locationInfo.setModuleOvervoltageError(DigitUtil.byteToBinaryStr(bodybs[26],5,4));
		//单体欠压故障，第27字节6,7bit（正常）
		locationInfo.setModuleUndervoltageError(DigitUtil.byteToBinaryStr(bodybs[26],7,6));
		//单体电压差异故障，第28字节0,1bit（正常）
		locationInfo.setModuleVoltageDiffError(DigitUtil.byteToBinaryStr(bodybs[27],1,0));
		//电池组过压故障，第28字节2,3bit（正常）
		locationInfo.setBatteryOvervoltageError(DigitUtil.byteToBinaryStr(bodybs[27],3,2));
		//电池组欠压故障，第28字节4,5bit（正常）
		locationInfo.setBatteryUndervoltageError(DigitUtil.byteToBinaryStr(bodybs[27],7,6));
		//SOC过低故障，第29字节0,1bit（正常）
		locationInfo.setUnderSocError(DigitUtil.byteToBinaryStr(bodybs[28],1,0));
		//SOC过高故障，第29字节2,3bit（正常）
		locationInfo.setOverSocError(DigitUtil.byteToBinaryStr(bodybs[28],3,2));
		//控制板电源故障，第30字节0bit（正常）
		locationInfo.setControlBoardPowerError(DigitUtil.byteToBinaryStr(bodybs[29],0,0));
		//子板CAN通讯故障，第30字节2,3bit（正常）
		locationInfo.setSubPCBCanError(DigitUtil.byteToBinaryStr(bodybs[29],3,2));
		//与VCU间CAN通讯故障，第30字节4,5bit（正常）
		locationInfo.setVCUcCanError(DigitUtil.byteToBinaryStr(bodybs[29],5,4));
		//与充电机CAN通讯故障，第30字节6,7bit（正常）
		locationInfo.setChargeCanError(DigitUtil.byteToBinaryStr(bodybs[29],7,6));
		//电池冷却系统故障，第31字节0bit（正常）
		locationInfo.setCoolingSystemError(DigitUtil.byteToBinaryStr(bodybs[30],0,0));
		//电池加热系统故障，第31字节1bit（正常）
		locationInfo.setHeatingSystemError(DigitUtil.byteToBinaryStr(bodybs[30],1,1));
		//单体电压传感器故障，第31字节2bit（正常）
		locationInfo.setModuleVoltageSenserError(DigitUtil.byteToBinaryStr(bodybs[30],2,2));
		//电流传感器故障，第31字节3bit（正常）
		locationInfo.setCurrentSenserError(DigitUtil.byteToBinaryStr(bodybs[30],3,3));
		//温度传感器故障，第31字节4bit（正常）
		locationInfo.setTempretureSenserError(DigitUtil.byteToBinaryStr(bodybs[30],4,4));
		//总电压传感器故障，第31字节5bit（正常）
		locationInfo.setVoltageSenserError(DigitUtil.byteToBinaryStr(bodybs[30],5,5));
		//湿度传感器故障，第31字节6bit（正常）
		locationInfo.setWetnessSenserError(DigitUtil.byteToBinaryStr(bodybs[30],6,6));
		//充电机故障，第31字节7bit（正常）
		locationInfo.setChargeUnitError(DigitUtil.byteToBinaryStr(bodybs[30],7,7));
		//维修开关接通状态，第32字节0bit（接通）
		locationInfo.setRepairSwitchError(DigitUtil.byteToBinaryStr(bodybs[31],0,0));
		//电池包揭盖开关，第32字节1bit（打开）
		locationInfo.setBatteryCapSwitchError(DigitUtil.byteToBinaryStr(bodybs[31],1,1));

		//处理设备发送时间
        String year = DigitUtil.bcdToStr(bodybs[18]);
        String month = DigitUtil.bcdToStr(bodybs[19]);
        String day = DigitUtil.bcdToStr(bodybs[20]);
        String hour = DigitUtil.bcdToStr(bodybs[21]);
        String minute = DigitUtil.bcdToStr(bodybs[22]);
        String second = DigitUtil.bcdToStr(bodybs[23]);
        String sendDatetime = "20" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        locationInfo.setSendDatetime(sendDatetime);
        //处理车牌号码
        String carNumber = new String(DigitUtil.sliceBytes(bodybs, 24, 31), "GBK");

        locationMsg.setLocationInfo(locationInfo);
		return locationMsg;
	}
}
