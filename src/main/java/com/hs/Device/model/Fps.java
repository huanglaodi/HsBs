package com.hs.Device.model;

import java.util.Arrays;

import com.alibaba.fastjson.annotation.JSONField;

public class Fps {

	@JSONField(serialize = false)
	private Integer fpsid;
	@JSONField(serialize = false)
	private Integer usid;
	@JSONField(serialize = false)
	private byte[] fpsdata;
	
	private String fpsdata_base64;
	

	public String getFpsdata_base64() {
		return fpsdata_base64;
	}
	public void setFpsdata_base64(String fpsdata_base64) {
		this.fpsdata_base64 = fpsdata_base64;
	}
	public Integer getFpsid() {
		return fpsid;
	}
	public void setFpsid(Integer fpsid) {
		this.fpsid = fpsid;
	}
	public Integer getUsid() {
		return usid;
	}
	public void setUsid(Integer usid) {
		this.usid = usid;
	}
	public byte[] getFpsdata() {
		return fpsdata;
	}
	public void setFpsdata(byte[] fpsdata) {
		this.fpsdata = fpsdata;
	}
	@Override
	public String toString() {
		return "Fps [fpsid=" + fpsid + ", usid=" + usid + ", fpsdata=" + Arrays.toString(fpsdata) + ", fpsdata_base64="
				+ fpsdata_base64 + "]";
	}
	
	
}
