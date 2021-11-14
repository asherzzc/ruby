package com.road.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhouc
 * @date 2021/11/14 13:35
 * @description
 * @since 1.0
 */
@Controller
public class LoginRedirectController {
    @ApiOperation("前端页面跳转")
    @RequestMapping("/")
    public String toIndex() {
        return "redirect:/index.html";
    }
}
