package com.hklh8.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 角色资源关系表
 * Created by GouBo on 2018/1/2.
 */
@Entity
public class RoleResource extends BaseEntity {

    /**
     * 角色
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    /**
     * 资源
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Resource resource;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
