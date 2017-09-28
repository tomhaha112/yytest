package com.yonyou.live.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.payment.sdk.common.ServiceException;
import com.yonyou.live.sdk.service.LiveRemotService;
import com.yonyou.yht.sdk.UserCenter;
import com.yonyou.yht.sdkutils.JsonResponse;

@RestController
@RequestMapping(value = "/live")
public class LiveController {

	@Autowired
	LiveRemotService liveService;
	
	/**
	 * 获取直播列表
	 * @return
	 */
	@RequestMapping(value = "/getlives", method = RequestMethod.GET)
	public JsonResponse getAllLives(@RequestParam(value="tenantId")String tenantId,
			@RequestParam(value="pageNum",required = false) Integer pageNum,
			@RequestParam(value="pageSize",required = false) Integer pageSize) {
		JsonResponse result = new JsonResponse();
		String data = "";
		try {
			if(pageNum == null) pageNum = 1;
			if(pageSize == null) pageSize = 10;
			data = liveService.getLives(tenantId, pageNum, pageSize);
		} catch (ServiceException e) {
			e.printStackTrace();
			result.failed("访问直播失败");
		}
		result.successWithData("data", data);
		return result;
	}
	
	/**
	 * 获取直播话题
	 * @return
	 */
	@RequestMapping(value = "/getlive", method = RequestMethod.GET)
	public JsonResponse getlive(String liveId) {
		JsonResponse result = new JsonResponse();
		String data = "";
		try {
			data = liveService.getLive(liveId);
		} catch (ServiceException e) {
			e.printStackTrace();
			result.failed("访问直播失败");
		}
		result.successWithData("data", data);
		return result;
	}
	
	
	/**
	 * 创建直播间
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public JsonResponse createLiveRoom(HttpServletRequest request, String sitename, String sitelogo, String levels) {
		JsonResponse result = new JsonResponse();
		String data = "";
		try {
			String userId = "";
			String tenantId = "";
			AttributePrincipal principal = (AttributePrincipal) request
					.getUserPrincipal();
			if (principal != null) {
				Map<String, Object> attrMap = principal.getAttributes();
				userId = (String) attrMap.get("userId");
				tenantId = (String) attrMap.get("tenantId");
			}
			
			String user = com.yonyou.yht.sdk.UserCenter.getUserById(userId);
			if(StringUtils.isEmpty(user)){
			    return result.failedWithReturn("友互通连接异常");
			}
			JSONObject userJSON = JSONObject.parseObject(user).getJSONObject("user");
			if(StringUtils.isEmpty(userJSON.getString("userId"))){
				return result.failedWithReturn("用户查询异常");
			}
			
			String headimgInfo = UserCenter.getUserAvatar(userId);
			if(StringUtils.isEmpty(headimgInfo)){
			    return result.failedWithReturn("友互通连接异常");
			}
			JSONObject headimgJson = JSONObject.parseObject(headimgInfo);
			String headimg = "";
			if(StringUtils.equals(headimgJson.getString("status"),"1")){
				headimg = headimgJson.getString("avatar");
			}
			
			String nickname = userJSON.getString("userName");
			data = liveService.createLiveRoom(userId, nickname, headimg, sitelogo, sitename, tenantId, levels);
			if(StringUtils.isNoneEmpty(data)){
				JSONObject resultData = JSONObject.parseObject(data);
				if(resultData.containsKey("isok") && resultData.getBooleanValue("isok")){
					
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			result.failed("访问直播失败");
		}
		result.successWithData("data", data);
		return result;
	}
	
}
