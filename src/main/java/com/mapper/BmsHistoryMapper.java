package com.mapper;

import com.vo.req.LocationMsg;
import org.apache.ibatis.annotations.Param;

public interface BmsHistoryMapper {
    int insertBmsHistory(@Param(value = "locationInfo") LocationMsg.LocationInfo locationInfo);
}
