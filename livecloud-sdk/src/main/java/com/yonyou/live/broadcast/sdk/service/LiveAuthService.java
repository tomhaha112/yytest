package com.yonyou.live.broadcast.sdk.service;

import java.util.Date;

import com.yonyou.live.broadcast.result.ServiceResult;
import com.yonyou.live.broadcast.sdk.model.LiveAuthEntity;

public interface LiveAuthService {

	ServiceResult <LiveAuthEntity> getAuthByappId(String appId,Date currentTime);
	
}
