package com.hs.Device.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.DeviceEnrollArray;


//X1000E
@Mapper
public interface DeviceEnrollArrayMapper {
    //实时上传
	int insertEnroll(@Param("deviceEnroll") DeviceEnrollArray deviceEnroll);
	 
	List<DeviceEnrollArray> selectbyusid(@Param("usid") Integer usid);
	
	int deletebyid(@Param("usid") Integer usid);
	 
	int deleteallenroll();
	
	//send
	List<DeviceEnrollArray> selectbysendid(@Param("usersendid") Integer usersendid);
	int insertsendenroll(@Param("enrollarray") DeviceEnrollArray enrollarray);
	
	//get
	List<DeviceEnrollArray> selectbygetid(@Param("usergetid") Integer usergetid);
	int insertgetenroll(@Param("enrollarray") DeviceEnrollArray enrollarray);
	 
}
