package com.road.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.road.mapper.AdminRoleMapper;
import com.road.pojo.AdminRole;
import com.road.pojo.CommonResult;
import com.road.service.IAdminRoleService;
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
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {
    
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    public CommonResult updateRoles(Integer id, Integer[] rids) {
        if (rids == null || rids.length == 0) {
            return CommonResult.error("操作失败!");
        }
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", id));
        if (adminRoleMapper.insertRoles(id, rids) == rids.length) {
            return CommonResult.success("操作成功!");
        }
        return CommonResult.error("操作失败!");
    }
}
