package com.road.util;

/**
 * @author zhouc
 * @date 2021/9/30 10:42
 * @description rabbitmq消息数据库常量
 * @since 1.0
 */
public class MailLogConstant {

    /**
     * 投递成功状态
     */
    public static final Integer SUCCESS_STATUS = 1;
    /**
     * 投递失败状态
     */
    public static final Integer FAILURE_STATUS = 2;
    /**
     * 投递中状态
     */
    public static final Integer SENDING_STATUS = 0;
    /**
     * 允许投递失败的次数
     */
    public static final Integer TRY_COUNT = 3;
    /**
     * 多久后开始自动处理未确认信息 单位-->分钟
     */
    public static final Integer TRY_TIME = 1;
    /**
     * 邮件交换机名称
     */
    public static final String EXCHANGE_NAME = "mail.exchange";
    /**
     * 邮件队列名称
     */
    public static final String QUEUE_NAME = "mail.queue";
    /**
     * 路由Key名称
     */
    public static final String ROUTING_KEY_NAME = "mail.send";
}
