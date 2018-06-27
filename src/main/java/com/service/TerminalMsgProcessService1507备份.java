package com.service;

import com.common.JT808Const;
import com.entity.Config;
import com.mapper.*;
import com.service.codec.MsgEncoder;
import com.util.CarEventUtil;
import com.util.CarHistoryUtil;
import com.vo.PackageData;
import com.vo.PackageData.MsgBody;
import com.vo.Session;
import com.vo.req.ConfigMsg;
import com.vo.req.ConfigMsg.ConfigInfo;
import com.vo.req.EventMsg;
import com.vo.req.EventMsg.EventInfo;
import com.vo.req.LocationMsg;
import com.vo.req.LocationMsg.LocationInfo;
import com.vo.req.VersionMsg;
import com.vo.req.VersionMsg.VersionInfo;
import com.vo.resp.RespMsgBody;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Scope("prototype")
public class TerminalMsgProcessService1507备份 extends BaseMsgProcessService {

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
    
	@Autowired
    private ConfigMapper configMapper;
	
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
    	if (CarHistoryUtil.isPersistent(locationInfo)) {
    		carHistoryMapper.insertCarHistory(DateTime.now().toString("M"), locationInfo);
    	}//这个不需要判断，坐标相同也要入库，跟坐标没有关系
    	Session session = sessionManager.findSessionByKey(msg.getMsgHead().getTerminalPhone());
    	//如果session等于null则证明终端没有先发送登录包过来，需要主动断开该连接
    	if (session == null) {
    		msg.getChannel().close();
    	} else {
    		session.setLastCommunicateTime(DateTime.now());
            byte[] bs = this.msgEncoder.encode4LocationResp(msg, new RespMsgBody((byte) 1));
            super.send2Terminal(msg.getChannel(), bs);
    	}
    }
    
    //处理事件业务
    public void processEventMsg(EventMsg msg) throws Exception {
    	EventInfo eventInfo = msg.getEventInfo();
    	//判断是否需要写入事件到数据库
    	if (CarEventUtil.isPersistent(eventInfo)) {
    		carEventMapper.insertCarEvent(DateTime.now().toString("M"), eventInfo, eventInfo.getLocationInfo());
    	}
    }
    
    //处理自检信息业务
    public void processSelfCheckMsg(PackageData packageData) {
    	carRuntimeMapper.setCarOnlineState(packageData.getMsgHead().getTerminalPhone());
    }
    //处理终端版本信息业务
    public void processVersionMsg(VersionMsg versionMsg) throws InterruptedException {
    	VersionInfo versionInfo = versionMsg.getVersionInfo();
    	if (configMapper.updateConfig(versionInfo) == 0) {
    		configMapper.insertConfig(versionInfo);
    	}
    	int replyResult = 3;
    	Config config = configMapper.selectConfigByKey(versionInfo.getMac()).get(0);
    	String[] versions = config.getVersion().replace("V", "").split("\\.");
    	String[] sysVersions = config.getVersionSys().replace("V", "").split("\\.");
    	int updateTag = config.getUpdateTag();
    	int	updateCfgTag = config.getUpdateCfgTag();
    	int[] versionsInt = new int[4];
    	int[] sysVersionsInt = new int[4];
    	for (int i = 0; i < 4; i++) {
    		if (versions.length >= (i + 1)) {
    			versionsInt[i] = Integer.valueOf(versions[i]).intValue();
    		} else {
    			versionsInt[i] = 0;
    		}
    		if (sysVersions.length >= (i + 1)) {
    			sysVersionsInt[i] = Integer.valueOf(sysVersions[i]).intValue();
    		} else {
    			sysVersionsInt[i] = 0;
    		}
    	}
    	if (sysVersionsInt[0] > versionsInt[0] ||
    		sysVersionsInt[1] > versionsInt[1] ||
    		sysVersionsInt[2] > versionsInt[2] ||
    		sysVersionsInt[3] > versionsInt[3]) {
    		if (updateTag == 1 && updateCfgTag == 0) {
    			replyResult = 1;
    		} else if (updateTag == 1 && updateCfgTag == 1) {
    			replyResult = 2;
    		}
    	} else {
    		replyResult = 0;
    	}
    	byte[] bs = msgEncoder.encode4VersionResp(versionMsg, new RespMsgBody((byte) replyResult));
    	super.send2Terminal(versionMsg.getChannel(), bs);
    }
    
    //处理终端配置更新业务
    public void processConfigMsg(ConfigMsg configMsg) throws Exception {
    	ConfigInfo configInfo = configMsg.getConfigInfo();
    	List<Config> configs = configMapper.selectConfigByKey(configInfo.getMac());
    	if (configs.size() > 0) {
    		Config config = configs.get(0);
    		//车辆信息中车牌号包含4412或者终端手机号码是17775754123时，不下发配置文件
			if (!config.getCarNumber().contains("4412") && !config.getDevPhone().equals("17775754123")) {
	        	byte[] bs = msgEncoder.encode4ConfigResp(configMsg, config);
	        	super.send2Terminal(configMsg.getChannel(), bs);
			}
    	}
    }
    
    //处理指令业务，这里是处理终端返回的指令执行响应,不是下发指令
    public void processActionMsg(PackageData packageData) {
    	dataActionMapper.updateActionDealResult(packageData.getMsgBody());
    }
    
    //处理抓拍业务
    public void processImageActionMsg(PackageData packageData) throws Exception {
    	MsgBody msgBody = packageData.getMsgBody();
    	byte[] bodybs = msgBody.getBodyBytes();
    	long serialId = msgBody.getBodySerial();
    	//如果流水号很大则是事件抓拍，否则就是指令抓拍
    	if (serialId > 888888) {
    		//这里好像没什么用
    	} else {
    		//保存图片到服务器
			File file = new File(JT808Const.IMAGE_SAVE_PATH + serialId + ".jpg");
		    try (FileOutputStream out = new FileOutputStream(file)) {
				out.write(bodybs, 6, bodybs.length - 6); //减去标示符2位，唯一ID4位，共6位。
				out.flush();
		    } catch (IOException e) {
		        throw new RuntimeException(e.getMessage(), e);
		    }
		    msgBody.setResult(1);
		    //更新抓拍指令执行结果
		    this.processActionMsg(packageData);
    	}
    }
    
    //处理参数业务，这里是处理终端返回的参数执行响应
    public void processParamMsg(PackageData packageData) {
    	dataParamMapper.updateParamResult(packageData.getMsgBody());
    }
}
