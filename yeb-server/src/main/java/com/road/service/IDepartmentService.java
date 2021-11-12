package com.road.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.road.pojo.CommonResult;
import com.road.pojo.Department;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> getAllDepartment();

    CommonResult addDepartment(Department department);

    CommonResult deleteDepartment(Integer id);
}
