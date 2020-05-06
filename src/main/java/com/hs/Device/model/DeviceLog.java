package com.hs.Device.model;

import java.util.Arrays;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceLog {

	private Integer devicelogid;//主键
	private Integer usergetid; //外键
	private Integer pkid;     //外键
	
	private String devid;
	private String userid;
	private String verifymode;
	private String iomode;
	private String iotime;
	private String logimage;
	private String fkbindatalib;
	private byte[] data;
	private String data_base64;
	private Integer logcount;
	private String onelogsize;
	private String logarray;
	
	//V536
	@JSONField(name="inOut")
	private String inouts;
	private String doorMode;
	private byte[] logphoto;
	private String logphoto_base64;
	
	
	public String getDoorMode() {
		return doorMode;
	}
	public void setDoorMode(String doorMode) {
		this.doorMode = doorMode;
	}
	public String getData_base64() {
		return data_base64;
	}
	public void setData_base64(String data_base64) {
		this.data_base64 = data_base64;
	}
	public String getLogphoto_base64() {
		return logphoto_base64;
	}
	public void setLogphoto_base64(String logphoto_base64) {
		this.logphoto_base64 = logphoto_base64;
	}
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public Integer getLogcount() {
		return logcount;
	}
	public void setLogcount(Integer logcount) {
		this.logcount = logcount;
	}
	public String getOnelogsize() {
		return onelogsize;
	}
	public void setOnelogsize(String onelogsize) {
		this.onelogsize = onelogsize;
	}
	public String getLogarray() {
		return logarray;
	}
	public void setLogarray(String logarray) {
		this.logarray = logarray;
	}
	public Integer getUsergetid() {
		return usergetid;
	}
	public void setUsergetid(Integer usergetid) {
		this.usergetid = usergetid;
	}
	
	public String getInouts() {
		return inouts;
	}
	public void setInouts(String inouts) {
		this.inouts = inouts;
	}
	public byte[] getLogphoto() {
		return logphoto;
	}
	public void setLogphoto(byte[] logphoto) {
		this.logphoto = logphoto;
	}
	public String getFkbindatalib() {
		return fkbindatalib;
	}
	public void setFkbindatalib(String fkbindatalib) {
		this.fkbindatalib = fkbindatalib;
	}
	public Integer getDevicelogid() {
		return devicelogid;
	}
	public void setDevicelogid(Integer devicelogid) {
		this.devicelogid = devicelogid;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getVerifymode() {
		return verifymode;
	}
	public void setVerifymode(String verifymode) {
		this.verifymode = verifymode;
	}
	public String getIomode() {
		return iomode;
	}
	public void setIomode(String iomode) {
		this.iomode = iomode;
	}
	public String getIotime() {
		return iotime;
	}
	public void setIotime(String iotime) {
		this.iotime = iotime;
	}
	public String getLogimage() {
		return logimage;
	}
	public void setLogimage(String logimage) {
		this.logimage = logimage;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getDevid() {
		return devid;
	}
	public void setDevid(String devid) {
		this.devid = devid;
	}
	@Override
	public String toString() {
		return "DeviceLog [devicelogid=" + devicelogid + ", usergetid=" + usergetid + ", pkid=" + pkid + ", devid="
				+ devid + ", userid=" + userid + ", verifymode=" + verifymode + ", iomode=" + iomode + ", iotime="
				+ iotime + ", logimage=" + logimage + ", fkbindatalib=" + fkbindatalib + ", data="
				+ Arrays.toString(data) + ", data_base64=" + data_base64 + ", logcount=" + logcount + ", onelogsize="
				+ onelogsize + ", logarray=" + logarray + ", inouts=" + inouts + ", doorMode=" + doorMode
				+ ", logphoto=" + Arrays.toString(logphoto) + ", logphoto_base64=" + logphoto_base64 + "]";
	}




	
	

	
}
