package com.mail.admin.controller;

import com.mail.admin.dto.LoginDto;
import com.mail.admin.service.SysUserService;
import com.mail.admin.vo.LoginVo;
import com.mail.admin.vo.MenuDataVo;
import com.mail.common.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主接口
 */
@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    SysUserService sysUserService;

    /**
     * 登录
     *
     * @param loginDto loginDto
     * @return LoginVo
     */
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@Validated @RequestBody LoginDto loginDto) {
        return sysUserService.login(loginDto);
    }

//    @PostMapping(value = "/logout")
//    public Result<Object> logout(String username, String password) {
//        return
//    }

    @GetMapping(value = "/menuData")
    public Result<List<MenuDataVo>> menuData() {
        return sysUserService.menuData();
    }

}
