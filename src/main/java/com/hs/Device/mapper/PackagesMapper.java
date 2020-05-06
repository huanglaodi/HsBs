package com.hs.Device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hs.Device.model.Packages;

// /V536
@Mapper
public interface PackagesMapper {
	
	int insertpackage(@Param("packages") Packages pag);
	
	List<Packages> selectpagbygetid(@Param("usergetid") Integer usergetid);

}
