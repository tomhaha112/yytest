package com.yonyou.live.broadcast.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.nosql.redis.JedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.payment.sdk.common.ServiceException;
import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.live.broadcast.result.ServiceResult;
import com.yonyou.live.broadcast.sdk.model.LiveTenantEntity;
import com.yonyou.live.broadcast.sdk.remote.service.LiveService;
import com.yonyou.live.broadcast.sdk.service.LiveRoomService;
import com.yonyou.live.broadcast.util.MD5Utils;
import com.yonyou.yht.sdk.UserCenter;
import com.yonyou.yht.sdkutils.JsonResponse;

@Controller
@RequestMapping(value = "/admin/l")
public class LiveAdminController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LiveService liveService;
	
	@Autowired
	LiveRoomService liveRoomService;
	
	@Autowired
	private JedisTemplate jedisTemplate;
	
	public static String REDIS_PREFIX="yyuapserver";
	
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
		response.sendRedirect(url+"&sign="+sign+"&timestamp="+currentTime+"&userId="+userId);//+"&key=yonyouyun"  key不需要
		return result;
	}
	
	@RequestMapping(value = "/createSubAdmin", method = {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
	public String createSubAdmin(@RequestParam(value="sweepcode") String sweepcode,
			@RequestParam(value="sweepkey") String sweepKey, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResponse result = new JsonResponse();
		try{
			String zbid = "";
			String inviteUserId = "";
			String[] arr = sweepcode.split("@");
			if(arr.length != 2){
//				return result.failedWithReturn("参数异常");
				response.getWriter().write("参数异常");
			}else{
				zbid = arr[0].substring(9);
				inviteUserId = arr[1];
			}
			
			String qrcodeCacheKey =  new StringBuffer(REDIS_PREFIX).append(sweepKey).toString();
			String cacheUser = jedisTemplate.get(qrcodeCacheKey);
			if(StringUtils.isEmpty(cacheUser)){
//				return result.failedWithReturn("超时，请重新扫码");
				response.getWriter().write("超时，请重新扫码");
			}
			
			String[] cacheUserArray = cacheUser.split("&");
			String userId = cacheUserArray[1];
			
			
			String headimg = getHeadimg(userId);
			String nickname = getNickName(userId);
			
			String vZanServer = PropertyUtil.getPropertyByKey("vzan.server.url");
			String liveUrl = vZanServer + "/VZLive/AddYYLiveAdmin";
			long currentTime = System.currentTimeMillis()/1000;
			String sign = MD5Utils.getSign(currentTime);
			String httpResult = "";
			try {
				httpResult = liveService.createSubAdmin(liveUrl, sign, currentTime, zbid, inviteUserId, userId, headimg, nickname);
			} catch (ServiceException e) {
				logger.error(e.getMessage());
//				return result.failedWithReturn("请求失败");
				response.getWriter().write("请求失败");
			}
			
			JSONObject resultJSON = (JSONObject) JSONObject.parse(httpResult);
			if (resultJSON.getBoolean("isok") != null && resultJSON.getBoolean("isok")) {
				
				//查看是否有邀请记录
				LiveTenantEntity paramEntity = new LiveTenantEntity();
				paramEntity.setAdminId(userId);
				paramEntity.setLiveRoomId(zbid);
				ServiceResult<LiveTenantEntity> existResult = liveRoomService.getLiveRoomByEntity(paramEntity);
				if(existResult.isSuccess()){
					//已存在 修改状态
					LiveTenantEntity existEntity = existResult.getResult();
					existEntity.setStatus("1");
					ServiceResult<LiveTenantEntity> updateResult = liveRoomService.UpdateEntity(existEntity);
					if(!updateResult.isSuccess()){
//						return result.failedWithReturn(updateResult.getMessage());
						response.getWriter().write(updateResult.getMessage());
					}
				}else{
					//不存在 新增
					
					//根据邀请人的信息 填充被邀请人的信息
					paramEntity = new LiveTenantEntity();
					paramEntity.setAdminId(inviteUserId);
					paramEntity.setLiveRoomId(zbid);
					paramEntity.setStatus("1");
					ServiceResult<LiveTenantEntity> inviteEntityResult = liveRoomService.getLiveRoomByEntity(paramEntity);
					if(!inviteEntityResult.isSuccess()){
//						return result.failedWithReturn(inviteEntityResult.getMessage());
						response.getWriter().write(inviteEntityResult.getMessage());
					}
					LiveTenantEntity inviteEntity = inviteEntityResult.getResult();
					
					LiveTenantEntity tenantEntity = new LiveTenantEntity();
					tenantEntity.setAdminId(userId);
					tenantEntity.setTenantId(inviteEntity.getTenantId());
					tenantEntity.setAppcloudId(inviteEntity.getAppcloudId());
					tenantEntity.setCreateTime(new Date());
					tenantEntity.setInviteUserId(inviteUserId);
					tenantEntity.setLiveRoomId(zbid);
					liveRoomService.insertLiveRoom(tenantEntity);
				}
				
				result.successWithData("data", resultJSON.get("Msg"));
			}else{
				if (StringUtils.isNotEmpty(resultJSON.getString("code")) && StringUtils.isNotEmpty(resultJSON.getString("Msg"))) {
					logger.error("创建子管理员返回码:" + resultJSON.getString("code") + "\br" + "错误信息:" + resultJSON.getString("Msg"));
				}
				result.failedWithReturn(resultJSON.getString("Msg"));
			}
			request.setAttribute("result", resultJSON.getString("Msg"));
		} catch(Exception e){
			result.failedWithReturn(e.getLocalizedMessage());
			logger.error("创建子管理员失败", e);
		}
		return "index";
	}
	
	private String getNickName(String userId) {
		String user = com.yonyou.yht.sdk.UserCenter.getUserById(userId);
		if(StringUtils.isEmpty(user)){
			throw new RuntimeException("友互通连接异常");
		}
		JSONObject userJSON = JSONObject.parseObject(user).getJSONObject("user");
		if(StringUtils.isEmpty(userJSON.getString("userId"))){
			throw new RuntimeException("用户查询异常");
		}
		return userJSON.getString("userName");
	}

	private String getHeadimg(String userId) {
		String headimgInfo = UserCenter.getUserAvatar(userId);
		if(StringUtils.isEmpty(headimgInfo)){
			throw new RuntimeException("友互通连接异常");
		}
		JSONObject headimgJson = JSONObject.parseObject(headimgInfo);
		if(StringUtils.equals(headimgJson.getString("status"),"1")){
			return headimgJson.getString("avatar");
		}else{
			throw new RuntimeException("获取头像失败");
		}
	}

	@RequestMapping(value = "/delSumAdmin", method = {RequestMethod.GET, RequestMethod.POST})
	public JsonResponse delSumAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JsonResponse result = new JsonResponse();
		String userId = request.getParameter("userId");
		String zbid = request.getParameter("zbid");
		logger.info("删除子管理员回调 ： userId ->" + userId + ",zbid-> " + zbid);
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(zbid)){
			return result.failedWithReturn("参数有误");
		}
		ServiceResult<Integer> liveResult = liveRoomService.deleteByUserAndRoom(userId, zbid);
		if(liveResult.isSuccess()){
			int count = liveResult.getResult();
			if(count == 1){
				result.successWithData("msg", "删除成功");
				return result;
			}
		}
		result.failedWithReturn("删除失败");
		return result;
	}
	
	@RequestMapping(value = "/getUserInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public JsonResponse getUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		JsonResponse result = new JsonResponse();
		try{
			String userId = request.getParameter("userId");
			if(StringUtils.isEmpty(userId)){
				return result.failedWithReturn("参数有误");
			}
			JSONObject obj = new JSONObject();
			String headimg = getHeadimg(userId);
			String nickname = getNickName(userId);
			obj.put("headimg", headimg);
			obj.put("nickname", nickname);
			
			result.successWithData("data", obj);
		}catch (Exception e){
			result.failedWithReturn(e.getLocalizedMessage());
			logger.error("获取用户信息失败", e);
		}
		return result;
	}
}
