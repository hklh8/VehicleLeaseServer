package com.hklh8.validate;

import com.hklh8.validate.code.ImageCode;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 * Created by GouBo on 2017/12/27.
 */
public interface ValidateCodeGenerator {

    ImageCode generator(HttpServletRequest request);

}
