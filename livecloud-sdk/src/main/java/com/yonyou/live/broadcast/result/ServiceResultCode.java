package com.yonyou.live.broadcast.result;

/**
 * 全局错误码
 * 如果业务方有自己的业务错误码,可以重新定义
 */
public enum ServiceResultCode {
	/**
     * 获取套件suiteToken失败
     **/
    SUCCESS("0", "success"),
    SYS_ERROR("-1", "系统繁忙"),
    SYS_AUTH_ERROR("-2", "权限失败，请检查业务参数是否越权"),
    BIZ_LOCK_EXIST("-10", "锁存在"),
    BIZ_LOCK_NOT_EXIST("-11", "锁不存在"),
    PARAM_LACK_ERROR("-3", "参数不足"),
	
	LIVE_AUTH_QUERY_ERROR("1001","查询认证信息失败"),
	LIVE_ADMIN_QUERY_ERROR("1002","查询管理员信息失败"),
	LIVE_ADMIN_INSERT_ERROR("1003","插入直播间失败");

   
    private String errCode;
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "errCode=" + errCode + ",errMsg=" + errMsg;
    }

    private ServiceResultCode(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
