package com.hs.Device.model;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceEnrollArray {

	@JSONField(serialize = false)
	private Integer enrollarrayid;
	@JSONField(name = "backup_number")
	private Integer backupnumber;
	@JSONField(name = "enroll_data")
	private String enrolldata;
	@JSONField(serialize = false)
	private Integer usid;   //实时上传外键
	@JSONField(serialize = false)
	private Integer usersendid;   //用户发送的外键
	@JSONField(serialize = false)
	private Integer usergetid;    //机器回应外键
	
	//机器发送的人员数据类与enroll_data_array(此类)一对多
	@JSONField(serialize = false)
	private Users users;
	@JSONField(serialize = false)
	private UserSend usersend;
	@JSONField(serialize = false)
	private UserGet userget;
	

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
	public UserSend getUsersend() {
		return usersend;
	}
	public void setUsersend(UserSend usersend) {
		this.usersend = usersend;
	}
	public UserGet getUserget() {
		return userget;
	}
	public void setUserget(UserGet userget) {
		this.userget = userget;
	}
	
	
	public Integer getEnrollarrayid() {
		return enrollarrayid;
	}
	public void setEnrollarrayid(Integer enrollarrayid) {
		this.enrollarrayid = enrollarrayid;
	}
	public Integer getBackupnumber() {
		return backupnumber;
	}
	public void setBackupnumber(Integer backupnumber) {
		this.backupnumber = backupnumber;
	}
	public String getEnrolldata() {
		return enrolldata;
	}
	public void setEnrolldata(String enrolldata) {
		this.enrolldata = enrolldata;
	}
	
	public Integer getUsid() {
		return usid;
	}
	public void setUsid(Integer usid) {
		this.usid = usid;
	}
	
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "DeviceEnrollArray [enrollarrayid=" + enrollarrayid + ", backupnumber=" + backupnumber + ", enrolldata="
				+ enrolldata + ", usid=" + usid + ", usersendid=" + usersendid + ", usergetid=" + usergetid + ", users="
				+ users + ", usersend=" + usersend + ", userget=" + userget + "]";
	}
	
	


}
