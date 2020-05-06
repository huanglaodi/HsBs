package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.Users;

@Mapper
public interface UsersMapper {

	// 插入人员
	int insertUser(@Param("users") Users users);

	Users selectbydvidanduid(@Param("users") Users users);

	// 删除所有人员
	int deletealluser(@Param("devid") String dvid);

	// 删除某个id人员
	int deleteuserbyuid(@Param("users") Users users);

	int deleteuserbyuserid(@Param("users") Users users);

	// 修改某个人员/V536
	int updateuser(@Param("users") Users users);

	// 根据usersendid查询
	List<Users> selectbysendid(@Param("usersendid") Integer usersendid);

	// 根据usergetid查询
	List<Users> selectbygetid(@Param("usergetid") Integer usergetid);

	// 查询某设备所有人员
	List<Users> selectallusers(@Param("devid") String devid);

	//根据pkid查询
	List<Users> selectbypkid(@Param("pkid") String pkid);
	
	//主键查询
	Users selectbyusid(@Param("usid") Integer usid);
}
