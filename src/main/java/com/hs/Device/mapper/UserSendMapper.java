package com.hs.Device.mapper;




import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.UserSend;



@Mapper
public interface UserSendMapper {

	UserSend selectbytaskid(@Param("usersendid") Integer sendid);
	
	int insertUsersend(@Param("usersend") UserSend usersend);
	 
	 
}
