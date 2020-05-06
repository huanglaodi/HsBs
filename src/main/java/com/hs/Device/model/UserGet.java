package com.hs.Device.model;

import java.util.List;

//用户需要从机器获得的数据类
public class UserGet {
	
	private Integer usergetid;  //主键
	private Integer taskid;   //外键任务id
	
	private List<Users> users;
	private List<DeviceLog> logs;
	private List<DeviceState> devicestates;
	private List<Packages> packages;
	private List<DeviceInfo> deviceinfo;
	private List<DeleteUser> dlusers;
	private List<UserIdList> listuid;
	private List<DeviceSetting> devsts;
	private List<TimeZone> timezones;
	private List<UserParseTime> parsetimes;
	private List<DoorSetting> doorsettings;
	
	public List<DoorSetting> getDoorsettings() {
		return doorsettings;
	}
	public void setDoorsettings(List<DoorSetting> doorsettings) {
		this.doorsettings = doorsettings;
	}
	public List<UserParseTime> getParsetimes() {
		return parsetimes;
	}
	public void setParsetimes(List<UserParseTime> parsetimes) {
		this.parsetimes = parsetimes;
	}
	public List<TimeZone> getTimezones() {
		return timezones;
	}
	public void setTimezones(List<TimeZone> timezones) {
		this.timezones = timezones;
	}
	public List<DeviceSetting> getDevsts() {
		return devsts;
	}
	public void setDevsts(List<DeviceSetting> devsts) {
		this.devsts = devsts;
	}
	public Integer getUsergetid() {
		return usergetid;
	}
	public void setUsergetid(Integer usergetid) {
		this.usergetid = usergetid;
	}
	public Integer getTaskid() {
		return taskid;
	}
	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public List<DeviceLog> getLogs() {
		return logs;
	}
	public void setLogs(List<DeviceLog> logs) {
		this.logs = logs;
	}
	public List<DeviceState> getDevicestates() {
		return devicestates;
	}
	public void setDevicestates(List<DeviceState> devicestates) {
		this.devicestates = devicestates;
	}
	public List<Packages> getPackages() {
		return packages;
	}
	public void setPackages(List<Packages> packages) {
		this.packages = packages;
	}

	public List<DeviceInfo> getDeviceinfo() {
		return deviceinfo;
	}
	public void setDeviceinfo(List<DeviceInfo> deviceinfo) {
		this.deviceinfo = deviceinfo;
	}
	public List<DeleteUser> getDlusers() {
		return dlusers;
	}
	public void setDlusers(List<DeleteUser> dlusers) {
		this.dlusers = dlusers;
	}
	public List<UserIdList> getListuid() {
		return listuid;
	}
	public void setListuid(List<UserIdList> listuid) {
		this.listuid = listuid;
	}
	@Override
	public String toString() {
		return "UserGet [usergetid=" + usergetid + ", taskid=" + taskid + ", users=" + users + ", logs=" + logs
				+ ", devicestates=" + devicestates + ", packages=" + packages + ", deviceinfo=" + deviceinfo
				+ ", dlusers=" + dlusers + ", listuid=" + listuid + ", devsts=" + devsts + ", timezones=" + timezones
				+ ", parsetimes=" + parsetimes + ", doorsettings=" + doorsettings + "]";
	}



	
	
}
