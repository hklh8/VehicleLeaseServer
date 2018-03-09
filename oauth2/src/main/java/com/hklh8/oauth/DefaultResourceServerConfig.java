package com.hklh8.oauth;

import com.hklh8.authentication.DefaultFormAuthenticationConfig;
import com.hklh8.authorize.AuthorizeConfigManager;
import com.hklh8.validate.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * Created by GouBo on 2017/12/27.
 */
@Configuration
@EnableResourceServer
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private DefaultFormAuthenticationConfig defaultFormAuthenticationConfig;

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.expressionHandler(expressionHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        defaultFormAuthenticationConfig.configure(http);
        http.apply(validateCodeSecurityConfig).and().csrf().disable();
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
