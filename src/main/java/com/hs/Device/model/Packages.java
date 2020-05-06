package com.hs.Device.model;

import java.util.List;

public class Packages {
	private Integer pkid;//主键
	private Integer usergetid;//外键
	
	private Integer packageid;  
	//logs
	private Integer alllogcount;
	private Integer logscount;
	private List<DeviceLog> logs;   //一个包对应多个日志
	
	//users
	private Integer userscount;
	private List<Users> userslist;   //一个包对应多个人员
	
	//useridlist
	private List<UserIdList> lsuid;    //一个包对应多个userIDlist；
	

	public List<UserIdList> getLsuid() {
		return lsuid;
	}
	public void setLsuid(List<UserIdList> lsuid) {
		this.lsuid = lsuid;
	}
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public Integer getUsergetid() {
		return usergetid;
	}
	public void setUsergetid(Integer usergetid) {
		this.usergetid = usergetid;
	}
	public Integer getPackageid() {
		return packageid;
	}
	public void setPackageid(Integer packageid) {
		this.packageid = packageid;
	}
	public Integer getAlllogcount() {
		return alllogcount;
	}
	public void setAlllogcount(Integer alllogcount) {
		this.alllogcount = alllogcount;
	}
	public Integer getLogscount() {
		return logscount;
	}
	public void setLogscount(Integer logscount) {
		this.logscount = logscount;
	}
	public List<DeviceLog> getLogs() {
		return logs;
	}
	public void setLogs(List<DeviceLog> logs) {
		this.logs = logs;
	}
	public Integer getUserscount() {
		return userscount;
	}
	public void setUserscount(Integer userscount) {
		this.userscount = userscount;
	}
	public List<Users> getUserslist() {
		return userslist;
	}
	public void setUserslist(List<Users> userslist) {
		this.userslist = userslist;
	}
	@Override
	public String toString() {
		return "Packages [pkid=" + pkid + ", usergetid=" + usergetid + ", packageid=" + packageid + ", alllogcount="
				+ alllogcount + ", logscount=" + logscount + ", logs=" + logs + ", userscount=" + userscount
				+ ", userslist=" + userslist + ", lsuid=" + lsuid + "]";
	}


	
	

}
