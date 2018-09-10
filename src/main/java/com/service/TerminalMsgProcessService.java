package com.service;

import com.mapper.*;
import com.service.codec.MsgEncoder;
import com.util.ByteUtil;
import com.util.DigitUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.vo.PackageData;
import com.vo.Session;
import com.vo.req.LocationMsg;
import com.vo.req.LocationMsg.LocationInfo;
import com.vo.resp.RespMsgBody;

@Service
@Scope("prototype")
public class TerminalMsgProcessService extends BaseMsgProcessService {

	@Autowired
    private MsgEncoder msgEncoder;

	@Autowired
    private CarRuntimeMapper carRuntimeMapper;
    
	@Autowired
    private CarHistoryMapper carHistoryMapper;
	
	@Autowired
    private CarEventMapper carEventMapper;
    
	@Autowired
    private DataActionMapper dataActionMapper;
    
	@Autowired
    private DataParamMapper dataParamMapper;
	
    //处理终端登录业务
    public void processLoginMsg(PackageData packageData) throws Exception {
        byte[] bs = this.msgEncoder.encode4LoginResp(packageData, new RespMsgBody((byte) 1));
        String terminalPhone = packageData.getMsgHead().getTerminalPhone();
        Session session = new Session(terminalPhone, packageData.getChannel());
        session.setLastCommunicateTime(new DateTime());
        sessionManager.addSession(terminalPhone, session);
        carRuntimeMapper.setCarOnlineState(terminalPhone);
        super.send2Terminal(packageData.getChannel(), bs);
    }

    //处理基本位置信息业务
    public void processLocationMsg(LocationMsg msg) throws Exception {
    	LocationInfo locationInfo = msg.getLocationInfo();
    	if (carRuntimeMapper.updateCarRuntime(locationInfo) == 0) {
    		carRuntimeMapper.insertCarRuntime(locationInfo);
    	}
    	//判断是否需要写入位置信息到数据库
    		carHistoryMapper.insertCarHistory(locationInfo);
    	//这个不需要判断，坐标相同也要入库，跟坐标没有关系
    	Session session = sessionManager.findSessionByKey(msg.getMsgHead().getTerminalPhone());
    	//如果session等于null则证明终端没有先发送登录包过来，需要主动断开该连接
    	if (session == null) {
			String terminalPhone = msg.getMsgHead().getTerminalPhone();
			session = new Session(terminalPhone, msg.getChannel());
			session.setLastCommunicateTime(new DateTime());
			sessionManager.addSession(terminalPhone, session);
    	} else {
    		session.setLastCommunicateTime(DateTime.now());
            byte[] bs = this.msgEncoder.encode4LocationResp(msg, new RespMsgBody((byte) 0));
            byte[] bs2= ByteUtil.integerTo1Bytes(0);
            super.send2Terminal(msg.getChannel(), bs2);//切换为回复0
    	}
    }
}
