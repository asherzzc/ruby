package com.road.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.road.pojo.*;
import com.road.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "分页获取员工信息")
    @GetMapping("/")
    public ResponsePageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer start,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              Employee employee,
                                              LocalDate[] timeLimit) {
        return employeeService.getEmployeeByPage(start, size, employee, timeLimit);
    }

    // ======================== 添加员工 ============================= //
    @ApiOperation(value = "获取民族信息")
    @GetMapping("/nation")
    public List<Nation> getNationList() {
        return nationService.list();
    }

    @ApiOperation(value = "获取部门信息")
    @GetMapping("/department")
    public List<Department> getDepartmentList() {
        return departmentService.list();
    }

    @ApiOperation(value = "获取职称信息")
    @GetMapping("/joblevel")
    public List<Joblevel> getJobLevelList() {
        return joblevelService.list();
    }

    @ApiOperation(value = "获取政治面貌信息")
    @GetMapping("/politics")
    public List<PoliticsStatus> getPoliticsStatusList() {
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取职位信息")
    @GetMapping("/position")
    public List<Position> getPositionList() {
        return positionService.list();
    }

    @ApiOperation(value = "获取最大工号")
    @GetMapping("/maxWorkId")
    public CommonResult generateMaxWorkId() {
        return employeeService.generateMaxWorkId();
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public CommonResult addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public CommonResult updateEmployee(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) {
            return CommonResult.success("更新成功！");
        }
        return CommonResult.error("更新失败！");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public CommonResult deleteEmployee(@PathVariable("id") Integer id) {
        if (employeeService.removeById(id)) {
            return CommonResult.success("删除成功！");
        }
        return CommonResult.error("删除失败！");
    }

    @ApiOperation(value = "导出员工数据")
    @PostMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        // 设置响应头
        response.setHeader("content-type", "application/octet-stream");
        // 获得响应流
        ServletOutputStream outputStream = null;
        // 查询数据
        List<Employee> allEmployee = employeeService.getAllEmployee(null);
        // 构建Export对象
        ExportParams exportParams = new ExportParams("员工数据表", "员工数据");
        // 数据对象构建
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Employee.class, allEmployee);
        try {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("员工数据.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @ApiOperation(value = "导入员工数据")
    @PostMapping(value = "/import")
    public CommonResult importEmployee(MultipartFile file) {
        ImportParams params = new ImportParams();
        // 去掉第一行
        params.setTitleRows(1);
        try {
            // 读取数据
            List<Employee> employees = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            // 民族
            List<Nation> nation = nationService.list();
            // 职称
            List<Joblevel> joblevel = joblevelService.list();
            // 部门
            List<Department> department = departmentService.list();
            // 职位
            List<Position> position = positionService.list();
            // 政治面貌
            List<PoliticsStatus> politicsStatus = politicsStatusService.list();

            employees.forEach(employee -> {
                // 将外部表数据修改为Id
                employee.setNationId(nation.get(nation.indexOf(new Nation(employee.getNation().getName()))).getId());
                employee.setJobLevelId(joblevel.get(joblevel.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
                employee.setDepartmentId(department.get(department.indexOf(new Department(employee.getDepartment().getName()))).getId());
                employee.setPosId(position.get(position.indexOf(new Position(employee.getPosition().getName()))).getId());
                employee.setPoliticId(politicsStatus.get(politicsStatus.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
            });
            // 插入数据
            if (employeeService.saveBatch(employees)) {
                return CommonResult.success("导入成功！");
            }
            return CommonResult.error("导入失败！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.error("导入失败！");
    }
}
