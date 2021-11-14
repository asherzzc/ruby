package com.road.config.security;

import com.road.filter.LoginFilter;
import com.road.handler.SecurityAccessDeniedHandler;
import com.road.handler.SecurityAuthenticationEntryPoint;
import com.road.pojo.Admin;
import com.road.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: zhouc
 * @Date: 2021/9/9 10:50
 * @Since： 1.0
 * @Description: SpringSecurity配置类
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private SecurityAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private SecurityAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthorityForUrlFilter urlFilter;
    @Autowired
    private AuthorityForRoleFilter roleFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilter);
                        o.setAccessDecisionManager(roleFilter);
                        return o;
                    }
                })
                // 禁用缓存
                .and()
                .headers()
                .cacheControl();

        // 登录拦截器
        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        // 异常处理器
        http.exceptionHandling()
                // 权限不足时的异常拦截处理
                .accessDeniedHandler(accessDeniedHandler)
                // 认证失败时的异常拦截处理
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/login",
                        "/logout",
                        "/css/**",
                        "/js/**",
                        "/index.html",
                        "/favicon.ico",
                        "/webjars/**",
                        "/",
                        "/doc.html",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/kaptcha",
                        "/ws/**",
                        "/chat/**"
                );
    }

    /**
     * 自定义userDetailsService
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminService.getAdminInfoByUsername(username);

            if (admin != null) {
                admin.setRoles(adminService.getRolesByAdminId(admin.getId())); // 设置权限信息
                return admin;
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }
}
