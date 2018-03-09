package com.hklh8.controller;

import com.hklh8.dto.UserCondition;
import com.hklh8.dto.UserInfo;
import com.hklh8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Created by GouBo on 2018/1/2.
 */
@RestController
@RequestMapping("/user")
public class BaseUserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前登录的用户信息
     */
    @GetMapping("/me")
    public UserInfo me(Authentication user) {
        UserInfo info = new UserInfo();
        String username = (String) user.getPrincipal();
        info.setUsername(username);
        return info;
    }

    /**
     * 创建用户
     */
    @PostMapping
    public UserInfo create(@RequestBody UserInfo userInfo) {
        return userService.create(userInfo);
    }

    /**
     * 修改用户信息
     */
    @PutMapping
    public UserInfo update(@RequestBody UserInfo userInfo) {
        return userService.update(userInfo);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    public UserInfo getInfo(@PathVariable Long id) {
        return userService.getInfo(id);
    }

    /**
     * 分页查询用户
     */
    @GetMapping
    public Page<UserInfo> query(UserCondition condition, Pageable pageable) {
        return userService.query(condition, pageable);
    }

    /**
     * 按角色分页查询用户
     */
    @GetMapping("/role/{roleId}")
    public Page<UserInfo> queryByRole(@PathVariable Long roleId, Pageable pageable) {
        return userService.queryByRole(roleId, pageable);
    }
}
