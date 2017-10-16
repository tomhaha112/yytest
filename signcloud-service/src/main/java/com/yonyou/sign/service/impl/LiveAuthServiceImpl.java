package com.yonyou.sign.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yonyou.sign.result.ServiceResult;
import com.yonyou.sign.sdk.model.LiveAuthEntity;
import com.yonyou.sign.sdk.service.LiveAuthService;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.sign.dao.LiveAuthDao;
import com.yonyou.sign.result.ServiceResultCode;

public class LiveAuthServiceImpl implements LiveAuthService {

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
