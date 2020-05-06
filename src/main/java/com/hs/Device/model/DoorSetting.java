package com.hs.Device.model;

import com.alibaba.fastjson.annotation.JSONField;

//门禁机门禁参数
public class DoorSetting {

	@JSONField(serialize  = false)
	private Integer doorsettingid;
	@JSONField(serialize  = false)
	private Integer usersendid;
	@JSONField(serialize  = false)
	private Integer usergetid;
	
	@JSONField(name="OpenDoor_Delay")
	private String openDoor_Delay;
	@JSONField(name="DoorMagnetic_Type")
	private String  doorMagnetic_Type;
	@JSONField(name="DoorMagnetic_Delay")
	private String doorMagnetic_Delay;
	@JSONField(name="Anti-back")
	private  String anti_back;
	@JSONField(name="Alarm_Delay")
	private String alarm_Delay;
	@JSONField(name="Use_Alarm")
	private String use_Alarm;
	@JSONField(name="Wiegand_Type")
	private String wiegand_Type;
	@JSONField(name="Sleep_Time")
	private String sleep_Time;
	@JSONField(name="Screensavers_Time")
	private String screensavers_Time;
	@JSONField(name="Reverify_Time")
	private String reverify_Time;
	@JSONField(name="Glog_Warning")
	private String glog_Warning;
	@JSONField(name="Volume")
	private String volume;
	public Integer getDoorsettingid() {
		return doorsettingid;
	}
	public void setDoorsettingid(Integer doorsettingid) {
		this.doorsettingid = doorsettingid;
	}
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

	public String getDoorMagnetic_Type() {
		return doorMagnetic_Type;
	}
	public void setDoorMagnetic_Type(String doorMagnetic_Type) {
		this.doorMagnetic_Type = doorMagnetic_Type;
	}

	public String getAnti_back() {
		return anti_back;
	}
	public void setAnti_back(String anti_back) {
		this.anti_back = anti_back;
	}

	public String getUse_Alarm() {
		return use_Alarm;
	}
	public void setUse_Alarm(String use_Alarm) {
		this.use_Alarm = use_Alarm;
	}
	public String getWiegand_Type() {
		return wiegand_Type;
	}
	public void setWiegand_Type(String wiegand_Type) {
		this.wiegand_Type = wiegand_Type;
	}

	public String getOpenDoor_Delay() {
		return openDoor_Delay;
	}
	public void setOpenDoor_Delay(String openDoor_Delay) {
		this.openDoor_Delay = openDoor_Delay;
	}
	public String getDoorMagnetic_Delay() {
		return doorMagnetic_Delay;
	}
	public void setDoorMagnetic_Delay(String doorMagnetic_Delay) {
		this.doorMagnetic_Delay = doorMagnetic_Delay;
	}
	public String getAlarm_Delay() {
		return alarm_Delay;
	}
	public void setAlarm_Delay(String alarm_Delay) {
		this.alarm_Delay = alarm_Delay;
	}
	public String getSleep_Time() {
		return sleep_Time;
	}
	public void setSleep_Time(String sleep_Time) {
		this.sleep_Time = sleep_Time;
	}
	public String getScreensavers_Time() {
		return screensavers_Time;
	}
	public void setScreensavers_Time(String screensavers_Time) {
		this.screensavers_Time = screensavers_Time;
	}
	public String getReverify_Time() {
		return reverify_Time;
	}
	public void setReverify_Time(String reverify_Time) {
		this.reverify_Time = reverify_Time;
	}
	public String getGlog_Warning() {
		return glog_Warning;
	}
	public void setGlog_Warning(String glog_Warning) {
		this.glog_Warning = glog_Warning;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	@Override
	public String toString() {
		return "DoorSetting [doorsettingid=" + doorsettingid + ", usersendid=" + usersendid + ", usergetid=" + usergetid
				+ ", openDoor_Delay=" + openDoor_Delay + ", doorMagnetic_Type=" + doorMagnetic_Type
				+ ", doorMagnetic_Delay=" + doorMagnetic_Delay + ", anti_back=" + anti_back + ", alarm_Delay="
				+ alarm_Delay + ", use_Alarm=" + use_Alarm + ", wiegand_Type=" + wiegand_Type + ", sleep_Time="
				+ sleep_Time + ", screensavers_Time=" + screensavers_Time + ", reverify_Time=" + reverify_Time
				+ ", glog_Warning=" + glog_Warning + ", volume=" + volume + "]";
	}
	
	
	
	
}
