package com.road.controller;

import com.road.pojo.Admin;
import com.road.pojo.AdminLogin;
import com.road.pojo.CommonResult;
import com.road.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @description: 登录操作类
 * @Author: zhouc
 * @Date: 2021/9/9 9:06
 * @Since： 1.0
 */
@RestController
//@RequestMapping(value="/system/admin")
public class AdminLoginController {

    @Resource
    private IAdminService adminService;

    private final Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

    @ApiOperation(value = "登录方法")
    @PostMapping("/login")
    public CommonResult login(@RequestBody AdminLogin login, HttpServletRequest request) {
        return adminService.login(login, request);
    }

    /**
     * 注销交给前端来做 因为对资源的操作都需要Token的使用 所以注销让前端删除Token并返回登录页即可
     * 后端只需要返回状态码交给前端判断
     *
     * @return {commonResult}
     */
    @ApiOperation(value = "注销方法")
    @PostMapping("/logout")
    public CommonResult logout() {
        return CommonResult.success("注销成功", null);
    }

    @ApiOperation(value = "根据用户名获得用户信息")
    @GetMapping("/admin/info")
    public Admin adminInfo(Principal principal) {
        Admin info = adminService.getAdminInfoByUsername(principal.getName());
        info.setRoles(adminService.getRolesByAdminId(info.getId()));
        return info;
    }
}
