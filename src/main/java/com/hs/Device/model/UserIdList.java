package com.hs.Device.model;

import java.util.Arrays;

//获取人员列表
public class UserIdList {
	
	private Integer useridlistid;  //主键
	private Integer usergetid;   //外键
	private Integer pkid;      //外键
	
	private Integer userscount;
	private String name;
	private String userId;
	private String oneuseridsize;
	private String useridarray;
	private byte[] data;
	private String data_base64;
	
	
	public String getData_base64() {
		return data_base64;
	}
	public void setData_base64(String data_base64) {
		this.data_base64 = data_base64;
	}
	public String getOneuseridsize() {
		return oneuseridsize;
	}
	public void setOneuseridsize(String oneuseridsize) {
		this.oneuseridsize = oneuseridsize;
	}
	public String getUseridarray() {
		return useridarray;
	}
	public void setUseridarray(String useridarray) {
		this.useridarray = useridarray;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public Integer getUseridlistid() {
		return useridlistid;
	}
	public void setUseridlistid(Integer useridlistid) {
		this.useridlistid = useridlistid;
	}
	public Integer getUsergetid() {
		return usergetid;
	}
	public void setUsergetid(Integer usergetid) {
		this.usergetid = usergetid;
	}
	public Integer getUserscount() {
		return userscount;
	}
	public void setUserscount(Integer userscount) {
		this.userscount = userscount;
	}
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "UserIdList [useridlistid=" + useridlistid + ", usergetid=" + usergetid + ", pkid=" + pkid
				+ ", userscount=" + userscount + ", name=" + name + ", userId=" + userId + ", oneuseridsize="
				+ oneuseridsize + ", useridarray=" + useridarray + ", data=" + Arrays.toString(data) + ", data_base64="
				+ data_base64 + "]";
	}



	
	

}
