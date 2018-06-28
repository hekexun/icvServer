package com.vo.req;

import com.vo.PackageData;

/**
 * ClassName: LocationMsg 
 * @Description: 基本位置信息消息
 */
 
public class LocationMsg extends PackageData {

	/**
	 * @Fields locationInfo : 基本位置信息
	 */
	private LocationInfo locationInfo;
	
	public LocationMsg() {
		
	}

	public LocationMsg(PackageData packageData) {
		this();
		this.channel = packageData.getChannel();
		this.msgHead = packageData.getMsgHead();
		this.msgBody = packageData.getMsgBody();
	}
	
	public LocationInfo getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}

	public static class LocationInfo {

		public String getDevPhone() {
			return devPhone;
		}

		public void setDevPhone(String devPhone) {
			this.devPhone = devPhone;
		}

		private String devPhone;//号码
		private String carState; //车辆状态
		private float gpsPosX; //经度
		private float gpsPosY; //纬度
		private float gpsHeight; //高程
		private float gpsSpeed; //速度
		private float gpsDirect; //方向
		private String sendDatetime; //时间(设备时间)

		


		public String getCarState() {
			return carState;
		}
		
		public void setCarState(String carState) {
			this.carState = carState;
		}

		public float getGpsPosX() {
			return gpsPosX;
		}

		public void setGpsPosX(float gpsPosX) {
			this.gpsPosX = gpsPosX;
		}

		public float getGpsPosY() {
			return gpsPosY;
		}
		
		public void setGpsPosY(float gpsPosY) {
			this.gpsPosY = gpsPosY;
		}

		public float getGpsHeight() {
			return gpsHeight;
		}
		
		public void setGpsHeight(float gpsHeight) {
			this.gpsHeight = gpsHeight;
		}

		public float getGpsSpeed() {
			return gpsSpeed;
		}
		
		public void setGpsSpeed(float gpsSpeed) {
			this.gpsSpeed = gpsSpeed;
		}

		public float getGpsDirect() {
			return gpsDirect;
		}
		
		public void setGpsDirect(float gpsDirect) {
			this.gpsDirect = gpsDirect;
		}

		public String getSendDatetime() {
			return sendDatetime;
		}

		public void setSendDatetime(String sendDatetime) {
			this.sendDatetime = sendDatetime;
		}
		



	}

}
