package com.road.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.road.pojo.Role;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 获得用户的角色信息
     *
     * @param adminId
     * @return
     */
    List<Role> getRolesByAdminId(Integer adminId);
}
