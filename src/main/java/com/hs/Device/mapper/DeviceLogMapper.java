package com.hs.Device.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.DeviceLog;



@Mapper
public interface DeviceLogMapper {
	
    //上传考勤记录
	int insertlog(@Param("devicelog") DeviceLog devicelog);
	
	//根据getid查询
	List<DeviceLogMapper> getlogbygetid(@Param("usergetid") Integer usergetid);
	
	//根据pkid查询
	List<DeviceLogMapper> getlogbypkid(@Param("pkid") Integer pkid);
	
	//删除某台设备所有考勤记录
	int deletealllog(@Param("devid") String devid);
	
	//查询设备所有考勤
	List<DeviceLog > loglist(@Param("devid") String devid);
	
	
}
