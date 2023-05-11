package com.xatu.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.Result;
import com.xatu.user.domain.Student;
import com.xatu.user.domain.vo.StudentVo;
import com.xatu.user.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Wang Lei
 */
@RestController
@RequestMapping("/user/stu")
public class StudentController {
    @Resource
    StudentService studentService;

    @PostMapping("/login")
    public Result<Student> loginController(@RequestParam String number, @RequestParam String password) {
        Student student = studentService.login(number, password);
        if (student != null) {
            StudentVo studentVo = new StudentVo(student);
            System.out.println("登录成功");
            //用student表中的id登录
            StpUtil.login(studentVo.getId());
            studentVo.setUserToken(StpUtil.getTokenValue());
            return Result.success(studentVo, "登录成功！");
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


}
