package com.yonyou.live.broadcast.sdk.remote.service;

import com.yonyou.iuap.payment.sdk.common.OpenAPI;
import com.yonyou.iuap.payment.sdk.common.OpenService;
import com.yonyou.iuap.payment.sdk.common.ParamAttr;
import com.yonyou.iuap.payment.sdk.common.ServiceException;

@OpenService
public interface LiveService {

	String RESULT_JSON_KEY = "dataObj";

	@OpenAPI(httpMethod = OpenAPI.HttpMethod.POST, uriPath = "${runtime_uripath}", resultJsonKey = RESULT_JSON_KEY,resultErrCodeKey="",resultErrMsgKey="")
	String getLives(
			@ParamAttr(location = ParamAttr.Location.RUNTIME_URIPATH, paramKey = "callbackuri") final String callbackuri,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "sign") final String sign,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "pindex") final int pindex,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "psize") final int psize,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "timestamp") final Long timestamp)
			throws ServiceException;
	
	@OpenAPI(httpMethod = OpenAPI.HttpMethod.POST, uriPath = "${runtime_uripath}", resultErrCodeKey="",resultErrMsgKey="")
	String getLiveById(
			@ParamAttr(location = ParamAttr.Location.RUNTIME_URIPATH, paramKey = "callbackuri") final String callbackuri,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "sign") final String sign,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "timestamp") final Long timestamp,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "liveid") final String liveid)
			throws ServiceException;
	
	@OpenAPI(httpMethod = OpenAPI.HttpMethod.POST, uriPath = "${runtime_uripath}", resultErrCodeKey="",resultErrMsgKey="")
	String createSite(
			@ParamAttr(location = ParamAttr.Location.RUNTIME_URIPATH, paramKey = "callbackuri") final String callbackuri,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "sign") final String sign,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "timestamp") final Long timestamp,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "userId") final String userId,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "sitename") final String sitename,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "headimg") final String headimg,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "sitelogo") final String sitelogo,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "nickname") final String nickname)
			throws ServiceException;
	
	@OpenAPI(httpMethod = OpenAPI.HttpMethod.POST, uriPath = "${runtime_uripath}", resultErrCodeKey="",resultErrMsgKey="")
	String getLiveAdmin(
			@ParamAttr(location = ParamAttr.Location.RUNTIME_URIPATH, paramKey = "callbackuri") final String callbackuri,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "sign") final String sign,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "timestamp") final Long timestamp,
			@ParamAttr(location = ParamAttr.Location.URL, paramKey = "userId") final String userId)
			throws ServiceException;
	
	
}