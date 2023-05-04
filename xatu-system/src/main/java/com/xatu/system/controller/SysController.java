package com.xatu.system.controller;

import com.xatu.system.domain.Sys;
import com.xatu.system.service.SysService;
import com.xatu.system.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys")
public class SysController {
    @Resource
    private SysService sysService;

    @PostMapping("/login")
    public Result<Sys> loginController(@RequestParam int number, @RequestParam String password){
        Sys sys = sysService.loginService(number,password);
        if(sys!=null){
            return Result.success(sys,"登录成功！");
        }else{
            return Result.error("123","账号或密码错误！");
        }
    }
}
