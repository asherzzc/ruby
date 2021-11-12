package com.road.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhouc
 * @Date: 2021/9/8 10:39
 * @Since： 1.0
 * @Description: 公共对象返回类
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {
    private String message;
    private Object object;
    private Integer code;

    public CommonResult(Integer code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public CommonResult(String message) {
        this.message = message;
        this.object = null;
    }

    public static CommonResult success() {
        return new CommonResult(200, "操作成功", null);
    }

    public static CommonResult success(String message) {
        return new CommonResult(200, message, null);
    }

    public static CommonResult success(String message, Object object) {
        return new CommonResult(200, message, object);
    }

    public static CommonResult error(String message) {
        return new CommonResult(500, message, null);
    }

    public static CommonResult error(String message, Object object) {
        return new CommonResult(500, message, object);
    }
}
