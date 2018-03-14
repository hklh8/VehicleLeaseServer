package com.hklh8.init;

import com.hklh8.domain.Resource;
import com.hklh8.domain.ResourceType;
import com.hklh8.domain.Role;
import com.hklh8.domain.RoleResource;
import com.hklh8.properties.AuthorizeConstants;
import com.hklh8.repository.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 默认的系统数据初始化器，永远在其他数据初始化器之前执行
 * Created by GouBo on 2018/1/2.
 */
@Component
public class UserDataInitializer extends AbstractDataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    protected ResourceRepository resourceRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE;
    }


    @Override
    protected void doInit() {
        Role admin = initRole(AuthorizeConstants.SYS_MANAGER);
        Role clerk = initRole(AuthorizeConstants.SHOP_MANAGER);

        initResource();

        initAdminResources(admin);
        initClerkResources(clerk);
    }

    /**
     * 初始化角色数据
     */
    private Role initRole(String name) {
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return role;
    }

    /**
     * 初始化菜单数据
     */
    protected void initResource() {
        Resource root = createRoot(AuthorizeConstants.ROOT_NODE);

        createResource(AuthorizeConstants.HOME, AuthorizeConstants.ICON_HOME, AuthorizeConstants.UI_INDEX, "home", ResourceType.MENU, root);
        createResource(AuthorizeConstants.EQUIP_LIST, AuthorizeConstants.ICON_NETBAR_LIST, AuthorizeConstants.UI_NETBAR_LIST, "netBar", ResourceType.MENU, root);
        createResource(AuthorizeConstants.GLOBAL_SETTING, AuthorizeConstants.ICON_GLOBAL_SET, AuthorizeConstants.UI_GLOBAL_SET, "global", ResourceType.MENU, root);
        createResource(AuthorizeConstants.INFO_MONITOR, AuthorizeConstants.ICON_INFO_MONITOR, AuthorizeConstants.UI_INFO_MONITOR, "infoMonitor", ResourceType.MENU, root);
        Resource infoManage = createResource(AuthorizeConstants.INFO_MANAGE, AuthorizeConstants.ICON_INFO_MANAGE, "0", "msgManage", ResourceType.MENU, root);

        createResource(AuthorizeConstants.BEHAVIOR_LOG, null, AuthorizeConstants.UI_BEHAVIOR_LOG, "equipLog", ResourceType.MENU, infoManage);
        createResource(AuthorizeConstants.SYS_LOG, null, AuthorizeConstants.UI_SYS_LOG, "sysLog", ResourceType.MENU, infoManage);
        Resource customerInfo = createResource(AuthorizeConstants.USER_INFO, null, AuthorizeConstants.UI_USER_INFO,
                "customerInfo", ResourceType.MENU, infoManage);
        createResource(AuthorizeConstants.SHOP_MANAGER, null, AuthorizeConstants.UI_SHOP_MANAGER, "netSiteOpUser", ResourceType.MENU, infoManage);
        createResource(AuthorizeConstants.SYS_MANAGER, null, AuthorizeConstants.UI_SYS_MANAGER, "sysManager", ResourceType.MENU, infoManage);

        //管理员登录用户信息页面拥有权限的三个按钮
        createResource("查找/刷新", null, null, "find", ResourceType.BUTTON, customerInfo);
        createResource("修改", null, null, "update", ResourceType.BUTTON, customerInfo);
        createResource("删除", null, null, "del", ResourceType.BUTTON, customerInfo);


        //************************************************网点管理员菜单***************************************************************//
        //网点管理员菜单(用户信息)
        Resource clerkUserInfo = createResource(AuthorizeConstants.USER_INFO, AuthorizeConstants.ICON_CLERK_USER_INFO, AuthorizeConstants.CLERK_USER_INFO,
                "msgManage/customerInfo", ResourceType.MENU, root);
        //网点管理员登录用户信息页面拥有权限的按钮
        createResource("查找/刷新", null, null, "find", ResourceType.BUTTON, clerkUserInfo);
        createResource("录入", null, null, "add", ResourceType.BUTTON, clerkUserInfo);
        createResource("文件导入", null, null, "upload", ResourceType.BUTTON, clerkUserInfo);
        createResource("下载模版", null, null, "down", ResourceType.BUTTON, clerkUserInfo);

    }

    protected void initAdminResources(Role admin) {
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources) {
            if (resource.getParent() != null &&
                    !AuthorizeConstants.CLERK_USER_INFO.equals(resource.getLink()) &&
                    !AuthorizeConstants.CLERK_USER_INFO.equals(resource.getParent().getLink()) &&
                    !AuthorizeConstants.UI_SYS_MANAGER.equals(resource.getLink())) {
                RoleResource roleResource = new RoleResource();
                roleResource.setResource(resource);
                roleResource.setRole(admin);
                roleResourceRepository.save(roleResource);
            }
        }
    }

    protected void initClerkResources(Role clerk) {
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources) {
            if (AuthorizeConstants.CLERK_USER_INFO.equals(resource.getLink()) ||
                    (resource.getParent() != null && AuthorizeConstants.CLERK_USER_INFO.equals(resource.getParent().getLink()))) {
                RoleResource roleResource = new RoleResource();
                roleResource.setResource(resource);
                roleResource.setRole(clerk);
                roleResourceRepository.save(roleResource);
            }
        }
    }

    @Override
    protected boolean isNeedInit() {
        return roleRepository.count() == 0;
    }

    protected Resource createRoot(String name) {
        Resource node = new Resource();
        node.setName(name);
        resourceRepository.save(node);
        return node;
    }

    protected Resource createResource(String name, Resource parent) {
        return createResource(name, null, null, null, null, parent);
    }

    protected Resource createResource(String name, String iconName, String link, String permUrl, ResourceType type, Resource parent) {
        Resource node = new Resource();
        node.setName(name);
        node.setLink(link);
        node.setIcon(iconName);
        node.setParent(parent);
        node.setType(type);
        if (parent != null && type.equals(ResourceType.MENU)) {
            String parentLink = parent.getLink();
            int modifiedLink = 0;
            try {
                modifiedLink = Integer.parseInt(parentLink) + 1;
            } catch (Exception ignored) {
            }
            parent.setLink(String.valueOf(modifiedLink));
        }
        if (StringUtils.isNotBlank(permUrl)) {
            String url = "/" + permUrl + "/**";
            if (parent != null && parent.getUrl() != null) {
                if (StringUtils.endsWith(parent.getUrl(), "**")) {
                    parent.setUrl(StringUtils.removeEnd(parent.getUrl(), "**"));
                    Resource resource = resourceRepository.findOne(parent.getId());
                    BeanUtils.copyProperties(parent, resource);
                }
                url = parent.getUrl() + permUrl + "/**";
            }
            node.setUrl(url);
        }
        resourceRepository.save(node);
        return node;
    }
}
