package com.hs.Device.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.DeviceInfo;


//deviceinfo
@Mapper
public interface DeviceInfoMapper {
  //查询所有设备
	 List<DeviceInfo> selectAllDevice();
	
	 //根据usergetid查询
	 DeviceInfo selectbygetid(@Param("usergetid") Integer usergetid);
	 
	 //插入设备V536
	 int insertdevice(@Param("device") DeviceInfo device);
	 
	 //根据设备id查询
	 DeviceInfo selectdevicebyid(@Param("devid")  String devid);
	 
	 //查询某设备最近一条设备信息
	 DeviceInfo selectbydevid(@Param("devid")  String devid);
	 
}
