package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.DoorSetting;
import com.hs.Device.model.TimeZone;


@Mapper
public interface DoorSettingMapper {
	

	int insertdoorsetting(@Param("doorsetting") DoorSetting doorsetting);
	
	List<DoorSetting> selectbygetid(@Param("usergetid") Integer usergetid);
	
	List<DoorSetting> selectbysendid(@Param("usersendid") Integer usersendid);

}
