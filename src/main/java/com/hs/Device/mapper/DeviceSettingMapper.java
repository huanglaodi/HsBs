package com.hs.Device.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.DeviceSetting;


@Mapper
public interface DeviceSettingMapper {
  
	 DeviceSetting selectbygetid(@Param("usergetid") Integer usergetid);
	 
	 int insertdevst(@Param("devicesetting") DeviceSetting devicesetting);
	 

	 
}
