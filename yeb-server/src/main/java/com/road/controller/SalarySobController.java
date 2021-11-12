package com.road.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.road.pojo.CommonResult;
import com.road.pojo.Employee;
import com.road.pojo.ResponsePageBean;
import com.road.pojo.Salary;
import com.road.service.IEmployeeService;
import com.road.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhouc
 * @date 2021/11/10 19:13
 * @description
 * @since 1.0
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobController {

    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IEmployeeService employeeService;


    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalary() {
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有员工账套(分页)")
    @GetMapping("/")
    public ResponsePageBean getAllEmployeeWithSalary(@RequestParam("start") Integer start,
                                                     @RequestParam("size") Integer size) {
        return employeeService.getAllEmployeeWithSalary(start, size);
    }

    @ApiOperation(value = "更新员工账套")
    @PostMapping("/")
    public CommonResult updateEmployeeWithSalary(Integer eid, Integer sid) {
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id", eid))) {
            return CommonResult.success("更新成功！");
        }
        return CommonResult.success("更新失败！");
    }
}
