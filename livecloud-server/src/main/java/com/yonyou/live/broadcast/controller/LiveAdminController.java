package com.yonyou.live.broadcast.controller;

import javax.servlet.http.HttpServletRequest;

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
	@RequestMapping(value = "/getliveAdmin", method = RequestMethod.GET)
	public String createLiveAdmin(HttpServletRequest request,String callback) {
		JSONObject result = new JSONObject();
		String userId = request.getParameter("userId");
		String tenantId = request.getParameter("tenantId");
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(tenantId)){
			result.put("code", 1);
			result.put("msg", "参数错误");
			return callback+"("+result+")";
		}
		String appCloudId = httpRequestContextHolder.getHttpRequestContext().getCloudId();
		ServiceResult<LiveTenantEntity> liveResult = liveRoomService.getLiveRoomByTenant(tenantId,appCloudId,userId);
		if(!liveResult.isSuccess()){
			result.put("code", 1);
			result.put("msg", "您不是租户管理员，没有权限访问");
			return callback+"("+result+")";
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
			result.put("code", 1);
			result.put("msg", "服务异常");
			return callback+"("+result+")";
		}
		JSONObject resultJSON = (JSONObject) JSONObject.parse(httpResult);
		if (resultJSON.getBoolean("isok") != null && resultJSON.getBoolean("isok")) {
			String cookieValue = resultJSON.getJSONObject("dataObj").getString("userId");
			String redirectUrl = resultJSON.getString("Msg");
			result.put("code", 0);
			result.put("userId", cookieValue);
			result.put("liveUrl", redirectUrl);
			return callback+"("+result+")";
		}else {
			if (StringUtils.isNotEmpty(resultJSON.getString("code"))
					&& StringUtils.isNotEmpty(resultJSON.getString("Msg"))) {
				logger.error("进入直播管理界面错误:" + resultJSON.getString("code") + "\br" + "错误信息:" + resultJSON.getString("Msg"));
			}
			result.put("code", 1);
			result.put("msg", resultJSON.getString("Msg"));
			return callback+"("+result+")";
		}
	}
}
