package com.road.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zhouc
 * @date: 2021/9/17 23:07
 * @since： 1.0
 * @description: mybatis-plus配置类
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 分页插件配置
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
