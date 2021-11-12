package com.road.controller;


import com.road.pojo.CommonResult;
import com.road.pojo.Department;
import com.road.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;
    
    @ApiOperation(value = "查询所有部门信息")
    @GetMapping("/")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "插入一个部门")
    @PutMapping("/")
    public CommonResult addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }

    @ApiOperation(value = "删除一个部门")
    @DeleteMapping("/{id}")
    public CommonResult deleteDepartment(@PathVariable("id") Integer id) {
        return departmentService.deleteDepartment(id);
    }
}
