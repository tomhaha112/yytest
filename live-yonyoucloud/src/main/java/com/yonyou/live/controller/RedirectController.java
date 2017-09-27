package com.yonyou.live.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.live.sdk.service.LiveRemotService;

@Controller
@RequestMapping(value = "/redirectUrl")
public class RedirectController {

	@Autowired
	LiveRemotService liveService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/vzan")
	public void vzan(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userId = "";
			String tenantId = "";
			AttributePrincipal principal = (AttributePrincipal) request
					.getUserPrincipal();
			if (principal != null) {
				Map<String, Object> attrMap = principal.getAttributes();
				userId = (String) attrMap.get("userId");
				tenantId = (String) attrMap.get("tenantId");
				String serverUrl = PropertyUtil
						.getPropertyByKey("pom.vzan.server.url");

				String url = serverUrl
						+ "/livecloud_server/admin/l/getliveAdmin?userId="
						+ userId
						+ "&tenantId=" + tenantId + "&callback=callback&appId=1eee";
				response.sendRedirect(url);
				return;
			} else {
				logger.error("跳转页面获取用户信息失败");
				response.getWriter().write("获取用户信息失败");
				return;
			}
		} catch (Exception e) {
			try {
				response.getWriter().write("跳转管理页面异常");
			} catch (IOException e1) {
				
			}
			logger.error("跳转页面异常");
			return;
		}
	}
}
