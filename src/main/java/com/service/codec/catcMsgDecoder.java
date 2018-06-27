package com.service.codec;

import com.common.JT808Const;
import com.util.DigitUtil;
import com.vo.PackageData;
import com.vo.PackageData.MsgBody;
import com.vo.PackageData.MsgHead;
import com.vo.req.ConfigMsg;
import com.vo.req.ConfigMsg.ConfigInfo;
import com.vo.req.EventMsg;
import com.vo.req.EventMsg.EventInfo;
import com.vo.req.LocationMsg;
import com.vo.req.LocationMsg.LocationInfo;
import com.vo.req.VersionMsg;
import com.vo.req.VersionMsg.VersionInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MsgDecoder 
 * @Description: 消息解码器
 */
@Component
@Scope("prototype")
public class catcMsgDecoder {

	/**
	 * @Description: 将byte[]解码成业务对象
	 * @param bs
	 * @return PackageData  
	 */
	public PackageData bytes2PackageData(byte[] bs) {
		//先把数据包反转义一下
		List<Byte> listbs = new ArrayList<Byte>();
		for (int i = 1; i < bs.length - 1; i++) {
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
		byte[] bodybs = DigitUtil.sliceBytes(newbs, 11, 11 + msgHead.getBodyLength() - 1);
		MsgBody msgBody = this.parseMsgBodyFromBytes(bodybs);
		pkg.setMsgBody(msgBody);
		return pkg;
	}
	
	//解码消息头
	private MsgHead parseMsgHeadFromBytes(byte[] data) {
		MsgHead msgHead = new MsgHead();
		msgHead.setHeadId(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 0, 1)));
    	boolean hasSubPack = ((byte) ((data[2] >> 5) & 0x1) == 1) ? true : false;
    	msgHead.setHasSubPack(hasSubPack);
    	int encryptType = ((byte) ((data[2] >> 2) & 0x1)) == 1 ? 1 : 0;
    	msgHead.setEncryptType(encryptType);
    	String bodyLen = DigitUtil.byteToBinaryStr(data[1], 1, 0) + DigitUtil.byteToBinaryStr(data[2], 7, 0);
    	msgHead.setBodyLength(Integer.parseInt(bodyLen, 2));;
    	msgHead.setTerminalPhone(new String(DigitUtil.bcdToStr(DigitUtil.sliceBytes(data, 3, 8))));
    	msgHead.setHeadSerial(DigitUtil.byte2ToInt(DigitUtil.sliceBytes(data, 9, 10)));
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
		locationInfo.setRemoteAddress(locationMsg.getChannel().remoteAddress().toString());
		//处理状态
		locationInfo.setCarState(DigitUtil.byteToBinaryStr(bodybs[2]) + DigitUtil.byteToBinaryStr(bodybs[3]));
        //处理经度
        float gpsPosX = DigitUtil.byte4ToInt(bodybs, 4);
        locationInfo.setGpsPosX(gpsPosX*25/9/1000000);
        //处理纬度
        float gpsPosY = DigitUtil.byte4ToInt(bodybs, 8);
        locationInfo.setGpsPosY(gpsPosY*25/9/1000000);
        //处理高程
        float gpsHeight = DigitUtil.byte2ToInt(new byte[] {bodybs[12], bodybs[13]});
        locationInfo.setGpsHeight(gpsHeight);
        //处理速度
        float gpsSpeed = DigitUtil.byte2ToInt(new byte[] {bodybs[14], bodybs[15]});
        locationInfo.setGpsSpeed(gpsSpeed);
        //处理方向
        float gpsDirect = DigitUtil.byte2ToInt(new byte[] {bodybs[16], bodybs[17]});
        locationInfo.setGpsDirect(gpsDirect/100);
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
		locationInfo.setCarNumber(carNumber);
        //处理司机ID
        locationInfo.setDriverId(new String(DigitUtil.sliceBytes(bodybs, 32, 41)));
        //处理核准证ID
        locationInfo.setWorkPassport(new String(DigitUtil.sliceBytes(bodybs, 42, 51)));
        //处理车厢状态
        locationInfo.setBoxClose(bodybs[52]);
        //处理举升状态
        locationInfo.setBoxUp(bodybs[53]);
        //处理空重状态
        locationInfo.setBoxEmpty(bodybs[54]);
        //处理违规情况
        locationInfo.setCarWeigui(bodybs[55]);
        locationMsg.setLocationInfo(locationInfo);
		return locationMsg;
	}
	
	//解码事件包
	public EventMsg toEventMsg(PackageData packageData) throws UnsupportedEncodingException {
		EventMsg eventMsg = new EventMsg(packageData);
		EventInfo eventInfo = new EventInfo();
		LocationInfo locationInfo = new LocationInfo();
		byte[] msgBodyBytes = eventMsg.getMsgBody().getBodyBytes();
        //处理事件流水号
        long eventSerialId = DigitUtil.byte4ToInt(DigitUtil.sliceBytes(msgBodyBytes, 2, 5), 0);
        eventInfo.setEventSerialId(eventSerialId);
        //处理事件类型
        eventInfo.setEventType((int) msgBodyBytes[6]);
        
        //开始处理位置信息
        byte[] locationbs = DigitUtil.sliceBytes(msgBodyBytes, 3, msgBodyBytes.length - 1);
		//设置终端sim
		locationInfo.setDevPhone(eventMsg.getMsgHead().getTerminalPhone());
		//设置终端地址
		locationInfo.setRemoteAddress(eventMsg.getChannel().remoteAddress().toString());
		//处理状态
		locationInfo.setCarState(DigitUtil.byteToBinaryStr(locationbs[2]) + DigitUtil.byteToBinaryStr(locationbs[3]));
        //处理经度
        float gpsPosX = DigitUtil.byte4ToInt(locationbs, 4);
        locationInfo.setGpsPosX(gpsPosX*25/9/1000000);
        //处理纬度
        float gpsPosY = DigitUtil.byte4ToInt(locationbs, 8);
        locationInfo.setGpsPosY(gpsPosY*25/9/1000000);
        //处理高程
        float gpsHeight = DigitUtil.byte2ToInt(new byte[] {locationbs[12], locationbs[13]});
        locationInfo.setGpsHeight(gpsHeight);
        //处理速度
        float gpsSpeed = DigitUtil.byte2ToInt(new byte[] {locationbs[14], locationbs[15]});
        locationInfo.setGpsSpeed(gpsSpeed);
        //处理方向
        float gpsDirect = DigitUtil.byte2ToInt(new byte[] {locationbs[16], locationbs[17]});
        locationInfo.setGpsDirect(gpsDirect/100);
        //处理设备发送时间
        String year = DigitUtil.bcdToStr(locationbs[18]);
        String month = DigitUtil.bcdToStr(locationbs[19]);
        String day = DigitUtil.bcdToStr(locationbs[20]);
        String hour = DigitUtil.bcdToStr(locationbs[21]);
        String minute = DigitUtil.bcdToStr(locationbs[22]);
        String second = DigitUtil.bcdToStr(locationbs[23]);
        String sendDatetime = "20" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        locationInfo.setSendDatetime(sendDatetime);//此时间在显示完毕更新
        //处理车牌号码
        String carNumber = new String(DigitUtil.sliceBytes(locationbs, 24, 31), "GBK");
		locationInfo.setCarNumber(carNumber);
        //处理司机ID
        locationInfo.setDriverId(new String(DigitUtil.sliceBytes(locationbs, 32, 41)));
        //处理核准证ID
        locationInfo.setWorkPassport(new String(DigitUtil.sliceBytes(locationbs, 42, 51)));
        //处理车厢状态
        locationInfo.setBoxClose(locationbs[52]);
        //处理举升状态
        locationInfo.setBoxUp(locationbs[53]);
        //处理空重状态
        locationInfo.setBoxEmpty(locationbs[54]);
        //处理违规情况
        locationInfo.setCarWeigui(locationbs[55]);
        eventInfo.setLocationInfo(locationInfo);
        eventMsg.setEventInfo(eventInfo);
		return eventMsg;
	}
	
	//解码终端版本信息
	public VersionMsg toVersionMsg(PackageData packageData) throws UnsupportedEncodingException {
		VersionMsg versionMsg = new VersionMsg(packageData);
		VersionInfo versionInfo = new VersionInfo();
		byte[] bodybs = packageData.getMsgBody().getBodyBytes();
    	Integer ecuType = (int) bodybs[8];
    	Integer carType = (int) bodybs[9];
    	byte[] infobs = new byte[bodybs.length - 10];
    	for (int i = 0; i < bodybs.length - 10; i++) {
    		infobs[i] = bodybs[i + 10];
    	}
    	String[] terminalInfo = new String(infobs, "utf8").split(",");
    	versionInfo.setMac(terminalInfo[0]);
    	versionInfo.setCarNumber(terminalInfo[1]);
    	versionInfo.setDevPhone(packageData.getMsgHead().getTerminalPhone());
    	versionInfo.setVersion(terminalInfo[2]);
    	versionInfo.setEcuType(ecuType.toString());
    	versionInfo.setCarType(carType.toString());
    	versionMsg.setVersionInfo(versionInfo);
    	return versionMsg;
	}
	
	//解码终端配置更新信息
	public ConfigMsg toConfigMsg(PackageData packageData) throws UnsupportedEncodingException {
		ConfigMsg configMsg = new ConfigMsg(packageData);
		ConfigInfo configInfo = new ConfigInfo();
		byte[] bodybs = packageData.getMsgBody().getBodyBytes();
    	byte[] macbs = new byte[17];
    	for (int i = 0; i < 17; i++) {
    		macbs[i] = bodybs[i + 12]; 
    	}
    	String mac = new String(macbs);
    	configInfo.setMac(mac);
    	configMsg.setConfigInfo(configInfo);
    	return configMsg;
	}
	
}
