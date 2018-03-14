package com.hklh8.authentication;

import com.hklh8.domain.BaseUser;
import com.hklh8.repository.BaseUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by GouBo on 2018/1/2.
 * Rbac意为基于角色的权限访问控制（Role-Based Access Control）
 */
@Component
@Transactional
public class RbacUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名:" + username);
        BaseUser baseUser = baseUserRepository.findUserByUsernameAndActive(username, true);
        if (baseUser == null) {
            throw new UsernameNotFoundException(username);
        }
        baseUser.getUrls();
        return baseUser;
    }

}
