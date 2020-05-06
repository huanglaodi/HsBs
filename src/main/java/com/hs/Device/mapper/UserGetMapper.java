package com.hs.Device.mapper;




import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.UserGet;



@Mapper
public interface UserGetMapper {

	UserGet selectbytaskid(@Param("taskid") Integer taskid);
	
	
	 int insertuserget(@Param("userget") UserGet userget);
	 
}
