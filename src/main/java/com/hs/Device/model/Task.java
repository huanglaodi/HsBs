package com.hs.Device.model;

import java.sql.Timestamp;
import java.util.List;

public class Task {

	private Integer taskid;
	private Timestamp starttime;
	private Timestamp endtime;
	private Integer userid;
	private Integer instructid;
	private String state;
	private String deviceid;
	private Integer issend;
	
	private User user;       //用户
	private Device device;    //设备
	private Instruct instruct;//指令
	private List<UserSend> usersends;//发送的body
	private List<UserGet> usergets;  //接收的body
	
	public List<UserGet> getUsergets() {
		return usergets;
	}
	public void setUsergets(List<UserGet> usergets) {
		this.usergets = usergets;
	}

	public Integer getIssend() {
		return issend;
	}
	public void setIssend(Integer issend) {
		this.issend = issend;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Instruct getInstruct() {
		return instruct;
	}
	public void setInstruct(Instruct instruct) {
		this.instruct = instruct;
	}
	public Integer getTaskid() {
		return taskid;
	}
	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}
	public Timestamp getStarttime() {
		return starttime;
	}
	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}
	public Timestamp getEndtime() {
		return endtime;
	}
	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getInstructid() {
		return instructid;
	}
	public void setInstructid(Integer instructid) {
		this.instructid = instructid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public List<UserSend> getUsersends() {
		return usersends;
	}
	public void setUsersends(List<UserSend> usersends) {
		this.usersends = usersends;
	}
	@Override
	public String toString() {
		return "Task [taskid=" + taskid + ", starttime=" + starttime + ", endtime=" + endtime + ", userid=" + userid
				+ ", instructid=" + instructid + ", state=" + state + ", deviceid=" + deviceid + ", issend=" + issend
				+ ", user=" + user + ", device=" + device + ", instruct=" + instruct + ", usersends=" + usersends
				+ ", usergets=" + usergets + "]";
	}

	
	

	
	
}
