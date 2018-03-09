package com.hklh8.authorize;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/1/2.
 * Rbac意为基于角色的权限访问控制（Role-Based Access Control）
 */
@Component
@Order
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(HttpMethod.GET, "/**/*.html").permitAll()
                .antMatchers(HttpMethod.POST, "/equipLogReport/**").permitAll()
                .antMatchers(HttpMethod.POST, "/encrypt/**").permitAll()
                .antMatchers("/dyLog/**").permitAll()
                .antMatchers(HttpMethod.GET, "/user/me", "/resource").authenticated()
                .antMatchers(HttpMethod.POST, "/signOut").authenticated()
                .anyRequest().access("@rbacService.hasPermission(request, authentication)");
        return true;
    }

}
