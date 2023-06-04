package com.xatu.user.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.user.domain.Student;
import com.xatu.user.domain.vo.StudentVo;
import com.xatu.user.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Wang Lei
 */
@Api(value = "学生用户Controller", tags = {"学生用户Controller"})
@RestController
@RequestMapping("/user/stu")
public class StudentController {
    @Resource
    StudentService studentService;

    @ApiOperation(value = "学生用户登录")
    @SaIgnore //忽略鉴权校验
    @PostMapping("/login")
    public Result<Student> loginController(@ApiParam("学号") @RequestParam String number, @ApiParam("密码") @RequestParam String password) {
        Student student = studentService.login(number, password);
        if (student != null) {
            StudentVo studentVo = new StudentVo(student);
            //用student表中的id登录
            StpUtil.login(studentVo.getId());
            studentVo.setUserToken(StpUtil.getTokenValue());
            studentVo.setSchoolValue(SchoolEnum.getByCode(student.getSchool()).getDesc());
            return Result.success(studentVo, "登录成功！");
        } else {
            System.out.println("账号密码错误");
            return Result.error(CodeConstants.LOGIN_ERROR, "账号或密码错误！");
        }
    }

    @ApiOperation(value = "学生注销")
    @PostMapping("/logout")
    public Result logoutController(@ApiParam("学生用户id") @RequestParam int id) {
        StpUtil.logout(id);
        System.out.println("当前是否处于登录状态：" + StpUtil.isLogin());
        //获取当前会话账号id, 如果未登录，则返回null
        System.out.println("当前会话账号id：" + StpUtil.getLoginIdDefaultNull());
        return Result.success();
    }

    @ApiOperation(value = "学生修改头像")
    @PostMapping("/changePhoto")
    public Result<Student> changePhotoController(@ApiParam("学生用户id") @RequestParam int id, @ApiParam("头像Url") @RequestParam String photoUrl) {
        Student stu = studentService.changePhoto(id, photoUrl);
        if (stu != null) {
            return Result.success(stu,"照片修改成功！");
        }
        else {
            return Result.error(CodeConstants.ERROR, "照片修改错误！");
        }
    }

    @ApiOperation(value = "学生修改密码")
    @PostMapping("/changePassword")
    public Result changePwdController(@ApiParam("学生用户id") @RequestParam int id, @ApiParam("旧密码") @RequestParam String oldPwd, @ApiParam("新密码") @RequestParam String newPwd) {
        boolean flag = studentService.changePassword(id, oldPwd, newPwd);
        if (flag) {
            return Result.success("密码修改成功！");
        }
        else{
            return Result.error(CodeConstants.ERROR, "密码修改失败！");
        }
    }

    @ApiOperation(value = "学生修改个人信息")
    @PostMapping("/update")
    public Result<Student> updateController(@ApiParam("学生新修改的信息") @RequestBody Student student){
        Student student1 = studentService.updateInfo(student);
        if (student1 != null) {
            return Result.success(student1);
        }
        else {
            return Result.error(CodeConstants.ERROR, "更新用户信息失败");
        }
    }
}
