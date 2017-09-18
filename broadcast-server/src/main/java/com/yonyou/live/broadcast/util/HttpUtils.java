package com.yonyou.live.broadcast.util;

import java.util.Map;

import com.yonyou.iuap.utils.HttpTookit;

public class HttpUtils {

	/**
	 * 发送微赞post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url,Map<String,String> params){
		long currentTime = System.currentTimeMillis();
		String sign = MD5Utils.getSign(currentTime);
		params.put("sign", sign);
		String httpResult = HttpTookit.doPost(url, params);
		return httpResult;
	}
	
	/**
	 * 发送微赞get请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doGet(String url,Map<String,String> params){
		long currentTime = System.currentTimeMillis();
		String sign = MD5Utils.getSign(currentTime);
		params.put("sign", sign);
		String httpResult = HttpTookit.doGet(url, params);
		return httpResult;
	}
	
	
	public static void main(String[] args) {
	
	
	}
}
