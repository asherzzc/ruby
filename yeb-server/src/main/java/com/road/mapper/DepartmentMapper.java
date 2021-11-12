package com.road.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.road.pojo.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartment(@Param("id") Integer id);

    void addDepartment(Department department);

    void deleteDepartment(Department department);
}
