package com.mail.admin;

import com.mail.admin.entity.SysUser;
import com.mail.admin.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailAdminApplicationTests {

    @Autowired
    SysUserService sysUserService;
    @Test
    void contextLoads() {

    }
    @Test
    public void selectByIdTest() {
        SysUser sysUser = sysUserService.findById(1L);
        System.out.println(sysUser);
    }

}
