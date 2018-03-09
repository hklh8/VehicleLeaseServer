package com.hklh8.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 角色用户关系表
 * Created by GouBo on 2018/1/2.
 */
@Entity
public class RoleUser extends BaseEntity {

    /**
     * 角色
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private BaseUser baseUser;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BaseUser getBaseUser() {
        return baseUser;
    }

    public void setBaseUser(BaseUser baseUser) {
        this.baseUser = baseUser;
    }
}
