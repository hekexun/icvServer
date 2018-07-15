package com.scheduler;

import java.util.Iterator;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mapper.CarRuntimeMapper;
import com.server.SessionManager;
import com.vo.Session;

import io.netty.channel.Channel;

@Component
public class TerminalScheduler {

	@Autowired
	private CarRuntimeMapper carRuntimeMapper;
	
	/**
	 * @Description: 移除没有持续上报数据的终端session, 并更改车辆实时表的车辆状态为离线状态   
	 * @return void  
	 */
	@Scheduled(cron = "0 6/6 * * * ? ")
	public void removeIdleSession() {
		Iterator<Entry<String, Session>> iterator = 
				SessionManager.getInstance().getSessionMap().entrySet().iterator();
		DateTime idleTime = DateTime.now().minusMinutes(6);
		while (iterator.hasNext()) {
			Session session = iterator.next().getValue();
			DateTime lastCommunicateTime = session.getLastCommunicateTime();
			if (lastCommunicateTime == null || lastCommunicateTime.isBefore(idleTime)) {
				Channel channel = session.getChannel();
				if (channel.isOpen()) {
					channel.close();
				}
				iterator.remove();
			}
		}
		//最后的时间写入到数据库//carRuntimeMapper.setCarOfflineState(idleTime.toString("yyyy-MM-dd HH:mm:ss"));
	}
	
}
