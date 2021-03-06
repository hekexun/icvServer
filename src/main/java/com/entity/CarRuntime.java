package com.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CarRuntime {

	private String devPhone;
	private String locateState; //
	private float gpsPosX; //经度11-14 /除以10的6次方
	private float gpsPosY; //纬度15-18 /除以10的6次方
	private float gpsSpeed; //速度19-20 除以10
	private int gpsDirect; //方向21-22
	private float gpsMileage;//里程23
	private float canSpeed; //can速度 24-25 除以10
	private float Height; //高程 除以10  26-27
	private  int drivemode ;//驾驶模式   28-29
	private int engine;//发动机转速 30-31
	private int acceleratorPedal;//加速踏板开度 32
	private int atmosphericPressure;//大气压力33-36
	private int brakePedal;//制动踏板开度 37
	private int tempreture;//温度 38
	private int steeringwheelAngle;//方向盘转角39
	private int videostate;//视频状态 40

	private Date reviceDatetime;

	private Date sendDatetime;

	public String getLocateState() {
		return locateState;
	}

	public void setLocateState(String locateState) {
		this.locateState = locateState;
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

	public float getGpsSpeed() {
		return gpsSpeed;
	}

	public void setGpsSpeed(float gpsSpeed) {
		this.gpsSpeed = gpsSpeed;
	}

	public int getGpsDirect() {
		return gpsDirect;
	}

	public void setGpsDirect(int gpsDirect) {
		this.gpsDirect = gpsDirect;
	}

	public float getGpsMileage() {
		return gpsMileage;
	}

	public void setGpsMileage(float gpsMileage) {
		this.gpsMileage = gpsMileage;
	}

	public float getCanSpeed() {
		return canSpeed;
	}

	public void setCanSpeed(float canSpeed) {
		this.canSpeed = canSpeed;
	}

	public float getHeight() {
		return Height;
	}

	public void setHeight(float height) {
		Height = height;
	}

	public int getDrivemode() {
		return drivemode;
	}

	public void setDrivemode(int drivemode) {
		this.drivemode = drivemode;
	}

	public int getEngine() {
		return engine;
	}

	public void setEngine(int engine) {
		this.engine = engine;
	}

	public int getAcceleratorPedal() {
		return acceleratorPedal;
	}

	public void setAcceleratorPedal(int acceleratorPedal) {
		this.acceleratorPedal = acceleratorPedal;
	}

	public int getAtmosphericPressure() {
		return atmosphericPressure;
	}

	public void setAtmosphericPressure(int atmosphericPressure) {
		this.atmosphericPressure = atmosphericPressure;
	}

	public int getBrakePedal() {
		return brakePedal;
	}

	public void setBrakePedal(int brakePedal) {
		this.brakePedal = brakePedal;
	}

	public int getTempreture() {
		return tempreture;
	}

	public void setTempreture(int tempreture) {
		this.tempreture = tempreture;
	}

	public int getSteeringwheelAngle() {
		return steeringwheelAngle;
	}

	public void setSteeringwheelAngle(int steeringwheelAngle) {
		this.steeringwheelAngle = steeringwheelAngle;
	}

	public int getVideostate() {
		return videostate;
	}

	public void setVideostate(int videostate) {
		this.videostate = videostate;
	}



	
	public String getDevPhone() {
		return devPhone;
	}

	
	public void setDevPhone(String devPhone) {
		this.devPhone = devPhone;
	}

	

	public Date getReviceDatetime() {
		return reviceDatetime;
	}

	
	public void setReviceDatetime(Date reviceDatetime) {
		this.reviceDatetime = reviceDatetime;
	}

	
	public Date getSendDatetime() {
		return sendDatetime;
	}

	
	public void setSendDatetime(Date sendDatetime) {
		this.sendDatetime = sendDatetime;
	}
		
}
