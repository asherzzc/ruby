package com.road.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.road.pojo.AdminRole;
import com.road.pojo.CommonResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface IAdminRoleService extends IService<AdminRole> {

    CommonResult updateRoles(Integer id, Integer[] rids);
}
