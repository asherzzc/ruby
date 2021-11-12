package com.road.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.road.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 插入对应角色的菜单id值
     *
     * @param rid
     * @param mids
     * @return
     */
    Integer insertMids(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
