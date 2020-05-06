package com.hs.Device.model;

public class UserParseTime {
	
	private Integer parsetimeid;
	private Integer usersendid;
	private Integer usergetid;
	
	private String userid;
	private String validedatestart;
	private String validedateend;
	private String weekno;
	public Integer getParsetimeid() {
		return parsetimeid;
	}
	public void setParsetimeid(Integer parsetimeid) {
		this.parsetimeid = parsetimeid;
	}
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getValidedatestart() {
		return validedatestart;
	}
	public void setValidedatestart(String validedatestart) {
		this.validedatestart = validedatestart;
	}
	public String getValidedateend() {
		return validedateend;
	}
	public void setValidedateend(String validedateend) {
		this.validedateend = validedateend;
	}
	public String getWeekno() {
		return weekno;
	}
	public void setWeekno(String weekno) {
		this.weekno = weekno;
	}
	@Override
	public String toString() {
		return "UserParseTime [parsetimeid=" + parsetimeid + ", usersendid=" + usersendid + ", usergetid=" + usergetid
				+ ", userid=" + userid + ", validedatestart=" + validedatestart + ", validedateend=" + validedateend
				+ ", weekno=" + weekno + "]";
	}
	
	

}
