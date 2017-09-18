package com.yonyou.live.sdk.service;

import com.yonyou.iuap.payment.sdk.common.OpenAPI;
import com.yonyou.iuap.payment.sdk.common.OpenService;
import com.yonyou.iuap.payment.sdk.common.ParamAttr;
import com.yonyou.iuap.payment.sdk.common.ServiceException;

@OpenService
public interface LiveRemotService {

	String RESULT_JSON_KEY = "data";

	@OpenAPI(httpMethod = OpenAPI.HttpMethod.POST, uriPath = "/broadcast-server/service/l/getlives", resultJsonKey = RESULT_JSON_KEY,resultErrCodeKey="",resultErrMsgKey="")
	String getLives(
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "userId") final String userId) throws ServiceException;
	
	@OpenAPI(httpMethod = OpenAPI.HttpMethod.POST, uriPath = "/broadcast-server/service/l/getlive", resultJsonKey = "",resultErrCodeKey="",resultErrMsgKey="")
	String getLive(
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "liveid") final String liveid)
			throws ServiceException;
	
	@OpenAPI(httpMethod = OpenAPI.HttpMethod.POST, uriPath = "/broadcast-server/service/l/create", resultJsonKey = "",resultErrCodeKey="",resultErrMsgKey="")
	String createLiveRoom(
			@ParamAttr(location = ParamAttr.Location.JSON_CONTENT, paramKey = "userId") final String userId,
			@ParamAttr(location = ParamAttr.Location.JSON_CONTENT, paramKey = "nickname") final String nickname,
			@ParamAttr(location = ParamAttr.Location.JSON_CONTENT, paramKey = "headimg") final String headimg,
			@ParamAttr(location = ParamAttr.Location.JSON_CONTENT, paramKey = "sitelogo") final String sitelogo,
			@ParamAttr(location = ParamAttr.Location.JSON_CONTENT, paramKey = "sitename") final String sitename,
			@ParamAttr(location = ParamAttr.Location.JSON_CONTENT, paramKey = "tenantId") final String tenantId)
			throws ServiceException;
	
	@OpenAPI(httpMethod = OpenAPI.HttpMethod.POST, uriPath = "/broadcast-server/service/l/getliveAdmin", resultJsonKey = "",resultErrCodeKey="",resultErrMsgKey="")
	void liveManage(
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "userId") final String userId,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "tenantId") final String tenantId)
			throws ServiceException;
}
