package com.road.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 动态权限配置
 *
 * @Author: zhouc
 * @Date: 2021/9/11 19:28
 * @Since： 1.0
 * @Description: 判断当前用户是否有访问某资源的角色
 */
@Component
public class AuthorityForRoleFilter implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute attribute : configAttributes) {
            // 需要的权限
            String returnRole = attribute.getAttribute();
            if (returnRole.equals("ROLE_LOGIN")) { // 如果访问的路径不存在
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new UsernameNotFoundException("请先登录后再操作！");
                } else {
                    return; // 访问路径出错
                }
            }

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(returnRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足！！ 请尝试联系管理员");
    }


    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
