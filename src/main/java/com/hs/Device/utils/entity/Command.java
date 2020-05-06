package com.hs.Device.utils.entity;

public class Command {
	private String TransId;
	private String CmdCode;
	private byte[] bytCmd;
	private String sResponse;
	private String jsonstr;
	
	public Command() {
		TransId = "";
		CmdCode = "";
		setJsonstr("");
		sResponse = "null";
		bytCmd = new byte[0];
	}
	public Command(String transId, String cmdCode, byte[] bytCmd, String sResponse) {
		TransId = transId;
		CmdCode = cmdCode;
		this.bytCmd = bytCmd;
		this.sResponse = sResponse;
		setJsonstr("");
	}
	public String getTransId() {
		return TransId;
	}
	public void setTransId(String transId) {
		TransId = transId;
	}
	public String getCmdCode() {
		return CmdCode;
	}
	public void setCmdCode(String cmdCode) {
		CmdCode = cmdCode;
	}
	public byte[] getBytCmd() {
		return bytCmd;
	}
	public void setBytCmd(byte[] bytCmd) {
		if(bytCmd == null)
			this.bytCmd = new byte[0];
		else
			this.bytCmd = bytCmd;
	}
	public String getsResponse() {
		return sResponse;
	}
	public void setsResponse(String sResponse) {
		this.sResponse = sResponse;
	}
	public String getJsonstr() {
		return jsonstr;
	}
	public void setJsonstr(String jsonstr) {
		this.jsonstr = jsonstr;
	}
	public void clear(){
		TransId = "";
		CmdCode = "";
		setJsonstr("");
		sResponse = "null";
		bytCmd = new byte[0];
	}
}
