package com.hs.Device.model;

import java.sql.Timestamp;

public class DeviceState {
	
	  //GET_DEVICE_STATE X1000
	  private Integer devicestateid;   //主键
	  private Integer usergetid;     //外键
	  
	  private Integer alllogcount; 
	  private Integer facecount;
	  private Integer facemax; 
	  private Integer fpcount; 
	  private Integer fpmax;
	  private Integer idcardcount; 
	  private Integer idcardmax; 
	  private Integer managercount; 
	  private Integer managermax; 
	  private Integer passwordcount;
	  private Integer passwordmax; 
	  private Integer pvcount; 
	  private Integer pvmax;
	  private Integer totallogcount; 
	  private Integer totallogmax; 
	  private Integer usercount; 
	  private Integer usermax;
	  private Integer isonline;
	  private Timestamp    lasttime;	
	  
	  
	public Integer getIsonline() {
		return isonline;
	}
	public void setIsonline(Integer isonline) {
		this.isonline = isonline;
	}
	
	public Timestamp getLasttime() {
		return lasttime;
	}
	public void setLasttime(Timestamp lasttime) {
		this.lasttime = lasttime;
	}
	public Integer getDevicestateid() {
		return devicestateid;
	}
	public void setDevicestateid(Integer devicestateid) {
		this.devicestateid = devicestateid;
	}
	public Integer getUsergetid() {
		return usergetid;
	}
	public void setUsergetid(Integer usergetid) {
		this.usergetid = usergetid;
	}
	public Integer getAlllogcount() {
		return alllogcount;
	}
	public void setAlllogcount(Integer alllogcount) {
		this.alllogcount = alllogcount;
	}
	public Integer getFacecount() {
		return facecount;
	}
	public void setFacecount(Integer facecount) {
		this.facecount = facecount;
	}
	public Integer getFacemax() {
		return facemax;
	}
	public void setFacemax(Integer facemax) {
		this.facemax = facemax;
	}
	public Integer getFpcount() {
		return fpcount;
	}
	public void setFpcount(Integer fpcount) {
		this.fpcount = fpcount;
	}
	public Integer getFpmax() {
		return fpmax;
	}
	public void setFpmax(Integer fpmax) {
		this.fpmax = fpmax;
	}
	public Integer getIdcardcount() {
		return idcardcount;
	}
	public void setIdcardcount(Integer idcardcount) {
		this.idcardcount = idcardcount;
	}
	public Integer getIdcardmax() {
		return idcardmax;
	}
	public void setIdcardmax(Integer idcardmax) {
		this.idcardmax = idcardmax;
	}
	public Integer getManagercount() {
		return managercount;
	}
	public void setManagercount(Integer managercount) {
		this.managercount = managercount;
	}
	public Integer getManagermax() {
		return managermax;
	}
	public void setManagermax(Integer managermax) {
		this.managermax = managermax;
	}
	public Integer getPasswordcount() {
		return passwordcount;
	}
	public void setPasswordcount(Integer passwordcount) {
		this.passwordcount = passwordcount;
	}
	public Integer getPasswordmax() {
		return passwordmax;
	}
	public void setPasswordmax(Integer passwordmax) {
		this.passwordmax = passwordmax;
	}
	public Integer getPvcount() {
		return pvcount;
	}
	public void setPvcount(Integer pvcount) {
		this.pvcount = pvcount;
	}
	public Integer getPvmax() {
		return pvmax;
	}
	public void setPvmax(Integer pvmax) {
		this.pvmax = pvmax;
	}
	public Integer getTotallogcount() {
		return totallogcount;
	}
	public void setTotallogcount(Integer totallogcount) {
		this.totallogcount = totallogcount;
	}
	public Integer getTotallogmax() {
		return totallogmax;
	}
	public void setTotallogmax(Integer totallogmax) {
		this.totallogmax = totallogmax;
	}
	public Integer getUsercount() {
		return usercount;
	}
	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
	}
	public Integer getUsermax() {
		return usermax;
	}
	public void setUsermax(Integer usermax) {
		this.usermax = usermax;
	}
	@Override
	public String toString() {
		return "DeviceState [devicestateid=" + devicestateid + ", usergetid=" + usergetid + ", alllogcount="
				+ alllogcount + ", facecount=" + facecount + ", facemax=" + facemax + ", fpcount=" + fpcount
				+ ", fpmax=" + fpmax + ", idcardcount=" + idcardcount + ", idcardmax=" + idcardmax + ", managercount="
				+ managercount + ", managermax=" + managermax + ", passwordcount=" + passwordcount + ", passwordmax="
				+ passwordmax + ", pvcount=" + pvcount + ", pvmax=" + pvmax + ", totallogcount=" + totallogcount
				+ ", totallogmax=" + totallogmax + ", usercount=" + usercount + ", usermax=" + usermax + ", isonline="
				+ isonline + ", lasttime=" + lasttime + "]";
	}

	 

}
