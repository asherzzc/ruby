package com.road.exception;

import com.road.pojo.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: zhouc
 * @Date: 2021/9/12 16:22
 * @Since： 1.0
 * @Description: 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = SQLException.class)
//    public CommonResult sqlException() {
//        return CommonResult.error("数据库操作异常");
//    }

    @ExceptionHandler(value = NullPointerException.class)
    public CommonResult nullPointerException() {
        return CommonResult.error("你又有新的空指针异常啦!");
    }
}
