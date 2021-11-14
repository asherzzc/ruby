package com.road.controller;

import com.road.pojo.Admin;
import com.road.pojo.ChatMessage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author zhouc
 * @date 2021/11/10 20:28
 * @description
 * @since 1.0
 */
@RestController
@Api(value = "消息中转站", tags = "消息中转站")
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMessage chatMessage) {
        // 1.获取当前登录用户
        Admin admin = (Admin) authentication.getPrincipal();
        // 2.登录的用户名
        chatMessage.setFrom(admin.getUsername());
        // 3.显示的用户名
        chatMessage.setFormNickName(admin.getName());
        // 4.发送时间
        chatMessage.setDate(LocalDateTime.now());
        // 1.发送给谁 2.队列 3.发送的消息
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getTo(), "/queue/chat", chatMessage);
    }
}