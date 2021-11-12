package com.road.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.road.pojo.Admin;
import com.road.pojo.AdminLogin;
import com.road.pojo.CommonResult;
import com.road.pojo.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface IAdminService extends IService<Admin> {

    CommonResult login(AdminLogin login, HttpServletRequest request);

    /**
     * 通过用户名获得用户信息
     *
     * @param username
     * @return
     */
    Admin getAdminInfoByUsername(String username);

    /**
     * 根据用户ID获得角色信息
     *
     * @param adminId
     * @return
     */
    List<Role> getRolesByAdminId(Integer adminId);

    List<Admin> getAllAdminOrSearch(String keyword);

    CommonResult updateUserPassword(Integer adminId, String oldPass, String pass);

    CommonResult updateAdminUserFace(String url, Integer id, Authentication authentication);
}
