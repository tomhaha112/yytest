package com.yonyou.live.broadcast.sdk.service;

import com.yonyou.live.broadcast.result.ServiceResult;
import com.yonyou.live.broadcast.sdk.model.LiveTenantEntity;

public interface LiveRoomService {

	ServiceResult <LiveTenantEntity> getLiveRoomByTenant(String tenantId,String appCloudId,String adminId);
	
	ServiceResult <LiveTenantEntity> getLiveRoomByAppAndTenant(String tenantId,String appCloudId);
	
	ServiceResult <Integer> insertLiveRoom(LiveTenantEntity liveEntity);
	
	ServiceResult <LiveTenantEntity> getLiveRoomByEntity(LiveTenantEntity paramEntity);
	
	ServiceResult <Integer> deleteByUserAndRoom(String userId, String liveRoomId);
	
	ServiceResult <LiveTenantEntity> UpdateEntity(LiveTenantEntity entity);
}
