package com.yonyou.live.test;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.payment.sdk.common.ServiceException;
import com.yonyou.live.sdk.service.LiveRemotService;
import com.yonyou.yht.utils.JsonResponse;


@RestController
@RequestMapping(value = "/live/l")
public class TestController {

	@Autowired
	LiveRemotService liveService;
	
	/**
	 * 获取直播列表
	 * @return
	 */
	@RequestMapping(value = "/getlives", method = RequestMethod.GET)
	public JsonResponse getAllLives(String userId) {
		JsonResponse result = new JsonResponse();
		String data = "";
		try {
			data = liveService.getLives(userId);
		} catch (ServiceException e) {
			e.printStackTrace();
			result.failed("访问直播失败");
		}
		result.successWithData("data", data);
		return result;
	}
	
	
	/**
	 * 获取直播列表
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
	 * 获取直播列表
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public JsonResponse createLiveRoom(String userId,String tenantId,String sitename) {
		JsonResponse result = new JsonResponse();
		String data = "";
		try {
			String user = com.yonyou.yht.sdk.UserCenter.getUserById(userId);
			if(StringUtils.isEmpty(user)){
			    return result.failedWithReturn("友互通连接异常");
			}
			JSONObject userJSON = JSONObject.parseObject(user).getJSONObject("user");
			if(StringUtils.isEmpty(userJSON.getString("userId"))){
				return result.failedWithReturn("用户查询异常");
			}
			String nickname = userJSON.getString("userName");
			String headimg = "https://iuap-market-test.oss-cn-beijing.aliyuncs.com/userphoto-efefefefefef.jpg";
			String sitelogo = "https://iuap-market-test.oss-cn-beijing.aliyuncs.com/userphoto-a7ecc96a-7ab0-4a01-a966-24baae219fb6.jpg";
			data = liveService.createLiveRoom(userId, nickname, headimg, sitelogo, sitename, tenantId);
		} catch (ServiceException e) {
			e.printStackTrace();
			result.failed("访问直播失败");
		}
		result.successWithData("data", data);
		return result;
	}
	
	
	/**
	 * 获取直播列表
	 * @return
	 */
	@RequestMapping(value = "/getLiveAdmin", method = RequestMethod.GET)
	public void redrictLive(String userId,String tenantId) {
		try {
		  liveService.liveManage(userId, tenantId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
}
