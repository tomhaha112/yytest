package com.yonyou.sign.sdk.service;

import java.util.Date;

import com.yonyou.sign.result.ServiceResult;
import com.yonyou.sign.sdk.model.LiveAuthEntity;

public interface LiveAuthService {

	ServiceResult <LiveAuthEntity> getAuthByappId(String appId,Date currentTime);
	
}
