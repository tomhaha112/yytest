package com.yonyou.live.broadcast.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.live.broadcast.dao.LiveRoomDao;
import com.yonyou.live.broadcast.result.ServiceResult;
import com.yonyou.live.broadcast.result.ServiceResultCode;
import com.yonyou.live.broadcast.sdk.model.LiveTenantEntity;
import com.yonyou.live.broadcast.sdk.service.LiveRoomService;

public class LiveRoomServiceImpl implements LiveRoomService{

	@Autowired
	private LiveRoomDao roomDao;
	
	@Override
	public ServiceResult<LiveTenantEntity> getLiveRoomByTenant(String tenantId, String appCloudId, String adminId) {
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("appcloudId", appCloudId);
		paramsMap.put("adminId", adminId);
		paramsMap.put("tenantId", tenantId);
		LiveTenantEntity tenantEntity =  roomDao.getLiveRoomByTenant(paramsMap);
		if(tenantEntity == null){
			return ServiceResult.failure(ServiceResultCode.LIVE_ADMIN_QUERY_ERROR.getErrCode(), ServiceResultCode.LIVE_ADMIN_QUERY_ERROR.getErrMsg());
		}
		return ServiceResult.success(tenantEntity);
	}

	@Override
	public ServiceResult<Integer> insertLiveRoom(LiveTenantEntity liveEntity) {
		int insertResult = roomDao.insertLiveRoomEntity(liveEntity);
		if(insertResult == 1){
			return ServiceResult.success(insertResult);
		}
		return ServiceResult.failure(ServiceResultCode.LIVE_ADMIN_INSERT_ERROR.getErrCode(), ServiceResultCode.LIVE_ADMIN_INSERT_ERROR.getErrMsg());
	}

}
