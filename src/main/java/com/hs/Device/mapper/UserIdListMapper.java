package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.UserIdList;

// /V536
@Mapper
public interface UserIdListMapper {
	
	int insertuserids(@Param("useridlist") UserIdList useridlist);
	
	List<UserIdList> selectbygetid(@Param("usergetid") Integer usergetid);
	
	List<UserIdList> selectbypkid(@Param("pkid") Integer pkid);

}
