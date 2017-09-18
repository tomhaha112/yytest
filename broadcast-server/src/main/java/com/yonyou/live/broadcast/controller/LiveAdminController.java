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

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.payment.common.http.HttpRequestContextHolder;
import com.yonyou.iuap.payment.sdk.common.ServiceException;
import com.yonyou.iuap.utils.PropertyUtil;
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
	
	@Autowired
	private HttpRequestContextHolder httpRequestContextHolder;
	
	/**
	 * 进入直播管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getliveAdmin", method = RequestMethod.POST)
	public JsonResponse createLiveAdmin(HttpServletRequest request,HttpServletResponse response) {
		JsonResponse result = new JsonResponse();
		String userId = request.getParameter("userId");
		String tenantId = request.getParameter("tenantId");
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(tenantId)){
			return result.failedWithReturn("参数为空");
		}
		String appCloudId = httpRequestContextHolder.getHttpRequestContext().getCloudId();
		ServiceResult<LiveTenantEntity> liveResult = liveRoomService.getLiveRoomByTenant(tenantId,appCloudId,userId);
		if(!liveResult.isSuccess()){
			return result.failedWithReturn("该用户没有权限");
		}
		String vZanServer = PropertyUtil.getPropertyByKey("vzan.server.url");
		String liveUrl = vZanServer + "/VZLive/GetBackstage";
		long currentTime = System.currentTimeMillis()/1000;
		String sign = MD5Utils.getSign(currentTime);
		String httpResult = "";
		try {
			httpResult = liveService.getLiveAdmin(liveUrl, sign, currentTime, userId);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return result.failedWithReturn("请求失败");
		}
		JSONObject resultJSON = (JSONObject) JSONObject.parse(httpResult);
		if (resultJSON.getBoolean("isok") != null && resultJSON.getBoolean("isok")) {
			String cookieValue = resultJSON.getJSONObject("dataObj").getString("userId");
			String redirectUrl = resultJSON.getString("Msg");
			JSONObject data = new JSONObject();
			data.put("userId", cookieValue);
			data.put("liveUrl", redirectUrl);
			result.successWithData("data", data);
		}else {
			if (StringUtils.isNotEmpty(resultJSON.getString("code"))
					&& StringUtils.isNotEmpty(resultJSON.getString("Msg"))) {
				logger.error("进入直播管理界面错误:" + resultJSON.getString("code") + "\br" + "错误信息:" + resultJSON.getString("Msg"));
			}
			try {
				 response.sendRedirect("www.baidu.com");
			} catch (IOException e) {
				logger.error("重定向异常");
			}
			result.failedWithReturn(resultJSON.getString("Msg"));
		}
		return result;
	}
}
