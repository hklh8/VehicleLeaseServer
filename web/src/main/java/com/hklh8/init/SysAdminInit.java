package com.hklh8.init;

import com.hklh8.domain.Role;
import com.hklh8.domain.RoleUser;
import com.hklh8.domain.User;
import com.hklh8.properties.AuthorizeConstants;
import com.hklh8.repository.RoleRepository;
import com.hklh8.repository.RoleUserRepository;
import com.hklh8.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/1/26.
 * 管理员初始化
 */
@Component
public class SysAdminInit extends AbstractDataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doInit() throws Exception {
        initSuperAdmin();
        initAdmin();
    }

    /**
     * 初始化超级管理员
     */
    private void initSuperAdmin() {
        User superAdmin = new User();
        superAdmin.setUsername(AuthorizeConstants.SUPER_ADMIN);
        superAdmin.setPassword(passwordEncoder.encode("123456"));
//        superAdmin.setLevel(1);
        userRepository.save(superAdmin);
    }

    /**
     * 初始化管理员
     */
    private void initAdmin() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
//        admin.setLevel(2);
        userRepository.save(admin);

        Role role = roleRepository.findByName(AuthorizeConstants.SYS_MANAGER);
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(role);
        roleUser.setBaseUser(admin);
        roleUserRepository.save(roleUser);
    }


    @Override
    protected boolean isNeedInit() {
        return userRepository.count() == 0;
    }

    @Override
    public Integer getIndex() {
        return 0;//authorize模块初始化资源必须最先，authorize模块设置为Integer.MIN_VALUE，此处必须大于Integer.MIN_VALUE
    }
}
