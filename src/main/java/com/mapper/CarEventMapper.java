package com.mapper;

import org.apache.ibatis.annotations.Param;

import com.vo.req.EventMsg.EventInfo;
import com.vo.req.LocationMsg.LocationInfo;

public interface CarEventMapper {

	int insertCarEvent(@Param(value = "month") String month,
                       @Param(value = "eventInfo") EventInfo eventInfo,
                       @Param(value = "locationInfo") LocationInfo locationInfo);
	
}
