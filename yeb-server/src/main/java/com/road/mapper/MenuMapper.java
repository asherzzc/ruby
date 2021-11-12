package com.road.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.road.pojo.Menu;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenusByAdminId(Integer adminId);

    List<Menu> getMenusWithRoles();

    /**
     * 获得所有的菜单信息
     *
     * @return
     */
    List<Menu> getAllMenus();
}
