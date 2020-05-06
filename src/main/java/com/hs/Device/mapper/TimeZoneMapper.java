package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.DeleteUser;
import com.hs.Device.model.TimeZone;


@Mapper
public interface TimeZoneMapper {
	

	int inserttimezone(@Param("timezone") TimeZone timezone);
	
	List<TimeZone> selectbygetid(@Param("usergetid") Integer usergetid);
	
	List<TimeZone> selectbysendid(@Param("usersendid") Integer usersendid);

}
