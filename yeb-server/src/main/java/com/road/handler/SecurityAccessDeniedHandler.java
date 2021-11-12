package com.road.handler;

import com.alibaba.fastjson.JSON;
import com.road.pojo.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: zhouc
 * @Date: 2021/9/9 13:34
 * @Since： 1.0
 * @Description: 权限不足异常处理
 */
@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        // 返回数据
        CommonResult result = new CommonResult(403, "权限不足", null);
        writer.write(JSON.toJSONString(result));
    }
}
