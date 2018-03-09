package com.hklh8.validate;

import com.hklh8.properties.SecurityConstants;
import com.hklh8.validate.code.ImageCode;
import com.hklh8.validate.code.ValidateCode;
import com.hklh8.validate.code.ValidateCodeRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by GouBo on 2017/12/26.
 */
@RestController
public class ValidateCodeController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL)
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = imageCodeGenerator.generator(request);
//        request.getSession().setAttribute(SESSION_KEY, imageCode);
        ValidateCode code = new ValidateCode(imageCode.getCode(),imageCode.getExpireTime());
        validateCodeRepository.save(request, code);
        logger.info("验证码为：" + imageCode.getCode());
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
