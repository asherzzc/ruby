package com.road.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhouc
 * @Date: 2021/9/9 14:38
 * @Since： 1.0
 */
@RestController
public class TestController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "hello controller";
    }
    
    @GetMapping(value = "/employee/basic/test")
    public String employee() {
        return "test employee";
    }

    @GetMapping(value = "/system/cfg/test")
    public String system() {
        return "test system";
    }
}
