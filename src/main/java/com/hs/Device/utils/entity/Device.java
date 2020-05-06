package com.hs.Device.utils.entity;

public class Device {
	private String DevId;
	private String fk_name;
	private String fk_time;
	private String fk_info;
	
	public Device() {
	}
	public Device(String devId, String fk_name, String fk_time, String fk_info) {
		DevId = devId;
		this.fk_name = fk_name;
		this.fk_time = fk_time;
		this.fk_info = fk_info;
	}
	public String getDevId() {
		return DevId;
	}
	public void setDevId(String devId) {
		DevId = devId;
	}
	public String getFk_name() {
		return fk_name;
	}
	public void setFk_name(String fk_name) {
		this.fk_name = fk_name;
	}
	public String getFk_time() {
		return fk_time;
	}
	public void setFk_time(String fk_time) {
		this.fk_time = fk_time;
	}
	public String getFk_info() {
		return fk_info;
	}
	public void setFk_info(String fk_info) {
		this.fk_info = fk_info;
	}
}
