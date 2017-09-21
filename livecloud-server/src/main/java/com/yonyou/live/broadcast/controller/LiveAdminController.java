package com.yonyou.live.broadcast.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.live.broadcast.result.ServiceResult;
import com.yonyou.live.broadcast.sdk.model.LiveTenantEntity;
import com.yonyou.live.broadcast.sdk.remote.service.LiveService;
import com.yonyou.live.broadcast.sdk.service.LiveRoomService;
import com.yonyou.live.broadcast.util.MD5Utils;
import com.yonyou.yht.utils.JsonResponse;

@RestController
@RequestMapping(value = "/admin/l")
public class LiveAdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LiveService liveService;
	
	@Autowired
	LiveRoomService liveRoomService;
	
	/**
	 * 进入直播管理界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getliveAdmin", method = RequestMethod.GET)
	public JsonResponse createLiveAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JsonResponse result = new JsonResponse();
		String userId = request.getParameter("userId");
		String tenantId = request.getParameter("tenantId");
		String appId = request.getParameter("appId");
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(tenantId) || StringUtils.isEmpty(appId)){
			return result.failedWithReturn("参数有误");
		}
		ServiceResult<LiveTenantEntity> liveResult = liveRoomService.getLiveRoomByTenant(tenantId,appId,userId);
		if(!liveResult.isSuccess()){
			return result.failedWithReturn("您不是租户管理员，没有权限访问");
		}
		long currentTime = System.currentTimeMillis()/1000;
		String sign = MD5Utils.getSign(currentTime);
		String url = "http://tliveapi.vzan.com/VZLive/UrlRedirect?zbid="+liveResult.getResult().getLiveRoomId();
		response.sendRedirect(url+"&sign="+sign+"&timestamp="+currentTime+"&key=yonyouyun");
		return result;
	}
}
