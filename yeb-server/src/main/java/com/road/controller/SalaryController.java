package com.road.controller;


import com.road.pojo.CommonResult;
import com.road.pojo.Salary;
import com.road.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private ISalaryService salaryService;

    @ApiOperation(value = "获取所有账套信息")
    @GetMapping("/")
    public List<Salary> getAll() {
        return salaryService.list();
    }

    @ApiOperation(value = "更新账套信息")
    @PostMapping("/")
    public CommonResult updateSalary(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) {
            return CommonResult.success("更新成功！");
        }
        return CommonResult.error("更新失败！");
    }

    @ApiOperation(value = "添加账套信息")
    @PutMapping("/")
    public CommonResult addSalary(@RequestBody Salary salary) {
        salary.setCreateDate(LocalDateTime.now());
        if (salaryService.save(salary)) {
            return CommonResult.success("添加成功！");
        }
        return CommonResult.error("添加失败！");
    }

    @ApiOperation(value = "删除账套信息")
    @DeleteMapping("/{id}")
    public CommonResult addSalary(@PathVariable String id) {
        if (salaryService.removeById(id)) {
            return CommonResult.success("删除成功！");
        }
        return CommonResult.error("删除失败！");
    }
}
