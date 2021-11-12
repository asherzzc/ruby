package com.road.controller;


import com.road.pojo.CommonResult;
import com.road.pojo.Position;
import com.road.service.IPositionService;
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
 * @Description: 职位管理类
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/system/basic/position")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "查询所有职位")
    @GetMapping("/")
    public List<Position> getPositionList() {
        return positionService.list();
    }

    @ApiOperation(value = "删除职位")
    @DeleteMapping("/{id}")
    public CommonResult deletePosition(@PathVariable("id") Integer id) {
        if (positionService.removeById(id)) {
            return CommonResult.success("删除成功！");
        }
        return CommonResult.error("删除失败！");
    }

    @ApiOperation(value = "批量删除职位")
    @DeleteMapping("/")
    public CommonResult deletePositionByIds(Integer[] ids) {
        if (positionService.removeByIds(Arrays.asList(ids))) {
            return CommonResult.success("批量删除成功！");
        }
        return CommonResult.error("批量删除失败！");
    }

    @ApiOperation(value = "更新职称信息")
    @PostMapping("/")
    public CommonResult updatePosition(@RequestBody Position position) {
        if (positionService.updateById(position)) {
            return CommonResult.success("更新成功！");
        }
        return CommonResult.error("更新失败！");
    }

    @ApiOperation(value = "添加一个职位")
    @PutMapping("/")
    public CommonResult addPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)) {
            return CommonResult.success("添加成功！");
        }
        return CommonResult.error("添加失败！");
    }

}
