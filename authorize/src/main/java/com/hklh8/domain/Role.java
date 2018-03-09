package com.hklh8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色信息
 * Created by GouBo on 2018/1/2.
 */
@Entity
public class Role extends BaseEntity {

    /**
     * 角色名称
     */
    @Column(length = 20, nullable = false)
    private String name;
    /**
     * 角色拥有权限的资源集合
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<RoleResource> resources = new HashSet<>();
    /**
     * 角色的用户集合
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<RoleUser> roleUsers = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoleResource> getResources() {
        return resources;
    }

    public void setResources(Set<RoleResource> resources) {
        this.resources = resources;
    }

    public Set<RoleUser> getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(Set<RoleUser> roleUsers) {
        this.roleUsers = roleUsers;
    }
}
