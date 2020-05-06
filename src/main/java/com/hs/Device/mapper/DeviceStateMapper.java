package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.DeviceState;

//X1000
@Mapper
public interface DeviceStateMapper {
	
  int insertdvstate(@Param("devicestate") DeviceState devicestate);
  
  List<DeviceState> selectdvstate(@Param("usergetid") Integer usergetid);

}
