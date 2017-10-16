package com.yonyou.sign.sdk.model;

import java.util.Date;

/**
 * 认证实体
 * 
 * @author dongjch
 *
 */
public class LiveAuthEntity {

	/**
	 * 主键
	 */
	private int id;

	/**
	 * 应用编码
	 */
	private String appcloudId;

	/**
	 * 应用私钥
	 */
	private String privatekey;

	/**
	 * 注册时间
	 */
	private Date createtime;

	/**
	 * 截止日期
	 */
	private Date endtime;
	/**
	 * 开通状态
	 */
	private int openstate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppcloudId() {
		return appcloudId;
	}

	public void setAppcloudId(String appcloudId) {
		this.appcloudId = appcloudId;
	}

	public String getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getOpenstate() {
		return openstate;
	}

	public void setOpenstate(int openstate) {
		this.openstate = openstate;
	}

}
