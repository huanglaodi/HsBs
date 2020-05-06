package com.hs.Device.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.Device;


//device
@Mapper
public interface DeviceMapper {
  //查询所有设备
	 List<Device> selectAllDevice();
	
	 
	 //插入设备V536
	 int insertdevice(@Param("device") Device device);
	 
	 //根据设备id查询
	 Device selectdevicebyid(@Param("devid") String devid);
	 
	 int updatedevice(@Param("device") Device device);
	 
	 
	 
}
