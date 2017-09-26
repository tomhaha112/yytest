package com.yonyou.live.broadcast.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yonyou.live.broadcast.sdk.model.LiveTenantEntity;

@Repository
public interface LiveRoomDao {

	LiveTenantEntity getLiveRoomByTenant(Map<String,String> paramMap);
	
	int insertLiveRoomEntity(LiveTenantEntity liveEntity);
	
	LiveTenantEntity getLiveRoomByAppAndTenant(Map<String,String> paramMap);
	
	LiveTenantEntity getLiveRoomByEntity(LiveTenantEntity paramEntity);

	int deleteByUserAndRoom(@Param("userId")String userId, @Param("liveRoomId")String liveRoomId);

	void updateLiveRoom(LiveTenantEntity liveTenantEntity);
}
