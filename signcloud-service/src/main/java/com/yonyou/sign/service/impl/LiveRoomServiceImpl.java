package com.yonyou.sign.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.sign.dao.LiveRoomDao;
import com.yonyou.sign.result.ServiceResult;
import com.yonyou.sign.result.ServiceResultCode;
import com.yonyou.sign.sdk.model.LiveTenantEntity;
import com.yonyou.sign.sdk.service.LiveRoomService;

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

	@Override
	public ServiceResult<LiveTenantEntity> getLiveRoomByAppAndTenant(String tenantId, String appCloudId) {
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("appcloudId", appCloudId);
		paramsMap.put("tenantId", tenantId);
		LiveTenantEntity tenantEntity =  roomDao.getLiveRoomByAppAndTenant(paramsMap);
		if(tenantEntity == null){
			return ServiceResult.failure(ServiceResultCode.LIVE_ADMIN_QUERY_ERROR.getErrCode(), ServiceResultCode.LIVE_ADMIN_QUERY_ERROR.getErrMsg());
		}
		return ServiceResult.success(tenantEntity);
	}

	@Override
	public ServiceResult<LiveTenantEntity> getLiveRoomByEntity(
			LiveTenantEntity paramEntity) {
		LiveTenantEntity tenantEntity =  roomDao.getLiveRoomByEntity(paramEntity);
		if(tenantEntity == null){
			return ServiceResult.failure(ServiceResultCode.LIVE_ADMIN_QUERY_ERROR.getErrCode(), ServiceResultCode.LIVE_ADMIN_QUERY_ERROR.getErrMsg());
		}
		return ServiceResult.success(tenantEntity);
	}

	@Override
	public ServiceResult<Integer> deleteByUserAndRoom(String userId,
			String liveRoomId) {
		int count = 0;
		try{
			count =  roomDao.deleteByUserAndRoom(userId, liveRoomId);
		}catch(Exception e){
			e.printStackTrace();
			return ServiceResult.failure(ServiceResultCode.LIVE_ADMIN_DEL_ERROR);
		}
		return ServiceResult.success(count);
	}

	@Override
	public ServiceResult<LiveTenantEntity> UpdateEntity(LiveTenantEntity LiveTenantEntity) {
		try{
			roomDao.updateLiveRoom(LiveTenantEntity);
		}catch(Exception e){
			e.printStackTrace();
			return ServiceResult.failure(ServiceResultCode.LIVE_ADMIN_UPDATE_ERROR);
		}
		return ServiceResult.success(LiveTenantEntity);
	}

}
