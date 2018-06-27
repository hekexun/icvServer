package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.CarRuntime;
import com.vo.req.LocationMsg.LocationInfo;

public interface CarRuntimeMapper {

	int insertCarRuntime(@Param(value = "locationInfo") LocationInfo locationInfo);
	
	int updateCarRuntime(@Param(value = "locationInfo") LocationInfo locationInfo);
	
	int setCarOnlineState(@Param(value = "devPhone") String devPhone);
	
	int setCarOfflineState(@Param(value = "idleTime") String idleTime);
	
	List<CarRuntime> findCarPassword(@Param(value = "terminalPhone") String terminalPhone);
}
