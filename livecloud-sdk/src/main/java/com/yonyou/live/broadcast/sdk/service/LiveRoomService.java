package com.yonyou.live.broadcast.sdk.service;

import com.yonyou.live.broadcast.result.ServiceResult;
import com.yonyou.live.broadcast.sdk.model.LiveTenantEntity;

public interface LiveRoomService {

	ServiceResult <LiveTenantEntity> getLiveRoomByTenant(String tenantId,String appCloudId,String adminId);
	
	ServiceResult <Integer> insertLiveRoom(LiveTenantEntity liveEntity);
}
