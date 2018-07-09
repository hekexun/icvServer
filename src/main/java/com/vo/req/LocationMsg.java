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
        ///hekexun
		/*
		private String devPhone;//号码
		private String carState; //车辆状态
		private float gpsPosX; //经度
		private float gpsPosY; //纬度
		private float gpsHeight; //高程
		private float gpsSpeed; //速度
		private float gpsDirect; //方向
		private String sendDatetime; //时间(设备时间)*/
		///hekexun 修改用来接收新能源电池信息的info 2018-07-09

		private String devPhone;//号码
		private String BMSState; //运行模式
		private float errorLevel; //系统故障等级
		//电池组的信号
		private float SOC; //电池组SOC
		private float MaxBatteryMonomerNumber; //电池最高单体电压编号
		private float MaxBatteryMonomerVoltage;//电池最高单体电压
		private float MinBatteryMonomerNumber; //电池最低单体电压编号
		private float MinBatteryMonomerVoltage; //电池最低单体电压

		//
		private String prechargeState; //预充电状态
		private float highVoltageConnectorsState;//高压连接状态，第8字节 1bit
		private String BmsCheckState;//BMS自检状态，第8字节 2,3bit
		private float SOH;//电池SOH，第9字节
		private float totalVoltage;//电池组总电压，第10,11字节
		private float total电流;//电池组总电流，第12,13字节
		private float MaxTempreture;//电池最高温度，第14字节
		private float lowTempreture;//电池最低温度，第15字节

		private String ChargeConnectionState;//充电线连接状态，第16字节0bit
		private String ChargeState;//充电状态，第16字节1,2bit
		private String positiveContactorState;//总正接触器状态，第16字节3bit
		private String preChargeConnectorState;//预充电接触器状态，第16字节4bit
		private String negativeContactorState;//总负接触器状态，第16字节5bit
		private String preChargeContactorState;//充电机接触器状态，第16字节6bit
		private String heatingSystemState;//电池加热系统工作状态，第17字节0,1bit
		private String coolingSystemState;//电池冷却系统工作状态，第17字节2,3bit
		private String wetnessState;//电池湿度状态，第17字节4,5,6,7,bit
		private float MaxContinuousChargePower;//最大可用持续充电功率，第18字节
		private float MaxChargePower;//最大可用短时充电功率，第19字节
		private float MaxContinuousDischargePower;//最大可用持续放电功率，第20字节
		private float MaxDischargePower;//最大可用短时放电功率，第21字节
		private float positiveResistance;//正极绝缘阻值，第22字节
		private float negativeResistance;//负极绝缘阻值，第23字节

		private  float insulationError;//绝缘故障，第24字节0,1,2bit
		private  float preChargeError;//预充电故障，第24字节4bit
		private String positiveContactorError;//总正接触器故障，第24字节5bit
		private String negtiveContactorError;//总负接触器故障，第24字节6bit
		private String preChargeConnectorError;//预充接触器故障，第24字节7bit
		private String chargeContactorError;//充电机接触器故障，第25字节0bit
		private String lowTempertureError;//温度过低故障，第25字节1,2bit
		private String highTempertureError;//温度过高故障，第25字节3,4bit
		private String diffTempertureError;//温度差异故障，第25字节5,6bit
		private String highWetnessError;//湿度过大故障，第25字节7bit
		private String ChargeError;//充电过流故障，第26字节0,1bit
		放点过流故障，第26字节2,3bit
		单体过压故障，第26字节4,5bit
		单体欠压故障，第26字节6,7bit
		单体电压差异故障，第27字节0,1bit
		电池组过压故障，第27字节2,3bit
		电池组欠压故障，第27字节4,5bit
		SOC过低故障，第28字节0,1bit
		SOC过高故障，第28字节2,3bit
		控制板电源故障，第29字节0bit
		子板CAN通讯故障，第29字节2,3bit
		与VCU间CAN通讯故障，第29字节4,5bit
		与充电机CAN通讯故障，第29字节6,7bit
		电池冷却系统故障，第30字节0bit
		电池加热系统故障，第30字节1bit
		单体电压传感器故障，第30字节2bit
		电流传感器故障，第30字节3bit
		温度传感器故障，第30字节4bit
		总电压传感器故障，第30字节5bit
		湿度传感器故障，第30字节6bit
		充电机故障，第30字节7bit
		维修开关接通状态，第31字节0bit
		电池包揭盖开关，第31字节1bit



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
