package com.road.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.road.pojo.CommonResult;
import com.road.pojo.Employee;
import com.road.pojo.ResponsePageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface IEmployeeService extends IService<Employee> {

    ResponsePageBean getEmployeeByPage(Integer start, Integer size, Employee employee, LocalDate[] timeLimit);

    CommonResult addEmployee(Employee employee);

    CommonResult generateMaxWorkId();


    List<Employee> getAllEmployee(Integer id);

    ResponsePageBean getAllEmployeeWithSalary(Integer start, Integer size);
}
