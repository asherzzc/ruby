package com.road.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.road.pojo.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 获取用户信息
     *
     * @param keyword
     * @return
     */
    List<Admin> getAllAdminOrSearch(@Param("keyword") String keyword, @Param("id") Integer id);
}
