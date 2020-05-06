package com.hs.Device.model;

import java.sql.Timestamp;

public class Device {
  private Integer deviceid;
  private String devid;
  private String devname;
  private String model;
  private Integer isonline;
  private Timestamp lasttime;
  private Timestamp fktime;
  private String facedataver;
  private String firmware;
  private String firmwarefilename;
  private String fkbindatalib;
  private String fpdataver;
  private String support;
  
public Timestamp getFktime() {
	return fktime;
}
public void setFktime(Timestamp fktime) {
	this.fktime = fktime;
}
public String getFacedataver() {
	return facedataver;
}
public void setFacedataver(String facedataver) {
	this.facedataver = facedataver;
}
public String getFirmware() {
	return firmware;
}
public void setFirmware(String firmware) {
	this.firmware = firmware;
}
public String getFirmwarefilename() {
	return firmwarefilename;
}
public void setFirmwarefilename(String firmwarefilename) {
	this.firmwarefilename = firmwarefilename;
}
public String getFkbindatalib() {
	return fkbindatalib;
}
public void setFkbindatalib(String fkbindatalib) {
	this.fkbindatalib = fkbindatalib;
}
public String getFpdataver() {
	return fpdataver;
}
public void setFpdataver(String fpdataver) {
	this.fpdataver = fpdataver;
}
public String getSupport() {
	return support;
}
public void setSupport(String support) {
	this.support = support;
}
public String getModel() {
	return model;
}
public void setModel(String model) {
	this.model = model;
}

public Timestamp getLasttime() {
	return lasttime;
}
public void setLasttime(Timestamp lasttime) {
	this.lasttime = lasttime;
}
public Integer getDeviceid() {
	return deviceid;
}
public void setDeviceid(Integer deviceid) {
	this.deviceid = deviceid;
}
public String getDevid() {
	return devid;
}
public void setDevid(String devid) {
	this.devid = devid;
}
public String getDevname() {
	return devname;
}
public void setDevname(String devname) {
	this.devname = devname;
}
public Integer getIsonline() {
	return isonline;
}
public void setIsonline(Integer isonline) {
	this.isonline = isonline;
}
@Override
public String toString() {
	return "Device [deviceid=" + deviceid + ", devid=" + devid + ", devname=" + devname + ", model=" + model
			+ ", isonline=" + isonline + ", lasttime=" + lasttime + ", fktime=" + fktime + ", facedataver="
			+ facedataver + ", firmware=" + firmware + ", firmwarefilename=" + firmwarefilename + ", fkbindatalib="
			+ fkbindatalib + ", fpdataver=" + fpdataver + ", support=" + support + "]";
}




  
  
}
