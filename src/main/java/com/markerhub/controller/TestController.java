package com.markerhub.controller;

import com.markerhub.common.lang.Result;
import com.markerhub.service.SysMenuService;
import com.markerhub.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test1")
    public Result test(){
        return Result.succ(sysUserService.list());
    }

    @GetMapping("/test2")
    public Object test2(){
        return sysMenuService.list();
    }

    @GetMapping("/test/pass")
    public Result pass(){

        //加密后的密码
        String password = bCryptPasswordEncoder.encode("111111");

        //用户输入的密码是否匹配
        boolean matches = bCryptPasswordEncoder.matches("111111", password);

        System.out.println("匹配结果："+matches);
        return Result.succ(password);
    }
}
