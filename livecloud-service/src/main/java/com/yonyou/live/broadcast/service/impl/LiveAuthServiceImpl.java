package com.yonyou.live.broadcast.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.live.broadcast.dao.LiveAuthDao;
import com.yonyou.live.broadcast.result.ServiceResult;
import com.yonyou.live.broadcast.result.ServiceResultCode;
import com.yonyou.live.broadcast.sdk.model.LiveAuthEntity;
import com.yonyou.live.broadcast.sdk.service.LiveAuthService;

public class LiveAuthServiceImpl implements LiveAuthService{

	@Autowired
	private LiveAuthDao authDao;
	
	public ServiceResult<LiveAuthEntity> getAuthByappId(String appId, Date currentTime) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("appcloudId", appId);
		paramsMap.put("currentTime", currentTime);
		LiveAuthEntity authEntity = authDao.getAuthByappId(paramsMap);
		if(authEntity == null){
			return ServiceResult.failure(ServiceResultCode.LIVE_AUTH_QUERY_ERROR.getErrCode(), ServiceResultCode.LIVE_AUTH_QUERY_ERROR.getErrMsg());
		}
		return ServiceResult.success(authEntity);
	}

}
