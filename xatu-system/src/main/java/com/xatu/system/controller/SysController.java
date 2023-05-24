package com.xatu.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.Sys;
import com.xatu.system.domain.vo.SysVo;
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

    @SaIgnore //忽略鉴权校验
    @PostMapping("/login")
    public Result<Sys> loginController(@RequestParam Integer number, @RequestParam String password) {
        Sys sys = sysService.loginService(number, password);
        if (sys != null) {
            SysVo sysVo = new SysVo(sys);
            StpUtil.login(sysVo.getId());
            sysVo.setUserToken(StpUtil.getTokenValue());

            return Result.success(sysVo, "登录成功！");
        } else {
            return Result.error(CodeConstants.LOGIN_ERROR, "账号或密码错误！");
        }
    }
    @PostMapping("/logout")
    public Result logoutController(@RequestParam int id) {
        StpUtil.checkLogin();
        StpUtil.logout(id);
        System.out.println("当前是否处于登录状态：" + StpUtil.isLogin());
        //获取当前会话账号id, 如果未登录，则返回null
        System.out.println("当前会话账号id：" + StpUtil.getLoginIdDefaultNull());
        return Result.success();
    }
}
