package com.cxg.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 消费者启动类
 */

@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
        System.out.println("消费端启动成功！！！");
    }

}
