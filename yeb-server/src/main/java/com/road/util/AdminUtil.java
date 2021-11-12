package com.road.util;

import com.road.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: zhouc
 * @date: 2021/9/17 15:50
 * @since： 1.0
 * @description: 获取Admin的重要信息
 */
public class AdminUtil {

    /**
     * 获得当前Admin对象
     *
     * @return
     */
    public static Admin getCurrentAdmin() {
        return ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
