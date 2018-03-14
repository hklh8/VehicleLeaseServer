package com.hklh8.service;

import com.hklh8.domain.RoleUser;
import com.hklh8.domain.BaseUser;
import com.hklh8.dto.UserCondition;
import com.hklh8.dto.UserInfo;
import com.hklh8.repository.RoleRepository;
import com.hklh8.repository.RoleUserRepository;
import com.hklh8.repository.BaseUserRepository;
import com.hklh8.repository.spec.UserSpec;
import com.hklh8.repository.support.QueryResultConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务
 * Created by GouBo on 2018/1/2.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 创建用户
     */
    public UserInfo create(UserInfo userInfo) {
        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(userInfo, baseUser);
        baseUser.setPassword(passwordEncoder.encode("123456"));
        baseUserRepository.save(baseUser);
        userInfo.setId(baseUser.getId());

        createRoleUser(userInfo, baseUser);

        return userInfo;
    }

    /**
     * 修改用户
     */
    public UserInfo update(UserInfo userInfo) {
        BaseUser baseUser = baseUserRepository.findOne(userInfo.getId());
        BeanUtils.copyProperties(userInfo, baseUser);

        createRoleUser(userInfo, baseUser);
        return userInfo;
    }

    /**
     * 创建角色用户关系数据。
     */
    private void createRoleUser(UserInfo userInfo, BaseUser baseUser) {
        if (CollectionUtils.isNotEmpty(baseUser.getRoles())) {
            roleUserRepository.delete(baseUser.getRoles());
        }
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(roleRepository.getOne(userInfo.getRoleId()));
        roleUser.setBaseUser(baseUser);
        roleUserRepository.save(roleUser);
    }

    /**
     * 删除用户
     */
    public void delete(Long id) {
        baseUserRepository.delete(id);
    }

    /**
     * 获取用户详细信息
     */
    public UserInfo getInfo(Long id) {
        BaseUser baseUser = baseUserRepository.findOne(id);
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(baseUser, info);
        return info;
    }

    /**
     * 分页查询用户
     */
    public Page<UserInfo> query(UserCondition condition, Pageable pageable) {
        Page<BaseUser> users = baseUserRepository.findAll(new UserSpec(condition), pageable);
        return QueryResultConverter.convert(users, UserInfo.class, pageable);
    }

    public Page<UserInfo> queryByRole(Long roleId, Pageable pageable) {
        Page<BaseUser> users = baseUserRepository.findByRoleId(roleId, pageable);
        return QueryResultConverter.convert(users, UserInfo.class, pageable);
    }
}
