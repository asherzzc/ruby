package com.road.controller;


import com.road.pojo.Admin;
import com.road.pojo.CommonResult;
import com.road.pojo.Role;
import com.road.service.IAdminRoleService;
import com.road.service.IAdminService;
import com.road.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户管理类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IAdminRoleService adminRoleService;

    /**
     * 查询用户信息(包括角色信息) 同时支持搜索功能
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "用户展示与查询")
    @GetMapping("/")
    public List<Admin> getAllAdminOrSearch(String keyword) {
        return adminService.getAllAdminOrSearch(keyword);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public CommonResult deleteAdmin(@PathVariable("id") Integer id) {
        if (adminService.removeById(id)) {
            return CommonResult.success("删除成功!");
        }
        return CommonResult.error("删除失败!");
    }

    /**
     * 更新用户信息
     *
     * @param admin
     * @return
     */
    @ApiOperation(value = "更新用户信息")
    @PostMapping("/")
    public CommonResult updateAdmin(@RequestBody Admin admin) {
        if (adminService.updateById(admin)) {
            return CommonResult.success("更新成功!");
        }
        return CommonResult.success("更新失败!");
    }


    /**
     * 获取用户角色 用于展示
     *
     * @return
     */
    @ApiOperation(value = "获取用户角色")
    @GetMapping("/role")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    /**
     * 更新用户角色(删除)
     *
     * @return
     */
    @ApiOperation(value = "更新用户角色")
    @PostMapping("/role")
    public CommonResult updateRoles(Integer id, Integer[] rids) {
        return adminRoleService.updateRoles(id, rids);
    }
}
