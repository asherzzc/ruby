package com.road.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.road.pojo.CommonResult;
import com.road.pojo.MenuRole;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 修改角色对应的mid
     *
     * @param rid
     * @param mids
     * @return
     */
    CommonResult updateMenu(Integer rid, Integer[] mids);
}
