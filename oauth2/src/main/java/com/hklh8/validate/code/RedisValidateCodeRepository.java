package com.hklh8.validate.code;

import com.hklh8.validate.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by GouBo on 2017/12/27.
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired(required = false)
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public void save(HttpServletRequest request, ValidateCode code) {
        redisTemplate.opsForValue().set(buildKey(request), code, 30, TimeUnit.MINUTES);
    }


    @Override
    public ValidateCode get(HttpServletRequest request) {
        Object value = redisTemplate.opsForValue().get(buildKey(request));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }


    @Override
    public void remove(HttpServletRequest request) {
        redisTemplate.delete(buildKey(request));
    }


    private String buildKey(HttpServletRequest request) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + deviceId;
    }
}
