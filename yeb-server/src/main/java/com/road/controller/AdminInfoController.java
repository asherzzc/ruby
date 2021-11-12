package com.road.controller;

import com.road.pojo.Admin;
import com.road.pojo.CommonResult;
import com.road.service.IAdminService;
import com.road.util.FastDFSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author zhouc
 * @date 2021/11/10 21:04
 * @description 用户个人中心
 * @since 1.0
 */
@RestController
public class AdminInfoController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "更新用户信息")
    @PostMapping("/admin/info")
    public CommonResult updateInfo(@RequestBody Admin admin, Authentication authentication) {
        if (adminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities()));
            return CommonResult.success("更新成功！");
        }
        return CommonResult.success("更新失败！");
    }

    @ApiOperation(value = "更新用户密码")
    @PostMapping("/admin/pass")
    public CommonResult updatePassword(@RequestBody Map<String, Object> map) {
        Integer adminId = (Integer) map.get("adminId");
        String oldPass = (String) map.get("oldPass");
        String pass = (String) map.get("pass");
        return adminService.updateUserPassword(adminId, oldPass, pass);
    }

    @ApiOperation(value = "更新用户头像")
    @PostMapping("/admin/userface")
    public CommonResult updateUserFace(MultipartFile file, Integer id, Authentication authentication) {
        //上传之后的文件名
        String[] filePath = FastDFSUtils.upload(file);
        //获取 url ， 初始文件名，上传之后的文件名
        String url = FastDFSUtils.getTrackerUrl() + filePath[0] + "/" + filePath[1];
        return adminService.updateAdminUserFace(url, id, authentication);
    }
}
