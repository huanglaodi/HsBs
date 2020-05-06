package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.DeleteUser;

//deleteuser  /V536
@Mapper
public interface DeleteUserMapper {
	
	//插入机器反馈的删除失败人员
	int insertdluser(@Param("deleteuser") DeleteUser deleteuser);
	
	//根据usergetid查询此次上传的数据
	List<DeleteUser> selectbygetid(@Param("usergetid") Integer usergetid);

}
