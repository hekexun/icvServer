package com.service.codec;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import com.common.IcvServerConst;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.common.IcvServerConst;
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
            	listbs.add((byte) IcvServerConst.MSG_DELIMITER);
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
		//从0开始后，这里要调节解析位置
		//首先bodylen
		MsgHead msgHead = new MsgHead();
		msgHead.setHeadId(Integer.parseInt(DigitUtil.byteToBinaryStr(data[0]),2));//(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 0, 1)));//分割出消息头 update by hkx
    	boolean hasSubPack = ((byte) ((data[1] >> 4) & 0x1) == 1) ? true : false;
    	msgHead.setHasSubPack(hasSubPack);
    	int encryptType = ((byte) ((data[1] >> 2) & 0x1)) == 1 ? 1 : 0;
    	msgHead.setEncryptType(encryptType);
    	String bodyLen = DigitUtil.byteToBinaryStr(data[2]);//(data[2], 1, 0) + DigitUtil.byteToBinaryStr(data[3], 7, 0);//(data[1], 1, 0) + DigitUtil.byteToBinaryStr(data[2], 7, 0);
    	msgHead.setBodyLength(Integer.parseInt(bodyLen, 2));;
    	msgHead.setTerminalPhone(new String(DigitUtil.bcdToStr(DigitUtil.sliceBytes(data, 5, 21))));
    	msgHead.setHeadSerial(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 3, 4)));
    	return msgHead;
	}
	
	//解码消息体
	private MsgBody parseMsgBodyFromBytes(byte[] data) {
		MsgBody msgBody = new MsgBody();
		msgBody.setBodyId(Integer.parseInt(DigitUtil.byteToBinaryStr(data[0]),2));//消息体的第1位表示消息id0x83
		msgBody.setBodySerial(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 2, 3)));//消息体的第2-3位表示消息的序列号
		msgBody.setResult(data[6]);
		msgBody.setBodyBytes(data);
    	return msgBody;
	}
	
	//解码基本位置包
	public LocationMsg toLocationMsg(PackageData packageData) throws UnsupportedEncodingException {
		LocationMsg locationMsg = new LocationMsg(packageData);
		LocationInfo locationInfo = new LocationInfo();
		byte[] bodybs = locationMsg.getMsgBody().getBodyBytes();
		//处理设备发送时间
		String year = DigitUtil.bcdToStr(bodybs[0]);
		String month = DigitUtil.bcdToStr(bodybs[1]);
		String day = DigitUtil.bcdToStr(bodybs[2]);
		String hour = DigitUtil.bcdToStr(bodybs[3]);
		String minute = DigitUtil.bcdToStr(bodybs[4]);
		String second = DigitUtil.bcdToStr(bodybs[5]);
		String sendDatetime = "20" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
		locationInfo.setSendDatetime(sendDatetime);
		//设置终端手机号
		locationInfo.setDevPhone(locationMsg.getMsgHead().getTerminalPhone());
		//设置终端地址
		//locationInfo.setRemoteAddress(locationMsg.getChannel().remoteAddress().toString());
		//定位状态
		locationInfo.setLocateState(DigitUtil.byteToBinaryStr(bodybs[10],0,0));//电池状态字符第一个字节的第一位;
       //经度
		int lot=DigitUtil.byte4ToInt(DigitUtil.sliceBytes(bodybs,11,14));
		locationInfo.setGpsPosX((float)lot/1000000);
		//纬度
		int lat=DigitUtil.byte4ToInt(DigitUtil.sliceBytes(bodybs,15,18));
		locationInfo.setGpsPosY((float)lat/1000000);
		//GPS速度
		locationInfo.setGpsSpeed((float) (DigitUtil.byte2ToInt(DigitUtil.sliceBytes(bodybs,19,20)))/10);
        //gps方向
		locationInfo.setGpsDirect(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(bodybs,21,22)));
		//gps里程
		locationInfo.setGpsMileage((Float.parseFloat(DigitUtil.byteToBinaryStr(bodybs[23]))/10));
		//can速度
		locationInfo.setCanSpeed((float) (DigitUtil.byte2ToInt(DigitUtil.sliceBytes(bodybs,24,25)))/10);
		//Height
		locationInfo.setHeight((float) (DigitUtil.byte2ToInt(DigitUtil.sliceBytes(bodybs,26,27)))/10);
		//drivemode驾驶模式
		locationInfo.setDrivemode(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(bodybs,28,29)));
		//处理车牌号码
        String carNumber = new String(DigitUtil.sliceBytes(bodybs, 24, 31), "GBK");

        locationMsg.setLocationInfo(locationInfo);
		return locationMsg;
	}
}
