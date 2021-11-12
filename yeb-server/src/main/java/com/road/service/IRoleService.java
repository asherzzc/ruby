package com.road.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.road.pojo.Menu;
import com.road.pojo.Role;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface IRoleService extends IService<Role> {

    List<Menu> getAllMenus();
}
