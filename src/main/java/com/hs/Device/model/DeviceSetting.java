package com.hs.Device.model;

//设备参数
public class DeviceSetting {
   //V536设备参数
	private Integer devstid;   //主键
	private Integer usergetid;   //外键
	
	private String alarmDelay;
	private String antiPass;
	private String devName;
	private String interval;
	private String language;
	private String openDoorDelay;
	private String reverifyTime;
	private String screensaversTime;
	private String serverHost;
	private String serverPort;
	private String sleepTime;
	private String tamperAlarm;
	private String volume;
	private String wiegandType;
	private String pushServerHost;
	private String pushServerPort;
	private String pushEnable;
	private String verifyMode;
	
	
	public String getPushServerHost() {
		return pushServerHost;
	}
	public void setPushServerHost(String pushServerHost) {
		this.pushServerHost = pushServerHost;
	}
	public String getPushServerPort() {
		return pushServerPort;
	}
	public void setPushServerPort(String pushServerPort) {
		this.pushServerPort = pushServerPort;
	}
	public String getPushEnable() {
		return pushEnable;
	}
	public void setPushEnable(String pushEnable) {
		this.pushEnable = pushEnable;
	}
	public String getVerifyMode() {
		return verifyMode;
	}
	public void setVerifyMode(String verifyMode) {
		this.verifyMode = verifyMode;
	}
	public Integer getDevstid() {
		return devstid;
	}
	public void setDevstid(Integer devstid) {
		this.devstid = devstid;
	}
	public Integer getUsergetid() {
		return usergetid;
	}
	public void setUsergetid(Integer usergetid) {
		this.usergetid = usergetid;
	}
	public String getAlarmDelay() {
		return alarmDelay;
	}
	public void setAlarmDelay(String alarmDelay) {
		this.alarmDelay = alarmDelay;
	}
	public String getAntiPass() {
		return antiPass;
	}
	public void setAntiPass(String antiPass) {
		this.antiPass = antiPass;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getOpenDoorDelay() {
		return openDoorDelay;
	}
	public void setOpenDoorDelay(String openDoorDelay) {
		this.openDoorDelay = openDoorDelay;
	}
	public String getReverifyTime() {
		return reverifyTime;
	}
	public void setReverifyTime(String reverifyTime) {
		this.reverifyTime = reverifyTime;
	}
	public String getScreensaversTime() {
		return screensaversTime;
	}
	public void setScreensaversTime(String screensaversTime) {
		this.screensaversTime = screensaversTime;
	}
	public String getServerHost() {
		return serverHost;
	}
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	public String getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(String sleepTime) {
		this.sleepTime = sleepTime;
	}
	public String getTamperAlarm() {
		return tamperAlarm;
	}
	public void setTamperAlarm(String tamperAlarm) {
		this.tamperAlarm = tamperAlarm;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getWiegandType() {
		return wiegandType;
	}
	public void setWiegandType(String wiegandType) {
		this.wiegandType = wiegandType;
	}
	@Override
	public String toString() {
		return "DeviceSetting [devstid=" + devstid + ", usergetid=" + usergetid + ", alarmDelay=" + alarmDelay
				+ ", antiPass=" + antiPass + ", devName=" + devName + ", interval=" + interval + ", language="
				+ language + ", openDoorDelay=" + openDoorDelay + ", reverifyTime=" + reverifyTime
				+ ", screensaversTime=" + screensaversTime + ", serverHost=" + serverHost + ", serverPort=" + serverPort
				+ ", sleepTime=" + sleepTime + ", tamperAlarm=" + tamperAlarm + ", volume=" + volume + ", wiegandType="
				+ wiegandType + ", pushServerHost=" + pushServerHost + ", pushServerPort=" + pushServerPort
				+ ", pushEnable=" + pushEnable + ", verifyMode=" + verifyMode + "]";
	}


	
	
}
