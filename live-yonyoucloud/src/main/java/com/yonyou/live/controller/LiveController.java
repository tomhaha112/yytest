package com.yonyou.live.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.payment.sdk.common.ServiceException;
import com.yonyou.iuap.tenant.sdk.TenantCenter;
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
					JSONObject json = new JSONObject();
			        json.put("tenantId", tenantId);
			        json.put("resCode", "livecloud");
			        Date date = new Date();
			        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        String biginDate =  sf.format(date);
			        Calendar calendar = Calendar.getInstance();
			        calendar.setTime(date);
			        calendar.add(Calendar.YEAR, 1);
			        date = calendar.getTime();
			        String endDate = sf.format(date);
			        json.put("beginDate", biginDate);
			        json.put("endDate", endDate);
			        JSONArray jsonArray = new JSONArray();
			        jsonArray.add(json);
			        String stringBathOpenApp = TenantCenter.StringBathOpenApp(jsonArray.toString());
			        if(StringUtils.isNoneEmpty(stringBathOpenApp)){
			        	JSONObject resultJSON = JSONObject.parseObject(stringBathOpenApp);
			        	JSONArray array =  (JSONArray) resultJSON.get("success");
			        	if(array.size() == 0){
			        		return result.failedWithReturn("租户开通云直播失败");
			        	}
			        }else{
			        	return result.failedWithReturn("租户开通云直播异常");
			        }
			        
				}else{
					return result.failedWithReturn("创建直播间失败");
				}
			}else{
				return result.failedWithReturn("创建直播间异常");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			result.failed("访问直播失败");
		}
		result.successWithData("data", data);
		return result;
	}
	
	@RequestMapping(value = "/createTenant", method = RequestMethod.GET)
    public JsonResponse testBatchOpen(HttpServletRequest request,HttpServletResponse response){
		JsonResponse result = new JsonResponse();
		String userId = "";
		AttributePrincipal principal = (AttributePrincipal) request
				.getUserPrincipal();
		if (principal != null) {
			Map<String, Object> attrMap = principal.getAttributes();
			userId = (String) attrMap.get("userId");
			String appCode = request.getParameter("appCode");
			String tenantResult = TenantCenter.getTenantByTenantCode(appCode);
			if(StringUtils.isNoneBlank(tenantResult)){
				String tenantId = ((JSONObject)JSONObject.parse(((JSONObject)JSONObject.parse(tenantResult)).getString("tenant"))).getString("tenantId");
				JSONObject json = new JSONObject();
		        json.put("tenantId", tenantId);
		        json.put("resCode", "livecloud");
		        Date date = new Date();
		        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		        String biginDate =  sf.format(date);
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.add(Calendar.YEAR, 1);
		        date = calendar.getTime();
		        String endDate = sf.format(date);
		        json.put("beginDate", biginDate);
		        json.put("endDate", endDate);
		        JSONArray jsonArray = new JSONArray();
		        jsonArray.add(json);
		        String stringBathOpenApp = TenantCenter.StringBathOpenApp(jsonArray.toString());
		        result.put("data", stringBathOpenApp);
			}else{
				result.put("data", "租户不存在");
			}
		} else {
			result.put("data", "拦截异常");
		}
		return result;
	}
}
