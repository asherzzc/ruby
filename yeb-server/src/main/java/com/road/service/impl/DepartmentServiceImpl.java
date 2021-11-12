package com.road.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.road.mapper.DepartmentMapper;
import com.road.pojo.CommonResult;
import com.road.pojo.Department;
import com.road.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment(-1);
    }

    @Override
    public CommonResult addDepartment(Department department) {
        department.setEnabled(true); // 默认为true
        departmentMapper.addDepartment(department);
        if (department.getResult() == 1) {
            return CommonResult.success("添加成功!", department);
        }
        return CommonResult.error("添加失败");
    }

    @Override
    public CommonResult deleteDepartment(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        Integer result = department.getResult();

        if (result == -2) {
            return CommonResult.error("删除失败,此部门是其他部门的子部门");
        } else if (result == -1) {
            return CommonResult.error("删除失败,此部门下仍有员工");
        }
        return CommonResult.success("删除成功!", department);
    }
}
