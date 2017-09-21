package com.yonyou.live.broadcast.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
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
@RequestMapping(value = "/service/l")
public class LiveController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LiveService liveService;
	
	@Autowired
	LiveRoomService liveRoomService;
	
	@Autowired
	private HttpRequestContextHolder httpRequestContextHolder;
	
	/**
	 * 获取直播列表
	 * @return
	 */
	@RequestMapping(value = "/getlives", method = RequestMethod.POST)
	public JsonResponse getAllLives() {
		JsonResponse result = new JsonResponse();
		String vZanServer = PropertyUtil.getPropertyByKey("vzan.server.url");
		String livesUrl = vZanServer + "/VZLive/GetLiveList";
		String httpResult = "";
		long currentTime = System.currentTimeMillis() / 1000;
		String sign = MD5Utils.getSign(currentTime);
		try {
			httpResult = liveService.getLives(livesUrl, sign, 1, 10, currentTime);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return result.failedWithReturn("请求失败");
		}
		if (StringUtils.isEmpty(httpResult)) {
			return result.failedWithReturn("请求失败");
		}
		JSONArray lives = (JSONArray) JSONArray.parse(httpResult);
		result.successWithData("data", lives);
		return result;
	}

	/**
	 * 获取话题
	 * @param liveid
	 * @return
	 */
	@RequestMapping(value = "/getlive", method = RequestMethod.POST)
	public JsonResponse getlive(String liveid) {
		JsonResponse result = new JsonResponse();
		if (StringUtils.isEmpty(liveid)) {
			return result.failedWithReturn("参数为空");
		}
		String vZanServer = PropertyUtil.getPropertyByKey("vzan.server.url");
		String liveUrl = vZanServer + "/VZLive/GetLiveModel";
		String httpResult = "";
		long currentTime = System.currentTimeMillis()/1000;
		String sign = MD5Utils.getSign(currentTime);
		try {
			httpResult = liveService.getLiveById(liveUrl, sign, currentTime, liveid);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return result.failedWithReturn("请求失败");
		}
		if (StringUtils.isEmpty(httpResult)) {
			return result.failedWithReturn("请求失败");
		}
		JSONObject resultJSON = (JSONObject) JSONObject.parse(httpResult);
		if (resultJSON.getBoolean("isok") != null && resultJSON.getBoolean("isok")) {
			result.successWithData("data", resultJSON.get("dataObj"));
		} else {
			if (StringUtils.isNotEmpty(resultJSON.getString("code"))
					&& StringUtils.isNotEmpty(resultJSON.getString("Msg"))) {
				logger.error("查询微赞返回码:" + resultJSON.getString("code") + "\br" + "错误信息:" + resultJSON.getString("Msg"));
			}
			result.failedWithReturn(resultJSON.getString("Msg"));
		}
		return result;
	}
	
	/**
	 * 创建直播间
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse createLiveRoom(@RequestBody(required = true) JSONObject json) {
		JsonResponse result = new JsonResponse();
		String userId = json.getString("userId");
		String nickname = json.getString("nickname");
		String headimg = json.getString("headimg");
		String sitelogo = json.getString("sitelogo");
		String sitename = json.getString("sitename");
		String tenantId = json.getString("tenantId");
		if(StringUtils.isAnyEmpty(new String[]{userId,nickname,sitename,headimg,sitelogo,tenantId})){
			return result.failedWithReturn("参数有误");
		}
		String tenantResult = com.yonyou.iuap.tenant.sdk.UserCenter.isAdminNew(tenantId, userId);
//		String headimg = com.yonyou.iuap.tenant.sdk.UserCenter.getUserAvatar(userId)
		if(StringUtils.isEmpty(tenantResult)){
			return result.failedWithReturn("租户查询异常");
		}
		if(StringUtils.equals(JSONObject.parseObject(tenantResult).getString("flag"), "0")){
			return result.failedWithReturn("该用户没有权限");
		}
		String vZanServer = PropertyUtil.getPropertyByKey("vzan.server.url");
		String liveUrl = vZanServer + "/VZLive/CreateSite";
		long currentTime = System.currentTimeMillis()/1000;
		String sign = MD5Utils.getSign(currentTime);
		String httpResult = "";
		try {
			httpResult = liveService.createSite(liveUrl, sign, currentTime, userId, sitename, headimg, sitelogo,nickname);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return result.failedWithReturn("请求失败");
		}
		JSONObject resultJSON = (JSONObject) JSONObject.parse(httpResult);
		if (resultJSON.getBoolean("isok") != null && resultJSON.getBoolean("isok")) {
			String liveRoomId = resultJSON.getString("dataObj");
			LiveTenantEntity tenantEntity = new LiveTenantEntity();
			tenantEntity.setAdminId(userId);
			tenantEntity.setTenantId(tenantId);
			String appCloudId = httpRequestContextHolder.getHttpRequestContext().getCloudId();
			tenantEntity.setAppcloudId(appCloudId);
			tenantEntity.setCreateTime(new Date());
			tenantEntity.setLiveRoomId(liveRoomId);
			liveRoomService.insertLiveRoom(tenantEntity);
			result.successWithData("data", resultJSON.get("Msg"));
		}else {
			if (StringUtils.isNotEmpty(resultJSON.getString("code")) && StringUtils.isNotEmpty(resultJSON.getString("Msg"))) {
				logger.error("创建直播间返回码:" + resultJSON.getString("code") + "\br" + "错误信息:" + resultJSON.getString("Msg"));
			}
			result.failedWithReturn(resultJSON.getString("Msg"));
		}
		return result;
	}

}
