package com.hklh8.validate;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by GouBo on 2017/12/27.
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}