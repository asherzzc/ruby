package com.road.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.road.pojo.CommonResult;
import com.road.pojo.Menu;
import com.road.pojo.MenuRole;
import com.road.pojo.Role;
import com.road.service.IMenuRoleService;
import com.road.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/system/basic/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "查询所有角色信息")
    @GetMapping("/")
    public List<Role> getRoleList() {
        return roleService.list();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{rid}")
    public CommonResult deleteRole(@PathVariable("rid") Integer rid) {
        if (roleService.removeById(rid)) {
            return CommonResult.success("删除成功！");
        }
        return CommonResult.error("删除失败！");
    }

    @ApiOperation(value = "添加一个角色")
    @PutMapping("/")
    public CommonResult addRole(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (roleService.save(role)) {
            return CommonResult.success("添加成功！");
        }
        return CommonResult.error("添加失败！");
    }

    @ApiOperation(value = "获得所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return roleService.getAllMenus();
    }

    /**
     * 用于展示角色拥有的菜单
     *
     * @param rid
     * @return
     */
    @ApiOperation(value = "通过角色ID获得菜单ID")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable("rid") Integer rid) {
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid)).
                stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色可使用的菜单")
    @PutMapping("/updateMenu")
    public CommonResult updateMenu(Integer rid, Integer[] mids) {
        return menuRoleService.updateMenu(rid, mids);
    }
}

