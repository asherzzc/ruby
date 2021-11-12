package com.road.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.road.mapper.MenuMapper;
import com.road.mapper.RoleMapper;
import com.road.pojo.Menu;
import com.road.pojo.Role;
import com.road.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private MenuMapper menuMapper;
        
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
