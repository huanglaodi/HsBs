package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.TimeZone;
import com.hs.Device.model.UserParseTime;


@Mapper
public interface UserParseTimeMapper {
	

	int insertuserparsetime(@Param("userparsetime") UserParseTime userparsetime);
	
	List<TimeZone> selectbygetid(@Param("usergetid") Integer usergetid);
	
	List<TimeZone> selectbysendid(@Param("usersendid") Integer usersendid);

}
