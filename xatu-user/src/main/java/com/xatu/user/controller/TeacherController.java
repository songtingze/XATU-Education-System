package com.xatu.user.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.Result;
import com.xatu.user.domain.Teacher;
import com.xatu.user.domain.vo.TeacherVo;
import com.xatu.user.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Wang Lei
 */
@RestController
@RequestMapping("/user/tea")
public class TeacherController {
    @Resource
    TeacherService teacherService;

    @SaIgnore //忽略鉴权校验
    @PostMapping("/login")
    public Result<Teacher> loginController(@RequestParam String number, @RequestParam String password) {
        Teacher teacher = teacherService.login(number, password);
        if (teacher != null) {
            TeacherVo teacherVo = new TeacherVo(teacher);
            //用teacher表中的id登录
            StpUtil.login(teacherVo.getId());
            teacherVo.setUserToken(StpUtil.getTokenValue());
            return Result.success(teacherVo, "登录成功！");
        } else {
            System.out.println("账号密码错误");
            return Result.error(CodeConstants.LOGIN_ERROR, "账号或密码错误！");
        }
    }

    @PostMapping("/logout")
    public Result logoutController(@RequestParam int id) {
        StpUtil.logout(id);
        System.out.println("当前是否处于登录状态：" + StpUtil.isLogin());
        //获取当前会话账号id, 如果未登录，则返回null
        System.out.println("当前会话账号id：" + StpUtil.getLoginIdDefaultNull());
        return Result.success();
    }

    @RequestMapping("/changePhoto")
    public Result<Teacher> changePhotoController(@RequestParam int id, @RequestParam String photoUrl) {
        Teacher tea = teacherService.changePhoto(id, photoUrl);
        if (tea != null) {
            return Result.success(tea,"照片修改成功！");
        }
        else {
            return Result.error(CodeConstants.ERROR, "照片修改错误！");
        }
    }

    @RequestMapping("/changePassword")
    public Result changePwdController(@RequestParam int id, @RequestParam String oldPwd, @RequestParam String newPwd) {
        boolean flag = teacherService.changePassword(id, oldPwd, newPwd);
        if (flag) {
            return Result.success("密码修改成功！");
        }
        else{
            return Result.error(CodeConstants.ERROR, "密码修改失败！");
        }
    }

}
