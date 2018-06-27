package com.vo.req;

import com.vo.PackageData;
/**
        * ClassName: catcMsg
        * @Description: 中国工况信息消息
        */

public class catcMsg extends PackageData {
    public void setLocationInfo(catcInfo catcInfo) {
        this.catcInfo = catcInfo;
    }

    /**
            * @Fields locationInfo : 基本位置信息
	 */
    private catcInfo catcInfo;

    public catcMsg() {

    }

    public catcMsg(PackageData packageData) {
        this();
        this.channel = packageData.getChannel();
        this.msgHead = packageData.getMsgHead();
        this.msgBody = packageData.getMsgBody();
    }
    public catcInfo getLocationInfo() {
        return catcInfo;
    }
    public static class catcInfo {
        public String getLocState() {
            return locState;
        }

        public void setLocState(String locState) {
            this.locState = locState;
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

        private String locState; //定位状态
        private float gpsPosX; //经度
        private float gpsPosY; //纬度
        private float gpsSpeed; //速度
        private float gpsDirect; //方向
        private String sendDatetime; //时间(设备时间)
    }
}
