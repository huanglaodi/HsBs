package com.hs.Device.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.Instruct;



@Mapper
public interface InstructMapper {

	//插入指令
	 int insertinstruct(@Param("instruct") Instruct instruct);
	//删除指令
	 int deleteinstruct(@Param("instructid") Integer instructid);
	 //查询指令
	Instruct selectinstructbyid(@Param("instructid") Integer instructid);
	 
	 
}
