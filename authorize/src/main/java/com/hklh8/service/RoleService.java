package com.hklh8.service;

import com.hklh8.domain.Role;
import com.hklh8.domain.RoleResource;
import com.hklh8.dto.RoleInfo;
import com.hklh8.repository.ResourceRepository;
import com.hklh8.repository.RoleRepository;
import com.hklh8.repository.RoleResourceRepository;
import com.hklh8.repository.RoleUserRepository;
import com.hklh8.repository.support.QueryResultConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色服务
 * Created by GouBo on 2018/1/2.
 */
@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    /**
     * 创建角色
     */
    public RoleInfo create(RoleInfo info) {
        Role role = new Role();
        BeanUtils.copyProperties(info, role);
        info.setId(roleRepository.save(role).getId());
        return info;
    }

    /**
     * 修改角色
     */
    public RoleInfo update(RoleInfo info) {
        Role role = roleRepository.findOne(info.getId());
        BeanUtils.copyProperties(info, role);
        return info;
    }

    /**
     * 删除角色
     */
    public void delete(Long id) {
        Role role = roleRepository.findOne(id);
        if (CollectionUtils.isNotEmpty(role.getRoleUsers())) {
            throw new RuntimeException("不能删除有下挂用户的角色");
        }
        roleRepository.delete(id);
    }
//
//	public String[] getRoleMenus(Long id) {
//		return StringUtils.split(roleRepository.findOne(id).getMenus(), ",");
//	}
//
//	public void setRoleMenu(Long roleId, String menuIds) {
//		Role role = roleRepository.findOne(roleId);
//		role.setMenus(menuIds);
//	}

    /**
     * 获取角色详细信息
     */
    public RoleInfo getInfo(Long id) {
        Role role = roleRepository.findOne(id);
        RoleInfo info = new RoleInfo();
        BeanUtils.copyProperties(role, info);
        return info;
    }

    /**
     * 根据角色名称查询角色
     */
    public RoleInfo getRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        RoleInfo info = new RoleInfo();
        BeanUtils.copyProperties(role, info);
        return info;
    }

    /**
     * 查询所有角色
     */
    public List<RoleInfo> findAll() {
        return QueryResultConverter.convert(roleRepository.findAll(), RoleInfo.class);
    }

    public String[] getRoleResources(Long id) {
        Role role = roleRepository.findOne(id);
        Set<String> resourceIds = new HashSet<>();
        for (RoleResource resource : role.getResources()) {
            resourceIds.add(resource.getResource().getId().toString());
        }
        return resourceIds.toArray(new String[resourceIds.size()]);
    }

    public void setRoleResources(Long roleId, String resourceIds) {
        resourceIds = StringUtils.removeEnd(resourceIds, ",");
        Role role = roleRepository.findOne(roleId);
        roleResourceRepository.delete(role.getResources());
        String[] resourceIdArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(resourceIds, ",");
        for (String resourceId : resourceIdArray) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRole(role);
            roleResource.setResource(resourceRepository.getOne(new Long(resourceId)));
            roleResourceRepository.save(roleResource);
        }
    }
}
