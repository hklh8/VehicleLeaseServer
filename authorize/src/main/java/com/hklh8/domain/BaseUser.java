package com.hklh8.domain;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * 用户
 * Created by GouBo on 2018/1/2.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseUser extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = -3521673552808391992L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户状态
     */
    private boolean active = true;

    /**
     * 用户的所有角色
     */
    @OneToMany(mappedBy = "baseUser", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<RoleUser> roles = new HashSet<>();
    /**
     * 用户有权访问的所有url，不持久化到数据库
     */
    @Transient
    private Set<String> urls = new HashSet<>();
    /**
     * 用户有权的所有资源id，不持久化到数据库
     */
    @Transient
    private Set<Long> resourceIds = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles.size());
        for(RoleUser roleUser : roles) {
//            grantedAuthorities.add(new GrantedAuthorityImpl(roleUser.getRole().getName()));
        }
        return grantedAuthorities;
    }

    public Set<Long> getAllResourceIds() {
        init(resourceIds);
        forEachResource(resource -> resourceIds.add(resource.getId()));
        return resourceIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<RoleUser> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleUser> roles) {
        this.roles = roles;
    }

    public Set<String> getUrls() {
        init(urls);
        forEachResource(resource -> urls.add(resource.getUrl()));
        return urls;
    }

    private void init(Set<?> data) {
        if (CollectionUtils.isEmpty(data)) {
            data = new HashSet<>();
        }
    }

    private void forEachResource(Consumer<Resource> consumer) {
        for (RoleUser role : roles) {
            for (RoleResource roleResource : role.getRole().getResources()) {
                consumer.accept(roleResource.getResource());
            }
        }
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }
}
