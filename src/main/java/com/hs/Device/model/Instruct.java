package com.hs.Device.model;

public class Instruct {
	private Integer instructid;
	private String instructname;
	
	
	public Integer getInstructid() {
		return instructid;
	}
	public void setInstructid(Integer instructid) {
		this.instructid = instructid;
	}
	
	public String getInstructname() {
		return instructname;
	}
	public void setInstruchname(String instruchname) {
		this.instructname = instruchname;
	}
	@Override
	public String toString() {
		return "Instruct [instructid=" + instructid + ", instructname="
				+ instructname + "]";
	}

	
	

}
