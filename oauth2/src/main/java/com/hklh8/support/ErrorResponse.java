package com.hklh8.support;

/**
 * Created by GouBo on 2017/12/28.
 */
public enum ErrorResponse {

    UNAUTHORIZED("10001","访问的服务需要身份认证，请引导用户到登录页!");

    private String errorCode;
    private String errorMsg;

    ErrorResponse(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
