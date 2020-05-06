package com.hs.Device.model;

public class TimeZone {
	
	private Integer timezoneid;
	private Integer usersendid;
	private Integer usergetid;
	
	private String timezoneno;
	private String t1;
	private String t2;
	private String t3;
	private String t4;
	private String t5;
	private String t6;
	
	public Integer getUsersendid() {
		return usersendid;
	}
	public void setUsersendid(Integer usersendid) {
		this.usersendid = usersendid;
	}
	public Integer getUsergetid() {
		return usergetid;
	}
	public void setUsergetid(Integer usergetid) {
		this.usergetid = usergetid;
	}
	public Integer getTimezoneid() {
		return timezoneid;
	}
	public void setTimezoneid(Integer timezoneid) {
		this.timezoneid = timezoneid;
	}
	
	public String getTimezoneno() {
		return timezoneno;
	}
	public void setTimezoneno(String timezoneno) {
		this.timezoneno = timezoneno;
	}
	public String getT1() {
		return t1;
	}
	public void setT1(String t1) {
		this.t1 = t1;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}
	public String getT3() {
		return t3;
	}
	public void setT3(String t3) {
		this.t3 = t3;
	}
	public String getT4() {
		return t4;
	}
	public void setT4(String t4) {
		this.t4 = t4;
	}
	public String getT5() {
		return t5;
	}
	public void setT5(String t5) {
		this.t5 = t5;
	}
	public String getT6() {
		return t6;
	}
	public void setT6(String t6) {
		this.t6 = t6;
	}
	@Override
	public String toString() {
		return "TimeZone [timezoneid=" + timezoneid + ", usersendid=" + usersendid + ", usergetid=" + usergetid
				+ ", timezoneno=" + timezoneno + ", t1=" + t1 + ", t2=" + t2 + ", t3=" + t3 + ", t4=" + t4 + ", t5="
				+ t5 + ", t6=" + t6 + "]";
	}

	

}
