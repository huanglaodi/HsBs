package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.Fps;

// /V536
@Mapper
public interface FpsMapper {
	
	int insertfps(@Param("fps") Fps fps);
	
	List<Fps> selectfps(@Param("usid") Integer usid);
	
	int deletebyusid(@Param("usid") Integer usid);

}
