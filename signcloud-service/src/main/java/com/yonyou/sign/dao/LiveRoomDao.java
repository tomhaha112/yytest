package com.yonyou.sign.dao;

import java.util.Map;

import com.yonyou.sign.sdk.model.LiveTenantEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveRoomDao {

	LiveTenantEntity getLiveRoomByTenant(Map<String,String> paramMap);
	
	int insertLiveRoomEntity(LiveTenantEntity liveEntity);
	
	LiveTenantEntity getLiveRoomByAppAndTenant(Map<String,String> paramMap);
	
	LiveTenantEntity getLiveRoomByEntity(LiveTenantEntity paramEntity);

	int deleteByUserAndRoom(@Param("userId")String userId, @Param("liveRoomId")String liveRoomId);

	void updateLiveRoom(LiveTenantEntity liveTenantEntity);
}
