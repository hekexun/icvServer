package com.mapper;

import com.vo.req.LocationMsg;
import org.apache.ibatis.annotations.Param;

public interface CarHistoryMapper {
    int insertCarHistory(@Param(value = "locationInfo") LocationMsg.LocationInfo locationInfo);
}
