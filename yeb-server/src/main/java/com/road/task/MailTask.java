package com.road.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.road.pojo.Employee;
import com.road.pojo.MailLog;
import com.road.service.IEmployeeService;
import com.road.service.IMailLogService;
import com.road.util.MailLogConstant;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhouc
 * @date 2021/10/10 15:11
 * @description 邮件发送失败处理
 * @since 1.0
 */
@Component
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask() {

        // 找到需要重试并且重试时间到达的对象
        List<MailLog> mailLogList = mailLogService.list(new QueryWrapper<MailLog>().
                eq("status", 0).lt("tryTime", LocalDateTime.now()));

        mailLogList.forEach(mailLog -> {
            String msgId = mailLog.getMsgId();
            // 对所有状态为 0 并且 重试次数为<=3的消息重发
            if (mailLog.getCount() <= 3) {
                mailLogService.update(new UpdateWrapper<MailLog>().
                        set("count", mailLog.getCount() + 1).
                        set("updateTime", LocalDateTime.now()).
                        set("tryTime", LocalDateTime.now().plusMinutes(MailLogConstant.TRY_TIME)).
                        eq("msgId", msgId));

                Employee employee = employeeService.getOne(new QueryWrapper<Employee>().eq("id", mailLog.getEid()));
                // 再次发送
                rabbitTemplate.convertAndSend(MailLogConstant.EXCHANGE_NAME, MailLogConstant.ROUTING_KEY_NAME, employee,
                        new CorrelationData(msgId));
            } else {
                mailLogService.update(new UpdateWrapper<MailLog>().
                        set("status", 2).
                        set("updateTime", LocalDateTime.now()).
                        eq("msgId", msgId));
            }
        });


    }

}
