package com.xatu.user.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.common.enums.TitleEnum;
import com.xatu.user.domain.Student;
import com.xatu.user.domain.Teacher;
import com.xatu.user.domain.vo.TeacherVo;
import com.xatu.user.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Wang Lei
 */
@Api(value = "老师用户Controller", tags = {"老师用户Controller"})
@RestController
@RequestMapping("/user/tea")
public class TeacherController {
    @Resource
    TeacherService teacherService;

    @ApiOperation(value = "教师登录")
    @SaIgnore //忽略鉴权校验
    @PostMapping("/login")
    public Result<Teacher> loginController(@ApiParam("教师工号") @RequestParam String number, @ApiParam("密码") @RequestParam String password) {
        Teacher teacher = teacherService.login(number, password);
        if (teacher != null) {
            TeacherVo teacherVo = new TeacherVo(teacher);
            //用teacher表中的id登录
            StpUtil.login(teacherVo.getId());
            teacherVo.setUserToken(StpUtil.getTokenValue());
            teacherVo.setSchoolValue(SchoolEnum.getByCode(teacher.getSchool()).getDesc());
            teacherVo.setTitleValue(TitleEnum.getByCode(teacher.getTitle()).getDesc());
            return Result.success(teacherVo, "登录成功！");
        } else {
            System.out.println("账号密码错误");
            return Result.error(CodeConstants.LOGIN_ERROR, "账号或密码错误！");
        }
    }

    @ApiOperation(value = "教师注销")
    @PostMapping("/logout")
    public Result logoutController(@ApiParam("教师用户id") @RequestParam int id) {
        StpUtil.logout(id);
        System.out.println("当前是否处于登录状态：" + StpUtil.isLogin());
        //获取当前会话账号id, 如果未登录，则返回null
        System.out.println("当前会话账号id：" + StpUtil.getLoginIdDefaultNull());
        return Result.success();
    }

    @ApiOperation(value = "教师修改头像")
    @PostMapping("/changePhoto")
    public Result<Teacher> changePhotoController(@ApiParam("教师用户id") @RequestParam int id, @ApiParam("教师头像Url") @RequestParam String photoUrl) {
        Teacher tea = teacherService.changePhoto(id, photoUrl);
        if (tea != null) {
            return Result.success(tea,"照片修改成功！");
        }
        else {
            return Result.error(CodeConstants.ERROR, "照片修改错误！");
        }
    }

    @ApiOperation(value = "教师修改密码")
    @PostMapping("/changePassword")
    public Result changePwdController(@ApiParam("教师用户id") @RequestParam int id, @ApiParam("旧密码") @RequestParam String oldPwd, @ApiParam("新密码") @RequestParam String newPwd) {
        boolean flag = teacherService.changePassword(id, oldPwd, newPwd);
        if (flag) {
            return Result.success("密码修改成功！");
        }
        else{
            return Result.error(CodeConstants.ERROR, "密码修改失败！");
        }
    }

    @ApiOperation(value = "教师修改个人信息")
    @PostMapping("/update")
    public Result<Teacher> updateController(@ApiParam("教师新修改的信息") @RequestBody Teacher teacher){
        Teacher teacher1 = teacherService.updateInfo(teacher);
        if (teacher1 != null) {
            return Result.success(teacher1);
        }
        else {
            return Result.error(CodeConstants.ERROR, "更新用户信息失败");
        }
    }

}
