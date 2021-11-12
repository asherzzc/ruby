package com.road.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.road.mapper.EmployeeMapper;
import com.road.mapper.MailLogMapper;
import com.road.pojo.CommonResult;
import com.road.pojo.Employee;
import com.road.pojo.MailLog;
import com.road.pojo.ResponsePageBean;
import com.road.service.IEmployeeService;
import com.road.util.MailLogConstant;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private MailLogMapper mailLogMapper;
    @Autowired
    private RabbitTemplate template;

    @Override
    public ResponsePageBean getEmployeeByPage(Integer start, Integer size, Employee employee, LocalDate[] timeLimit) {
        Page<Employee> page = new Page<>(start, size);
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeByPage(page, employee, timeLimit);
        return new ResponsePageBean(employeeIPage.getTotal(), employeeIPage.getRecords());
    }

    /**
     * 添加员工
     * 需要额外处理合同期限
     *
     * @param employee
     * @return
     */
    @Override
    public CommonResult addEmployee(Employee employee) {
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long day = beginContract.until(endContract, ChronoUnit.DAYS);

        // DecimalFormat 数字格式化类
        DecimalFormat format = new DecimalFormat("0.##");
        employee.setContractTerm(Double.parseDouble(format.format((day / 365))));

        if (employeeMapper.insert(employee) > 0) {
            // 为msgId生成一个UUID
            String msgId = UUID.randomUUID().toString();
            // 先将消息信息入库
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(MailLogConstant.SENDING_STATUS); // 未确认 默认为0
            mailLog.setRouteKey(MailLogConstant.ROUTING_KEY_NAME);
            mailLog.setExchange(MailLogConstant.EXCHANGE_NAME);
            mailLog.setCount(0); // 最多重新检测三次 初始值为0
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailLogConstant.TRY_TIME)); // 发送失败后 一分钟重试一次
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);

            // 将员工对象发送出去 使用路由模式
            template.convertAndSend(MailLogConstant.EXCHANGE_NAME, MailLogConstant.ROUTING_KEY_NAME, employee,
                    new CorrelationData(msgId));
            return CommonResult.success("插入成功！");
        }
        return CommonResult.error("插入失败！");
    }

    /**
     * 获取最大的工号ID
     *
     * @return
     */
    @Override
    public CommonResult generateMaxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("MAX(workID)"));
        String workId = (String) maps.get(0).get("MAX(workID)");
        return CommonResult.success("获取成功！", (Integer.parseInt(workId) + 1 + ""));
    }

    @Override
    public List<Employee> getAllEmployee(Integer id) {
        return employeeMapper.getAllEmployee(id);
    }

    /**
     * 获取所有员工工资账套信息
     *
     * @param start
     * @param size
     * @return
     */
    @Override
    public ResponsePageBean getAllEmployeeWithSalary(Integer start, Integer size) {
        Page<Employee> page = new Page<>(start, size);
        IPage<Employee> salaryPage = employeeMapper.getAllEmployeeWithSalary(page);
        return new ResponsePageBean(salaryPage.getTotal(), salaryPage.getRecords());
    }


}
