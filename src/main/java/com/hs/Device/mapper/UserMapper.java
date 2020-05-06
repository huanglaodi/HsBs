package com.hs.Device.mapper;




import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.User;



@Mapper
public interface UserMapper {

	User selectuserbyid(@Param("userid") Integer userid);
	
	
	 
	 
}
