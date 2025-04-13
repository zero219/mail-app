package com.mail.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MailGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailGatewayApplication.class, args);
    }

}
