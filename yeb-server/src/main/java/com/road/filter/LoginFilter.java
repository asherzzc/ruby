package com.road.filter;

import com.road.util.JwtGenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: zhouc
 * @Date: 2021/9/9 11:22
 * @Since： 1.0
 * @Description: 登录拦截器
 */
@Component
public class LoginFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtGenerateUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    
    static Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(tokenHeader); // 拿到token值
        // 如果格式正确并且能拿到值
        if (header != null && header.startsWith(tokenHead)) {
            String token = header.substring(tokenHead.length() + 1);
            String username = jwtUtil.getTokenUsername(token);
            // 如果在Token中可以解析到username属性
            log.info("context: {}", SecurityContextHolder.getContext().getAuthentication());

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.info("enter login filter method");
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 当解析完成后发现SpringSecurity的上下文中没有对应的对象就将对象放入 前提是Token合法
                if (jwtUtil.isTokenCanBeRefresh(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
