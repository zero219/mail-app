package com.mail.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/api")
public class HomeController {

    @Value("${student.name:false}")
    private String studentName;

    @Value("${student.age:0}")
    private int studentAge;

    @GetMapping("/hello")
    public String sayHello() {
        return "studentName";
    }

    @GetMapping("/infos")
    public String infos() {
        return studentName + studentAge;
    }
}
