package com.hs.Device.model;

import java.sql.Timestamp;

public class DeviceInfo {
	
	private Integer deviceid;  //主键
	private Integer usergetid;   //外键
	
	private String devid;
	private String devmodel;
	private String token;
	private String devname;
	private String firmware;
	private String fpver;
	private String facever;
	private String pvver;
	private Integer maxbufferlen;
	private Integer userlimit;
	private Integer fplimit;
	private Integer facelimit;
	private Integer pwdlimit;
	private Integer cardlimit;
	private Integer loglimit;
	private Integer usercount;
	private Integer managercount;
	private Integer fpcount;
	private Integer facecount;
	private Integer pwdcount;
	private Integer cardcount;
	private Integer logcount;
	private Integer allLogCount;
	private Integer isonline;
	private Timestamp  lasttime;
	
	
	public Integer getAllLogCount() {
		return allLogCount;
	}
	public void setAllLogCount(Integer allLogCount) {
		this.allLogCount = allLogCount;
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
	public Integer getUsergetid() {
		return usergetid;
	}
	public void setUsergetid(Integer usergetid) {
		this.usergetid = usergetid;
	}
	public String getDevid() {
		return devid;
	}
	public void setDevid(String devid) {
		this.devid = devid;
	}
	
	public String getDevmodel() {
		return devmodel;
	}
	public void setDevmodel(String devmodel) {
		this.devmodel = devmodel;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDevname() {
		return devname;
	}
	public void setDevname(String devname) {
		this.devname = devname;
	}
	public String getFirmware() {
		return firmware;
	}
	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}
	public String getFpver() {
		return fpver;
	}
	public void setFpver(String fpver) {
		this.fpver = fpver;
	}
	public String getFacever() {
		return facever;
	}
	public void setFacever(String facever) {
		this.facever = facever;
	}
	public String getPvver() {
		return pvver;
	}
	public void setPvver(String pvver) {
		this.pvver = pvver;
	}
	public Integer getMaxbufferlen() {
		return maxbufferlen;
	}
	public void setMaxbufferlen(Integer maxbufferlen) {
		this.maxbufferlen = maxbufferlen;
	}
	public Integer getUserlimit() {
		return userlimit;
	}
	public void setUserlimit(Integer userlimit) {
		this.userlimit = userlimit;
	}
	public Integer getFplimit() {
		return fplimit;
	}
	public void setFplimit(Integer fplimit) {
		this.fplimit = fplimit;
	}
	public Integer getFacelimit() {
		return facelimit;
	}
	public void setFacelimit(Integer facelimit) {
		this.facelimit = facelimit;
	}
	public Integer getPwdlimit() {
		return pwdlimit;
	}
	public void setPwdlimit(Integer pwdlimit) {
		this.pwdlimit = pwdlimit;
	}
	public Integer getCardlimit() {
		return cardlimit;
	}
	public void setCardlimit(Integer cardlimit) {
		this.cardlimit = cardlimit;
	}
	public Integer getLoglimit() {
		return loglimit;
	}
	public void setLoglimit(Integer loglimit) {
		this.loglimit = loglimit;
	}
	public Integer getUsercount() {
		return usercount;
	}
	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
	}
	public Integer getManagercount() {
		return managercount;
	}
	public void setManagercount(Integer managercount) {
		this.managercount = managercount;
	}
	public Integer getFpcount() {
		return fpcount;
	}
	public void setFpcount(Integer fpcount) {
		this.fpcount = fpcount;
	}
	public Integer getFacecount() {
		return facecount;
	}
	public void setFacecount(Integer facecount) {
		this.facecount = facecount;
	}
	public Integer getPwdcount() {
		return pwdcount;
	}
	public void setPwdcount(Integer pwdcount) {
		this.pwdcount = pwdcount;
	}
	public Integer getCardcount() {
		return cardcount;
	}
	public void setCardcount(Integer cardcount) {
		this.cardcount = cardcount;
	}
	public Integer getLogcount() {
		return logcount;
	}
	public void setLogcount(Integer logcount) {
		this.logcount = logcount;
	}
	public Integer getIsonline() {
		return isonline;
	}
	public void setIsonline(Integer isonline) {
		this.isonline = isonline;
	}
	@Override
	public String toString() {
		return "DeviceInfo [deviceid=" + deviceid + ", usergetid=" + usergetid + ", devid=" + devid + ", devmodel="
				+ devmodel + ", token=" + token + ", devname=" + devname + ", firmware=" + firmware + ", fpver=" + fpver
				+ ", facever=" + facever + ", pvver=" + pvver + ", maxbufferlen=" + maxbufferlen + ", userlimit="
				+ userlimit + ", fplimit=" + fplimit + ", facelimit=" + facelimit + ", pwdlimit=" + pwdlimit
				+ ", cardlimit=" + cardlimit + ", loglimit=" + loglimit + ", usercount=" + usercount + ", managercount="
				+ managercount + ", fpcount=" + fpcount + ", facecount=" + facecount + ", pwdcount=" + pwdcount
				+ ", cardcount=" + cardcount + ", logcount=" + logcount + ", allLogCount=" + allLogCount + ", isonline="
				+ isonline + ", lasttime=" + lasttime + "]";
	}



	



	
	
	

}
