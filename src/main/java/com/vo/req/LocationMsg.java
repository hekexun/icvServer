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
		private String BMSState; //运行模式，第1字节0bit，
		private String errorLevel; //系统故障等级
		//电池组的信号
		private int SOC; //电池组SOC
		private int MaxBatteryMonomerNumber; //电池最高单体电压编号
		private double MaxBatteryMonomerVoltage;//电池最高单体电压
		private int MinBatteryMonomerNumber; //电池最低单体电压编号
		private double MinBatteryMonomerVoltage; //电池最低单体电压

		//
		private String prechargeState; //预充电状态
		private int highVoltageConnectorsState;//高压连接状态
		private String BmsCheckState;//BMS自检状态
		private int SOH;//电池SOH，第9字节
		private int totalVoltage;//电池组总电压
		private int total电流;//电池组总电流
		private int MaxTempreture;//电池最高温度
		private int lowTempreture;//电池最低温度

		private String ChargeConnectionState;//充电线连接状态
		private String ChargeState;//充电状态
		private String positiveContactorState;//总正接触器状态
		private String preChargeConnectorState;//预充电接触器状态
		private String negativeContactorState;//总负接触器状态
		private String preChargeContactorState;//充电机接触器状态
		private String heatingSystemState;//电池加热系统工作状态
		private String coolingSystemState;//电池冷却系统工作状态
		private String wetnessState;//电池湿度状态
		private int MaxContinuousChargePower;//最大可用持续充电功率
		private int MaxChargePower;//最大可用短时充电功率
		private int MaxContinuousDischargePower;//最大可用持续放电功率
		private int MaxDischargePower;//最大可用短时放电功率
		private int positiveResistance;//正极绝缘阻值
		private int negativeResistance;//负极绝缘阻值

		private  String insulationError;//绝缘故障
		private  String preChargeError;//预充电故障
		private String positiveContactorError;//总正接触器故障
		private String negtiveContactorError;//总负接触器故障
		private String preChargeConnectorError;//预充接触器故障
		private String chargeContactorError;//充电机接触器故障
		private String lowTempertureError;//温度过低故障
		private String highTempertureError;//温度过高故障
		private String diffTempertureError;//温度差异故障
		private String highWetnessError;//湿度过大故障
		private String chargeError;//充电过流故障
		private String dischargeOvercurrentError;//放点过流故障
		private String moduleOvervoltageError;//单体过压故障
		private String moduleUndervoltageError;//单体欠压故障
		private String moduleVoltageDiffError;//单体电压差异故障
		private String batteryOvervoltageError;//电池组过压故障
		private String batteryUndervoltageError;//电池组欠压故障
		private String underSocError;//SOC过低故障
		private String overSocError;//SOC过高故障
		private String controlBoardPowerError;//控制板电源故障
		private String subPCBCanError;//子板CAN通讯故障
		private String VCUcCanError;//与VCU间CAN通讯故障
		private String chargeCanError;//与充电机CAN通讯故障
		private String coolingSystemError;//电池冷却系统故障
		private String heatingSystemError;//电池加热系统故障
		private String moduleVoltageSenserError;//单体电压传感器故障
		private String currentSenserError;//电流传感器故障
		private String tempretureSenserError;//温度传感器故障
		private String voltageSenserError;//总电压传感器故障
		private String wetnessSenserError;//湿度传感器故障
		private String ChargeUnitError;//充电机故障
		private String repairSwitchError;//维修开关接通状态
		private String batteryCapSwitchError;//电池包揭盖开关
		private String sendDatetime; //时间(设备时间)

		public String getErrorLevel() {
			return errorLevel;
		}

		public void setErrorLevel(String errorLevel) {
			this.errorLevel = errorLevel;
		}

		public String getBMSState() {
			return BMSState;
		}

		public void setBMSState(String BMSState) {
			this.BMSState = BMSState;
		}

		public int getSOC() {
			return SOC;
		}

		public void setSOC(int SOC) {
			this.SOC = SOC;
		}

		public int getMaxBatteryMonomerNumber() {
			return MaxBatteryMonomerNumber;
		}

		public void setMaxBatteryMonomerNumber(int maxBatteryMonomerNumber) {
			MaxBatteryMonomerNumber = maxBatteryMonomerNumber;
		}

		public double getMaxBatteryMonomerVoltage() {
			return MaxBatteryMonomerVoltage;
		}

		public void setMaxBatteryMonomerVoltage(double maxBatteryMonomerVoltage) {
			MaxBatteryMonomerVoltage = maxBatteryMonomerVoltage;
		}

		public int getMinBatteryMonomerNumber() {
			return MinBatteryMonomerNumber;
		}

		public void setMinBatteryMonomerNumber(int minBatteryMonomerNumber) {
			MinBatteryMonomerNumber = minBatteryMonomerNumber;
		}

		public double getMinBatteryMonomerVoltage() {
			return MinBatteryMonomerVoltage;
		}

		public void setMinBatteryMonomerVoltage(double minBatteryMonomerVoltage) {
			MinBatteryMonomerVoltage = minBatteryMonomerVoltage;
		}

		public String getPrechargeState() {
			return prechargeState;
		}

		public void setPrechargeState(String prechargeState) {
			this.prechargeState = prechargeState;
		}

		public int getHighVoltageConnectorsState() {
			return highVoltageConnectorsState;
		}

		public void setHighVoltageConnectorsState(int highVoltageConnectorsState) {
			this.highVoltageConnectorsState = highVoltageConnectorsState;
		}

		public String getBmsCheckState() {
			return BmsCheckState;
		}

		public void setBmsCheckState(String bmsCheckState) {
			BmsCheckState = bmsCheckState;
		}

		public int getSOH() {
			return SOH;
		}

		public void setSOH(int SOH) {
			this.SOH = SOH;
		}

		public int getTotalVoltage() {
			return totalVoltage;
		}

		public void setTotalVoltage(int totalVoltage) {
			this.totalVoltage = totalVoltage;
		}

		public int getTotal电流() {
			return total电流;
		}

		public void setTotal电流(int total电流) {
			this.total电流 = total电流;
		}

		public float getMaxTempreture() {
			return MaxTempreture;
		}

		public void setMaxTempreture(int maxTempreture) {
			MaxTempreture = maxTempreture;
		}

		public float getLowTempreture() {
			return lowTempreture;
		}

		public void setLowTempreture(int lowTempreture) {
			this.lowTempreture = lowTempreture;
		}

		public String getChargeConnectionState() {
			return ChargeConnectionState;
		}

		public void setChargeConnectionState(String chargeConnectionState) {
			ChargeConnectionState = chargeConnectionState;
		}

		public String getChargeState() {
			return ChargeState;
		}

		public void setChargeState(String chargeState) {
			ChargeState = chargeState;
		}

		public String getPositiveContactorState() {
			return positiveContactorState;
		}

		public void setPositiveContactorState(String positiveContactorState) {
			this.positiveContactorState = positiveContactorState;
		}

		public String getPreChargeConnectorState() {
			return preChargeConnectorState;
		}

		public void setPreChargeConnectorState(String preChargeConnectorState) {
			this.preChargeConnectorState = preChargeConnectorState;
		}

		public String getNegativeContactorState() {
			return negativeContactorState;
		}

		public void setNegativeContactorState(String negativeContactorState) {
			this.negativeContactorState = negativeContactorState;
		}

		public String getPreChargeContactorState() {
			return preChargeContactorState;
		}

		public void setPreChargeContactorState(String preChargeContactorState) {
			this.preChargeContactorState = preChargeContactorState;
		}

		public String getHeatingSystemState() {
			return heatingSystemState;
		}

		public void setHeatingSystemState(String heatingSystemState) {
			this.heatingSystemState = heatingSystemState;
		}

		public String getCoolingSystemState() {
			return coolingSystemState;
		}

		public void setCoolingSystemState(String coolingSystemState) {
			this.coolingSystemState = coolingSystemState;
		}

		public String getWetnessState() {
			return wetnessState;
		}

		public void setWetnessState(String wetnessState) {
			this.wetnessState = wetnessState;
		}

		public int getMaxContinuousChargePower() {
			return MaxContinuousChargePower;
		}

		public void setMaxContinuousChargePower(int maxContinuousChargePower) {
			MaxContinuousChargePower = maxContinuousChargePower;
		}

		public int getMaxChargePower() {
			return MaxChargePower;
		}

		public void setMaxChargePower(int maxChargePower) {
			MaxChargePower = maxChargePower;
		}

		public int getMaxContinuousDischargePower() {
			return MaxContinuousDischargePower;
		}

		public void setMaxContinuousDischargePower(int maxContinuousDischargePower) {
			MaxContinuousDischargePower = maxContinuousDischargePower;
		}

		public int getMaxDischargePower() {
			return MaxDischargePower;
		}

		public void setMaxDischargePower(int maxDischargePower) {
			MaxDischargePower = maxDischargePower;
		}

		public int getPositiveResistance() {
			return positiveResistance;
		}

		public void setPositiveResistance(int positiveResistance) {
			this.positiveResistance = positiveResistance;
		}

		public int getNegativeResistance() {
			return negativeResistance;
		}

		public void setNegativeResistance(int negativeResistance) {
			this.negativeResistance = negativeResistance;
		}

		public String getInsulationError() {
			return insulationError;
		}

		public void setInsulationError(String insulationError) {
			this.insulationError = insulationError;
		}

		public String getPreChargeError() {
			return preChargeError;
		}

		public void setPreChargeError(String preChargeError) {
			this.preChargeError = preChargeError;
		}

		public String getPositiveContactorError() {
			return positiveContactorError;
		}

		public void setPositiveContactorError(String positiveContactorError) {
			this.positiveContactorError = positiveContactorError;
		}

		public String getNegtiveContactorError() {
			return negtiveContactorError;
		}

		public void setNegtiveContactorError(String negtiveContactorError) {
			this.negtiveContactorError = negtiveContactorError;
		}

		public String getPreChargeConnectorError() {
			return preChargeConnectorError;
		}

		public void setPreChargeConnectorError(String preChargeConnectorError) {
			this.preChargeConnectorError = preChargeConnectorError;
		}

		public String getChargeContactorError() {
			return chargeContactorError;
		}

		public void setChargeContactorError(String chargeContactorError) {
			this.chargeContactorError = chargeContactorError;
		}

		public String getLowTempertureError() {
			return lowTempertureError;
		}

		public void setLowTempertureError(String lowTempertureError) {
			this.lowTempertureError = lowTempertureError;
		}

		public String getHighTempertureError() {
			return highTempertureError;
		}

		public void setHighTempertureError(String highTempertureError) {
			this.highTempertureError = highTempertureError;
		}

		public String getDiffTempertureError() {
			return diffTempertureError;
		}

		public void setDiffTempertureError(String diffTempertureError) {
			this.diffTempertureError = diffTempertureError;
		}

		public String getHighWetnessError() {
			return highWetnessError;
		}

		public void setHighWetnessError(String highWetnessError) {
			this.highWetnessError = highWetnessError;
		}

		public String getChargeError() {
			return chargeError;
		}

		public void setChargeError(String chargeError) {
			this.chargeError = chargeError;
		}

		public String getDischargeOvercurrentError() {
			return dischargeOvercurrentError;
		}

		public void setDischargeOvercurrentError(String dischargeOvercurrentError) {
			this.dischargeOvercurrentError = dischargeOvercurrentError;
		}

		public String getModuleOvervoltageError() {
			return moduleOvervoltageError;
		}

		public void setModuleOvervoltageError(String moduleOvervoltageError) {
			this.moduleOvervoltageError = moduleOvervoltageError;
		}

		public String getModuleUndervoltageError() {
			return moduleUndervoltageError;
		}

		public void setModuleUndervoltageError(String moduleUndervoltageError) {
			this.moduleUndervoltageError = moduleUndervoltageError;
		}

		public String getModuleVoltageDiffError() {
			return moduleVoltageDiffError;
		}

		public void setModuleVoltageDiffError(String moduleVoltageDiffError) {
			this.moduleVoltageDiffError = moduleVoltageDiffError;
		}

		public String getBatteryOvervoltageError() {
			return batteryOvervoltageError;
		}

		public void setBatteryOvervoltageError(String batteryOvervoltageError) {
			this.batteryOvervoltageError = batteryOvervoltageError;
		}

		public String getBatteryUndervoltageError() {
			return batteryUndervoltageError;
		}

		public void setBatteryUndervoltageError(String batteryUndervoltageError) {
			this.batteryUndervoltageError = batteryUndervoltageError;
		}

		public String getUnderSocError() {
			return underSocError;
		}

		public void setUnderSocError(String underSocError) {
			this.underSocError = underSocError;
		}

		public String getOverSocError() {
			return overSocError;
		}

		public void setOverSocError(String overSocError) {
			this.overSocError = overSocError;
		}

		public String getControlBoardPowerError() {
			return controlBoardPowerError;
		}

		public void setControlBoardPowerError(String controlBoardPowerError) {
			this.controlBoardPowerError = controlBoardPowerError;
		}

		public String getSubPCBCanError() {
			return subPCBCanError;
		}

		public void setSubPCBCanError(String subPCBCanError) {
			this.subPCBCanError = subPCBCanError;
		}

		public String getVCUcCanError() {
			return VCUcCanError;
		}

		public void setVCUcCanError(String VCUcCanError) {
			this.VCUcCanError = VCUcCanError;
		}

		public String getChargeCanError() {
			return chargeCanError;
		}

		public void setChargeCanError(String chargeCanError) {
			this.chargeCanError = chargeCanError;
		}

		public String getCoolingSystemError() {
			return coolingSystemError;
		}

		public void setCoolingSystemError(String coolingSystemError) {
			this.coolingSystemError = coolingSystemError;
		}

		public String getHeatingSystemError() {
			return heatingSystemError;
		}

		public void setHeatingSystemError(String heatingSystemError) {
			this.heatingSystemError = heatingSystemError;
		}

		public String getModuleVoltageSenserError() {
			return moduleVoltageSenserError;
		}

		public void setModuleVoltageSenserError(String moduleVoltageSenserError) {
			this.moduleVoltageSenserError = moduleVoltageSenserError;
		}

		public String getCurrentSenserError() {
			return currentSenserError;
		}

		public void setCurrentSenserError(String currentSenserError) {
			this.currentSenserError = currentSenserError;
		}

		public String getTempretureSenserError() {
			return tempretureSenserError;
		}

		public void setTempretureSenserError(String tempretureSenserError) {
			this.tempretureSenserError = tempretureSenserError;
		}

		public String getVoltageSenserError() {
			return voltageSenserError;
		}

		public void setVoltageSenserError(String voltageSenserError) {
			this.voltageSenserError = voltageSenserError;
		}

		public String getWetnessSenserError() {
			return wetnessSenserError;
		}

		public void setWetnessSenserError(String wetnessSenserError) {
			this.wetnessSenserError = wetnessSenserError;
		}

		public String getChargeUnitError() {
			return ChargeUnitError;
		}

		public void setChargeUnitError(String chargeUnitError) {
			ChargeUnitError = chargeUnitError;
		}

		public String getRepairSwitchError() {
			return repairSwitchError;
		}

		public void setRepairSwitchError(String repairSwitchError) {
			this.repairSwitchError = repairSwitchError;
		}

		public String getBatteryCapSwitchError() {
			return batteryCapSwitchError;
		}

		public void setBatteryCapSwitchError(String batteryCapSwitchError) {
			this.batteryCapSwitchError = batteryCapSwitchError;
		}

		public String getSendDatetime() {
			return sendDatetime;
		}

		public void setSendDatetime(String sendDatetime) {
			this.sendDatetime = sendDatetime;
		}
	}

}
