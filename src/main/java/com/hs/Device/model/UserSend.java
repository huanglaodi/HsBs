package com.hs.Device.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

//用户发送给机器的数据类
public class UserSend {
	private Integer usersendid;   //主键
	private Integer taskid;      //外键
	private Timestamp time;
	private Timestamp begintime;
	private Timestamp endtime;
	private String userid;
	private String username;
	private String userprivilege;
	private byte[] userphoto;
	private String userphoto_base64;
	private String fkname;
	private String serverip;
	private Integer serverport;
	private String firmwarefilename;
	private String firmwarebindata;
	private byte[] data;
	private String data_base64;
	private Integer backupnumber;
	private String timezoneno;
	private List<TimeZone> listtimezone;
	private List<UserParseTime> listparsetime;
	private List<DoorSetting> listdoorsetting;
	private List<DoorStatus>  listdoorstatus;
	//V536添加
	private String usersid;
	private Integer userscount;
	private String firmwarefileurl;
	private String feature;
	private String paramskey;
	private String paramsval;
	
	//EnrollArray
	private List<DeviceEnrollArray> listenroll;
	private List<Users> listusers;
	

	public List<DoorStatus> getListdoorstatus() {
		return listdoorstatus;
	}

	public void setListdoorstatus(List<DoorStatus> listdoorstatus) {
		this.listdoorstatus = listdoorstatus;
	}

	public List<DoorSetting> getListdoorsetting() {
		return listdoorsetting;
	}

	public void setListdoorsetting(List<DoorSetting> listdoorsetting) {
		this.listdoorsetting = listdoorsetting;
	}

	public List<UserParseTime> getListparsetime() {
		return listparsetime;
	}

	public void setListparsetime(List<UserParseTime> listparsetime) {
		this.listparsetime = listparsetime;
	}

	public String getTimezoneno() {
		return timezoneno;
	}

	public void setTimezoneno(String timezoneno) {
		this.timezoneno = timezoneno;
	}

	public List<TimeZone> getListtimezone() {
		return listtimezone;
	}

	public void setListtimezone(List<TimeZone> listtimezone) {
		this.listtimezone = listtimezone;
	}

	public Integer getBackupnumber() {
		return backupnumber;
	}

	public void setBackupnumber(Integer backupnumber) {
		this.backupnumber = backupnumber;
	}

	public String getUserphoto_base64() {
		return userphoto_base64;
	}

	public void setUserphoto_base64(String userphoto_base64) {
		this.userphoto_base64 = userphoto_base64;
	}

	public String getData_base64() {
		return data_base64;
	}

	public void setData_base64(String data_base64) {
		this.data_base64 = data_base64;
	}
	public String getUsersid() {
		return usersid;
	}

	public void setUsersid(String usersid) {
		this.usersid = usersid;
	}

	public Integer getUserscount() {
		return userscount;
	}

	public void setUserscount(Integer userscount) {
		this.userscount = userscount;
	}

	public String getFirmwarefileurl() {
		return firmwarefileurl;
	}

	public void setFirmwarefileurl(String firmwarefileurl) {
		this.firmwarefileurl = firmwarefileurl;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getParamskey() {
		return paramskey;
	}

	public void setParamskey(String paramskey) {
		this.paramskey = paramskey;
	}

	public String getParamsval() {
		return paramsval;
	}

	public void setParamsval(String paramsval) {
		this.paramsval = paramsval;
	}

	public List<Users> getListusers() {
		return listusers;
	}

	public void setListusers(List<Users> listusers) {
		this.listusers = listusers;
	}

	public Integer getUsersendid() {
		return usersendid;
	}

	public void setUsersendid(Integer usersendid) {
		this.usersendid = usersendid;
	}

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Timestamp getBegintime() {
		return begintime;
	}

	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserprivilege() {
		return userprivilege;
	}

	public void setUserprivilege(String userprivilege) {
		this.userprivilege = userprivilege;
	}

	public byte[] getUserphoto() {
		return userphoto;
	}

	public void setUserphoto(byte[] userphoto) {
		this.userphoto = userphoto;
	}

	public String getFkname() {
		return fkname;
	}

	public void setFkname(String fkname) {
		this.fkname = fkname;
	}

	public String getServerip() {
		return serverip;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	public Integer getServerport() {
		return serverport;
	}

	public void setServerport(Integer serverport) {
		this.serverport = serverport;
	}


	public String getFirmwarefilename() {
		return firmwarefilename;
	}

	public void setFirmwarefilename(String firmwarefilename) {
		this.firmwarefilename = firmwarefilename;
	}

	public String getFirmwarebindata() {
		return firmwarebindata;
	}

	public void setFirmwarebindata(String firmwarebindata) {
		this.firmwarebindata = firmwarebindata;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public List<DeviceEnrollArray> getListenroll() {
		return listenroll;
	}

	public void setListenroll(List<DeviceEnrollArray> listenroll) {
		this.listenroll = listenroll;
	}

	@Override
	public String toString() {
		return "UserSend [usersendid=" + usersendid + ", taskid=" + taskid + ", time=" + time + ", begintime="
				+ begintime + ", endtime=" + endtime + ", userid=" + userid + ", username=" + username
				+ ", userprivilege=" + userprivilege + ", userphoto=" + Arrays.toString(userphoto)
				+ ", userphoto_base64=" + userphoto_base64 + ", fkname=" + fkname + ", serverip=" + serverip
				+ ", serverport=" + serverport + ", firmwarefilename=" + firmwarefilename + ", firmwarebindata="
				+ firmwarebindata + ", data=" + Arrays.toString(data) + ", data_base64=" + data_base64
				+ ", backupnumber=" + backupnumber + ", timezoneno=" + timezoneno + ", listtimezone=" + listtimezone
				+ ", listparsetime=" + listparsetime + ", listdoorsetting=" + listdoorsetting + ", listdoorstatus="
				+ listdoorstatus + ", usersid=" + usersid + ", userscount=" + userscount + ", firmwarefileurl="
				+ firmwarefileurl + ", feature=" + feature + ", paramskey=" + paramskey + ", paramsval=" + paramsval
				+ ", listenroll=" + listenroll + ", listusers=" + listusers + "]";
	}






}
