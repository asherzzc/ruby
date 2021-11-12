package com.road.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: zhouc
 * @date: 2021/9/19 16:34
 * @since： 1.0
 * @description: 
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MailSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailSpringApplication.class, args);
    }
}
