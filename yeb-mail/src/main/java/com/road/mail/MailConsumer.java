package com.road.mail;

import com.rabbitmq.client.Channel;
import com.road.pojo.Employee;
import com.road.util.MailLogConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * @author: zhouc
 * @date: 2021/9/19 16:35
 * @since： 1.0
 * @description: 邮件消费者
 */
@Component
public class MailConsumer {


    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private RedisTemplate redisTemplate;

    static Logger log = LoggerFactory.getLogger(MailConsumer.class);

    @RabbitListener(
            queuesToDeclare = @Queue(MailLogConstant.QUEUE_NAME)
    )
    public void sendMail(Message message, Channel channel) {
        redisTemplate.opsForValue().set("test", "test");
        // 获取员工对象
        Employee employee = (Employee) message.getPayload();
        // 获得消息Tag
        MessageHeaders headers = message.getHeaders();
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 获得发送时传入的msgId
        String msgId = (String) headers.get("spring_returned_message_correlation");
        // Redis Hash操作对象
        HashOperations hashOperations = redisTemplate.opsForHash();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {


            if (hashOperations.entries("mail_log").containsValue(msgId)) {
                log.error("{} --> 消息已存在", msgId);
                channel.basicAck(tag, false);
            }
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("入职欢迎邮件");
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(employee.getEmail());
            helper.setSentDate(new Date());
            // 这里引入的是Template的Context
            Context context = new Context();
            // 设置模板中的变量
            context.setVariable("name", employee.getName());
            context.setVariable("workId", employee.getWorkID());
            context.setVariable("speciality", employee.getSpecialty());
            context.setVariable("school", employee.getSchool());
            context.setVariable("tipTop", employee.getTiptopDegree());
            // 第一个参数为模板的名称
            String process = templateEngine.process("mail.html", context);
            // 第二个参数true表示这是一个html文本
            helper.setText(process, true);
            javaMailSender.send(mimeMessage);
            log.info("=================== 邮件发送成功 ===================");
            hashOperations.put("mail_log", msgId, "OK");
            log.info("{} --> 消息消费成功", msgId);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("邮件发送失败:{}", e.getMessage());
            try {
                log.info("{} --> 消息消费失败", msgId);
                channel.basicNack(tag, false, true); // 第三个参数为消息是否入队
            } catch (IOException ioException) {
                log.info("{} --> 消息队列回调IO异常", msgId);
                ioException.printStackTrace();
            }
        }
    }
}
