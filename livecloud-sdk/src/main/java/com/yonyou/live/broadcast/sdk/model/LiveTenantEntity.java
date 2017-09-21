package com.yonyou.live.broadcast.sdk.model;

import java.util.Date;

/**
 * 直播间
 * @author dongjch
 *
 */
public class LiveTenantEntity {
	
	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 应用Id
	 */
	private String appcloudId;
	
	/**
	 * 租户Id
	 */
    private String tenantId;
    
    /**
     * 用户Id
     */
    private String adminId;
    
    
    /**
     * 直播间Id
     */
    private String liveRoomId;
    
    /**
     * 直播间创建时间
     */
    private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppcloudId() {
		return appcloudId;
	}

	public void setAppcloudId(String appcloudId) {
		this.appcloudId = appcloudId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getLiveRoomId() {
		return liveRoomId;
	}

	public void setLiveRoomId(String liveRoomId) {
		this.liveRoomId = liveRoomId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
