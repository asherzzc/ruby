package com.road.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.road.pojo.AdminRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer insertRoles(@Param("adminId") Integer id, @Param("rids") Integer[] rids);
}
