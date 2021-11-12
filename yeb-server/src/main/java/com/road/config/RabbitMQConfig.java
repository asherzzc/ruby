package com.road.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.road.pojo.MailLog;
import com.road.service.IMailLogService;
import com.road.util.MailLogConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouc
 * @date 2021/9/30 11:11
 * @description RabbitMQ 配置类
 * @since 1.0
 */
@Configuration
public class RabbitMQConfig {

    private final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    private IMailLogService mailLogService;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);

        // 消息发送成功回调
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            assert data != null;
            String msgId = data.getId();
            if (ack) {
                logger.info("{} --> 消息发送成功", msgId);
                mailLogService.update(new UpdateWrapper<MailLog>().set("status", 1).eq("msgId", msgId));
            } else {
                logger.error("{} --> 消息发送失败", msgId);
            }
        });

        // 消息发送失败回调
        rabbitTemplate.setReturnCallback(((message, repCode, repText, exchange, routingKey) -> {
            logger.error("{} --> 消息不到Queue", message.getBody());
        }));
        return rabbitTemplate;

    }

    @Bean
    public Queue queue() {
        return new Queue(MailLogConstant.QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MailLogConstant.EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailLogConstant.ROUTING_KEY_NAME);
    }
}
