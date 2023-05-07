package com.xatu.system.controller;

import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.Sys;
import com.xatu.system.service.SysService;
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
    public Result<Sys> loginController(@RequestParam Integer number, @RequestParam String password) {
        Sys sys = sysService.loginService(number, password);
        if (sys != null) {
            return Result.success(sys, "登录成功！");
        } else {
            return Result.error(CodeConstants.LOGIN_ERROR, "账号或密码错误！");
        }
    }
}
