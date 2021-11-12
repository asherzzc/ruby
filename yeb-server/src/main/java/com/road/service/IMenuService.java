package com.road.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.road.pojo.Menu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 根据用户ID查询对应的菜单
     *
     * @return
     */
    List<Menu> getMenusByAdminId();

    List<Menu> getMenusWithRoles();


}
