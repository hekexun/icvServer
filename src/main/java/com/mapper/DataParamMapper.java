package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.DataParam;
import com.vo.PackageData.MsgBody;

public interface DataParamMapper {

	int updateParamResult(@Param(value = "msgBody") MsgBody msgBody);
	
	int updateParamReceiveResult(@Param(value = "paramId") Integer paramId,
                                 @Param(value = "receiveResult") Integer receiveResult);
	
	List<DataParam> findSendParamData();
}
