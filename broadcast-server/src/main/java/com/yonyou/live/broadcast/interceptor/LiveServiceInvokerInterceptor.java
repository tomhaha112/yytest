package com.yonyou.live.broadcast.interceptor;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.util.Base64;
import com.yonyou.iuap.payment.common.http.HttpRequestContext;
import com.yonyou.iuap.payment.common.http.HttpRequestContextHolder;
import com.yonyou.iuap.payment.common.log.context.RpcContext;
import com.yonyou.live.broadcast.result.ServiceResult;
import com.yonyou.live.broadcast.sdk.model.LiveAuthEntity;
import com.yonyou.live.broadcast.sdk.service.LiveAuthService;

public class LiveServiceInvokerInterceptor extends HandlerInterceptorAdapter{
	
	private final static Logger accessLogger = LoggerFactory.getLogger(LiveServiceInvokerInterceptor.class);
	
	@Autowired
	private HttpRequestContextHolder httpRequestContextHolder;
	
	@Autowired
	private LiveAuthService liveAuthService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();

		accessLogger.info("RequestURL" + request.getRequestURL());
		accessLogger.info("ClientIp" + getRemortIP(request));
		accessLogger.info("QueryString" + request.getQueryString());

		String traceId = null == request.getAttribute("SEV-TRACEID") ? null
				: request.getAttribute("SEV-TRACEID").toString();
		if (!StringUtils.isEmpty(traceId)) {
			RpcContext.traceId.set(traceId);
		} else {
			request.setAttribute("SEV-TRACEID", RpcContext.traceId.get());
		}
		accessLogger.info("traceId_request:" + request.getAttribute("SEV-TRACEID"));
		
		
		if (!checkHeaderAuth(request, response)) {
			// 未通过认证
			response.setStatus(401);
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setHeader("WWW-authenticate", "Basic Realm=\"test\"");
			accessLogger.info("SEV-TRACEID:认证不通过,没有找到签名!");
			return false;
		}

		String cloudId = request.getAttribute("cloudId").toString();
		accessLogger.info("SEV-TRACEID/CLOUD-ID:"+traceId+'/'+cloudId+"认证通过！");


		if (handler instanceof HandlerMethod) {
			HttpRequestContext controllerContext = new HttpRequestContext();
			controllerContext.setStartTime(System.currentTimeMillis());
			controllerContext.setClientIp(getRemortIP(request));
			controllerContext.setRequestMethod(request.getMethod());
			controllerContext.setRequestURI(request.getRequestURI());
			controllerContext.setRequestURL(request.getRequestURL().toString());
			controllerContext.setQuery(request.getQueryString());
			controllerContext.setCookies(cookies);
			controllerContext.setCloudId(cloudId);
			httpRequestContextHolder.setHttpRequestContext(controllerContext);
		}
		return super.preHandle(request, response, handler);
	}

	public String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isEmpty(ip)) {
			return ip;
		}
		if (request.getHeader("X-Forwarded-For") != null) {
			for (String singleIP : request.getHeader("X-Forwarded-For").split(",")) {
				if (singleIP != null && !singleIP.equals("unknown")) {
					return singleIP.trim();
				}
			}
		}
		return request.getRemoteAddr();
	}

	private boolean checkHeaderAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String auth = request.getHeader("Authorization");
		if (StringUtils.isEmpty(auth) || auth.length() < 6) {
			return false;
		}
		// "Authorization: Basic jdhaHY0="，其中Basic表示基础认证， jdhaHY0=是base64编码的"user:passwd"字符串。
		String encodedAuth = auth.substring(6, auth.length());
		String decodedAuth = getFromBASE64(encodedAuth);
		String[] authArray = StringUtils.split(decodedAuth, ':');
//		if (authArray.length != 2 || StringUtils.isEmpty(ServiceSecretCache.getSecretByCloudId(authArray[0]))
//				|| !StringUtils.equals(ServiceSecretCache.getSecretByCloudId(authArray[0]), authArray[1])) {
//			return false;
//		}
		if(authArray.length != 2){
			return false;
		}
		String appId = authArray[0];
		ServiceResult<LiveAuthEntity> authEntity =  liveAuthService.getAuthByappId(appId, new Date(System.currentTimeMillis()));
		if(!authEntity.isSuccess() || authEntity.getResult() == null ||!StringUtils.equals(authArray[1], authEntity.getResult().getPrivatekey())){
			return false;
		}
		request.setAttribute("cloudId", authArray[0]);
		return true;
	}

	private String getFromBASE64(String s) {
		if (s == null)
			return null;
		try {
			byte[] b = Base64.decodeFast(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
}
