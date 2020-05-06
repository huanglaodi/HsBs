package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.Task;


@Mapper
public interface TaskMapper {

	 List<Task> selectTask(@Param("task") Task task);
	 
	 List<Task> selectTaskbyuserid(@Param("userid") Integer userid);
	 
	 int insertTask(@Param("task") Task task);
	 
	 int updateTask(@Param("task") Task task);
	 
	 List<Task> selectbydeviceid(@Param("deviceid") String devicename);
	
	
	 Task getTaskbytaskid(@Param("taskid") Integer taskid);
	 
}
