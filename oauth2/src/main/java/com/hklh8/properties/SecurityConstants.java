package com.hklh8.properties;

/**
 * Created by GouBo on 2017/12/29.
 */
public interface SecurityConstants {

    /**
     * 默认的处理验证码的url
     */
    String DEFAULT_VALIDATE_CODE_URL = "/code/image";

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/oauth/login";

    /**
     * 默认获取token的url
     */
    String DEFAULT_GET_TOKEN_URL = "/oauth/token";
}
