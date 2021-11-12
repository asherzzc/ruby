package com.road.config.security;

import com.road.pojo.Menu;
import com.road.pojo.Role;
import com.road.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 动态权限配置
 *
 * @Author: zhouc
 * @Date: 2021/9/11 16:59
 * @Since： 1.0
 * @Description: 根据URL判断对应的角色
 */
@Component
public class AuthorityForUrlFilter implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private IMenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override

    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        // 判断是否包含对应URL 以及查询其角色
        List<Menu> menusWithRoles = menuService.getMenusWithRoles();
        for (Menu menusWithRole : menusWithRoles) {
            if (antPathMatcher.match(menusWithRole.getUrl(), requestUrl)) {
                String[] roles = menusWithRole.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(roles);
            }
        }
        // 如果填写的请求不存在 默认返回 `ROLE_LOGIN`
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
