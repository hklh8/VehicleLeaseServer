package com.hklh8.service;

import com.hklh8.domain.BaseUser;
import com.hklh8.properties.AuthorizeConstants;
import com.hklh8.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by GouBo on 2018/1/2.
 * Rbac意为基于角色的权限访问控制（Role-Based Access Control）
 */
@Component
public class RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private UserRepository userRepository;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        boolean hasPermission = false;
        if (AuthorizeConstants.SUPER_ADMIN.equals(username)) {
            hasPermission = true;
        } else {
            BaseUser baseUser = userRepository.findUserByUsernameAndActive(username, true);
            if (baseUser == null) {
                return false;
            }
            Set<String> urls = baseUser.getUrls();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getServletPath())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
