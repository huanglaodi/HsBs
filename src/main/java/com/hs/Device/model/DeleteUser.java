package com.hs.Device.model;

public class DeleteUser {
	
	private Integer deleteid;  //主键
	private Integer usergetid;  //外键
	
	
	private String usersid;


	public Integer getDeleteid() {
		return deleteid;
	}


	public void setDeleteid(Integer deleteid) {
		this.deleteid = deleteid;
	}


	public Integer getUsergetid() {
		return usergetid;
	}


	public void setUsergetid(Integer usergetid) {
		this.usergetid = usergetid;
	}


	public String getUsersid() {
		return usersid;
	}


	public void setUsersid(String usersid) {
		this.usersid = usersid;
	}  
	
	

}
