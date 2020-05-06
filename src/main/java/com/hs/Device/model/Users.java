package com.hs.Device.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Users {


    @JSONField(serialize = false)
	private Integer usid;

    @JSONField(serialize = false)
	private Integer usersendid;
    
    @JSONField(serialize = false)
	private Integer usergetid;

    @JSONField(serialize = false)
	private String devid;
    
    private Integer pkid;
    
    @JSONField(name="userId")
	private String userid;
    
	private String name;
	
	@JSONField(serialize = false)
	private String privilege;
	
	@JSONField(name="privilege")
	private Integer pri_v536;
	
	private String userphoto;
	
	@JsonInclude(Include.NON_NULL)
	private String card;
	
	@JsonInclude(Include.NON_NULL)
	private String pwd;
	
	@JSONField(serialize = false)
	private byte[] face;
	
	@JSONField(name="face")
	private String face_base64;
	
	@JSONField(serialize = false)
	private byte[] palm;
	
	@JSONField(name="palm")
	private String palm_base64;
	
	@JSONField(serialize = false)
	private byte[] photo;
	
	@JSONField(name="photo")
	private String photo_base64;
	
	@JSONField(serialize = false)
	private Timestamp vaildstart;
	
	@JSONField(name = "vaildStart")
	private String starttime;
	
	@JSONField(serialize = false)
	private Timestamp vaildend;
	
	@JSONField(name = "vaildEnd")
	private String endtime;
	
	@JSONField(name="update")
	private Integer updates;
	
	private Integer photoEnroll;
	
    @JSONField(serialize = false)
	private byte[] data;
    
    @JSONField(serialize = false)
    private String[] data_base64;
    
    @JSONField(serialize = false)
	private List<Fps> listfps;

    @JSONField(serialize = false)
	private List<DeviceEnrollArray> enrollarrays;

    private List<String> fps;
    
	public List<String> getFps() {
		return fps;
	}

	public void setFps(List<String> fps) {
		this.fps = fps;
	}

	public Integer getPhotoEnroll() {
		return photoEnroll;
	}

	public void setPhotoEnroll(Integer photoEnroll) {
		this.photoEnroll = photoEnroll;
	}

	public Integer getPri_v536() {
		return pri_v536;
	}

	public void setPri_v536(Integer pri_v536) {
		this.pri_v536 = pri_v536;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getFace_base64() {
		return face_base64;
	}

	public void setFace_base64(String face_base64) {
		this.face_base64 = face_base64;
	}

	public String getPalm_base64() {
		return palm_base64;
	}

	public void setPalm_base64(String palm_base64) {
		this.palm_base64 = palm_base64;
	}

	public String getPhoto_base64() {
		return photo_base64;
	}

	public void setPhoto_base64(String photo_base64) {
		this.photo_base64 = photo_base64;
	}

	public String[] getData_base64() {
		return data_base64;
	}

	public void setData_base64(String[] data_base64) {
		this.data_base64 = data_base64;
	}

	public Integer getUsid() {
		return usid;
	}

	public void setUsid(Integer usid) {
		this.usid = usid;
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

	public String getDevid() {
		return devid;
	}

	public void setDevid(String devid) {
		this.devid = devid;
	}

	public Integer getPkid() {
		return pkid;
	}

	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUserphoto() {
		return userphoto;
	}

	public void setUserphoto(String userphoto) {
		this.userphoto = userphoto;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public byte[] getFace() {
		return face;
	}

	public void setFace(byte[] face) {
		this.face = face;
	}

	public byte[] getPalm() {
		return palm;
	}

	public void setPalm(byte[] palm) {
		this.palm = palm;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}


	public Timestamp getVaildstart() {
		return vaildstart;
	}

	public void setVaildstart(Timestamp vaildstart) {
		this.vaildstart = vaildstart;
	}

	public Timestamp getVaildend() {
		return vaildend;
	}

	public void setVaildend(Timestamp vaildend) {
		this.vaildend = vaildend;
	}

	public Integer getUpdates() {
		return updates;
	}

	public void setUpdates(Integer updates) {
		this.updates = updates;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public List<Fps> getListfps() {
		return listfps;
	}

	public void setListfps(List<Fps> listfps) {
		this.listfps = listfps;
	}

	public List<DeviceEnrollArray> getEnrollarrays() {
		return enrollarrays;
	}

	public void setEnrollarrays(List<DeviceEnrollArray> enrollarrays) {
		this.enrollarrays = enrollarrays;
	}

	@Override
	public String toString() {
		return "Users [usid=" + usid + ", usersendid=" + usersendid + ", usergetid=" + usergetid + ", devid=" + devid
				+ ", pkid=" + pkid + ", userid=" + userid + ", name=" + name + ", privilege=" + privilege
				+ ", pri_v536=" + pri_v536 + ", userphoto=" + userphoto + ", card=" + card + ", pwd=" + pwd + ", face="
				+ Arrays.toString(face) + ", face_base64=" + face_base64 + ", palm=" + Arrays.toString(palm)
				+ ", palm_base64=" + palm_base64 + ", photo=" + Arrays.toString(photo) + ", photo_base64="
				+ photo_base64 + ", vaildstart=" + vaildstart + ", starttime=" + starttime + ", vaildend=" + vaildend
				+ ", endtime=" + endtime + ", updates=" + updates + ", photoEnroll=" + photoEnroll + ", data="
				+ Arrays.toString(data) + ", data_base64=" + Arrays.toString(data_base64) + ", listfps=" + listfps
				+ ", enrollarrays=" + enrollarrays + ", fps=" + fps + "]";
	}


   
    
}