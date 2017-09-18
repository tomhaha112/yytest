package com.yonyou.live.broadcast.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.live.broadcast.sdk.model.LiveTenantEntity;

@Repository
public interface LiveRoomDao {

	LiveTenantEntity getLiveRoomByTenant(Map<String,String> paramMap);
	
	int insertLiveRoomEntity(LiveTenantEntity liveEntity);
}
