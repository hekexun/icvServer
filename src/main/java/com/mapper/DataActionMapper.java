package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.DataAction;
import com.vo.PackageData.MsgBody;

public interface DataActionMapper {

	int updateActionDealResult(@Param(value = "msgBody") MsgBody msgBody);
	
	int updateActionReceiveResult(@Param(value = "actionId") Integer actionId,
                                  @Param(value = "receiveResult") Integer receiveResult);
	
	List<DataAction> findSendActionData();
}
