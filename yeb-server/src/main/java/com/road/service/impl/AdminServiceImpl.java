package com.road.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.road.mapper.AdminMapper;
import com.road.mapper.RoleMapper;
import com.road.pojo.Admin;
import com.road.pojo.AdminLogin;
import com.road.pojo.CommonResult;
import com.road.pojo.Role;
import com.road.service.IAdminService;
import com.road.util.AdminUtil;
import com.road.util.JwtGenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtGenerateUtil jwtUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public CommonResult login(AdminLogin login, HttpServletRequest request) {

        // 判断验证码
        String kaptcha = (String) request.getSession().getAttribute("kaptcha");
        if (StringUtils.isEmpty(kaptcha) && !kaptcha.equals(login.getCode())) {
            return CommonResult.error("验证码错误！");
        }
        System.out.println(login.getUsername());
        log.info("username: {}", login.getUsername());
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
        // 判断错误情况
        if (userDetails == null || !passwordEncoder.matches(login.getPassword(), userDetails.getPassword())) {
            return CommonResult.error("用户名或密码错误！");
        }
        // 账号是否被锁定
        if (!userDetails.isEnabled()) {
            return CommonResult.error("账号已被锁定，请联系管理员");
        }

//      将登录对象的信息生成Authentication 放置到SpringSecurity上下文中
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("login info: {}", SecurityContextHolder.getContext().getAuthentication());

        // 生成Token并返回 同时告诉前端Token传输的格式
        String token = jwtUtil.generateToken(userDetails);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("tokenHeader", tokenHeader);
        map.put("tokenHead", tokenHead);
        return CommonResult.success("登录成功！", map);
    }

    /**
     * 根据用户名获得用户信息 单表查询用MybatisPlus
     *
     * @param username
     * @return
     */
    @Override
    public Admin getAdminInfoByUsername(String username) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", 1));
        log.info("admin:{}", admin.getUsername());
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).
                eq("enabled", true));
    }

    @Override
    public List<Role> getRolesByAdminId(Integer adminId) {
        return roleMapper.getRolesByAdminId(adminId);
    }

    @Override
    public List<Admin> getAllAdminOrSearch(String keyword) {
        return adminMapper.getAllAdminOrSearch(keyword, AdminUtil.getCurrentAdmin().getId());
    }

    @Override
    public CommonResult updateUserPassword(Integer adminId, String oldPass, String pass) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 查看输入的旧密码是否和数据库存储的一致
        if (encoder.matches(oldPass, admin.getPassword())) {
            // 一致更新
            admin.setPassword(encoder.encode(pass));
            int result = adminMapper.updateById(admin);
            if (1 == result) {
                return CommonResult.success("更新成功！");
            }
        }
        // 不一致退出
        return CommonResult.error("更新失败！");
    }

    @Override
    public CommonResult updateAdminUserFace(String url, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(url);
        if (adminMapper.updateById(admin) == 1) {
            // 修改全局对象
            Admin principal = (Admin) authentication.getPrincipal();
            principal.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return CommonResult.success("头像更新成功！", url);
        }
        return CommonResult.error("更新失败！");
    }

}
