package com.road.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zhouc
 * @date: 2021/9/18 9:45
 * @since： 1.0
 * @description: 配合mybatis plus 分页使用
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePageBean {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<?> data;
}
