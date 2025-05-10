package com.mail.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.mail.admin.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class MailAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailAdminApplication.class, args);
    }

}
