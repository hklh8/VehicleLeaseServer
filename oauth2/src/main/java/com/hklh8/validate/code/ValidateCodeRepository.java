package com.hklh8.validate.code;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by GouBo on 2017/12/27.
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     *
     * @param request
     * @param code
     */
    void save(HttpServletRequest request, ValidateCode code);

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    ValidateCode get(HttpServletRequest request);

    /**
     * 移除验证码
     *
     * @param request
     */
    void remove(HttpServletRequest request);
}
