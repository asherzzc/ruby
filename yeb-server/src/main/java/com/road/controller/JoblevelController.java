package com.road.controller;


import com.road.pojo.CommonResult;
import com.road.pojo.Joblevel;
import com.road.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhouc
 * @description: 职称管理
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {
    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "查询所有职称")
    @GetMapping("/")
    public List<Joblevel> getPositionList() {
        return joblevelService.list();
    }

    @ApiOperation(value = "删除职称")
    @DeleteMapping("/{id}")
    public CommonResult deletePosition(@PathVariable("id") Integer id) {
        if (joblevelService.removeById(id)) {
            return CommonResult.success("删除成功！");
        }
        return CommonResult.error("删除失败！");
    }

    @ApiOperation(value = "批量删除职位")
    @DeleteMapping("/")
    public CommonResult deletePositionByIds(Integer[] ids) {
        if (joblevelService.removeByIds(Arrays.asList(ids))) {
            return CommonResult.success("批量删除成功！");
        }
        return CommonResult.error("批量删除失败！");
    }

    @ApiOperation(value = "更新职称信息")
    @PostMapping("/")
    public CommonResult updatePosition(@RequestBody Joblevel joblevel) {
        if (joblevelService.updateById(joblevel)) {
            return CommonResult.success("更新成功！");
        }
        return CommonResult.error("更新失败！");
    }

    @ApiOperation(value = "添加一个职称")
    @PutMapping("/")
    public CommonResult addPosition(@RequestBody Joblevel joblevel) {
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)) {
            return CommonResult.success("添加成功！");
        }
        return CommonResult.error("添加失败！");
    }

}
