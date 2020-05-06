package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.DoorStatus;

// /V536
@Mapper
public interface DoorStatusMapper {
	
	
	int insertdoorst(@Param("doorstatus") DoorStatus doorstatus);
	
	//根据usersendid查询
	List<DoorStatus> selectbysendid(@Param("usersendid") Integer usersendid);

}
