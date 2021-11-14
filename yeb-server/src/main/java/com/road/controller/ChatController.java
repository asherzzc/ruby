package com.road.controller;

import com.road.pojo.Admin;
import com.road.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhouc
 * @date 2021/11/13 22:07
 * @description 在线聊天控制器
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    @Autowired
    private IAdminService adminService;


    @GetMapping("/admin")
    @ApiOperation(value = "获取操作操作员信息")
    public List<Admin> getAllAdmin(String keyword) {
        return adminService.getAllAdminOrSearch(keyword);
    }
}
