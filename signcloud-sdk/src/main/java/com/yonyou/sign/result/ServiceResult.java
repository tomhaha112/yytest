package com.yonyou.sign.result;

import java.io.Serializable;

/**
 *service层返回对象列表封装
 *Created by chenylh on 2017/3/10.
 * @param <T>
 */
public class ServiceResult<T>  implements Serializable{
	private static final long serialVersionUID = 1L;

	private boolean success = false;

    private String code;

    private String message;

    private T result;

    private ServiceResult() {
    }

    public static <T> ServiceResult<T> success(T result) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.result = result;
        item.code = "0";
        item.message = "success";
        return item;
    }

    public static <T> ServiceResult<T> failure(String errorCode, String errorMessage) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = errorCode;
        item.message = errorMessage;
        return item;
    }

    public static <T> ServiceResult<T> failure(ServiceResultCode codeEnum) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = codeEnum.getErrCode();
        item.message = codeEnum.getErrMsg();
        return item;
    }

    public static <T> ServiceResult<T> failure(String errorCode) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = errorCode;
        item.message = "failure";
        return item;
    }

    public boolean hasResult() {
        return result != null;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
