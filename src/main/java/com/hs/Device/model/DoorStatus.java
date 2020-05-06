package com.hs.Device.model;

public class DoorStatus {

	private Integer doorstatusid;
	private Integer usersendid;
	private String doorstatus;
	public Integer getDoorstatusid() {
		return doorstatusid;
	}
	public void setDoorstatusid(Integer doorstatusid) {
		this.doorstatusid = doorstatusid;
	}
	
	public Integer getUsersendid() {
		return usersendid;
	}
	public void setUsersendid(Integer usersendid) {
		this.usersendid = usersendid;
	}
	public String getDoorstatus() {
		return doorstatus;
	}
	public void setDoorstatus(String doorstatus) {
		this.doorstatus = doorstatus;
	}
	@Override
	public String toString() {
		return "DoorStatus [doorstatusid=" + doorstatusid + ", usersendid=" + usersendid + ", doorstatus=" + doorstatus
				+ "]";
	}

	
	
}
