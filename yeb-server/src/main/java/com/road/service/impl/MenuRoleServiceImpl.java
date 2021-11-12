package com.road.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.road.mapper.MenuRoleMapper;
import com.road.pojo.CommonResult;
import com.road.pojo.MenuRole;
import com.road.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public CommonResult updateMenu(Integer rid, Integer[] mids) {
        if (mids == null || mids.length == 0) {
            return CommonResult.success("更新成功！");
        }
        // 首先对应rid中的菜单信息删除
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        // 然后再把mids中的值插入
        if (menuRoleMapper.insertMids(rid, mids) == mids.length) {
            return CommonResult.success("更新成功！");
        }
        return CommonResult.error("更新失败！");
    }
}
