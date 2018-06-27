package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Config;
import com.vo.req.VersionMsg.VersionInfo;

public interface ConfigMapper {

	int insertConfig(@Param(value = "configInfo") VersionInfo configInfo);
	
	int updateConfig(@Param(value = "configInfo") VersionInfo configInfo);
	
	List<Config> selectConfigByKey(@Param(value = "mac") String mac);
	
}
