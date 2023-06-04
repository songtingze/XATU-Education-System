package com.xatu.system.controller;

import com.xatu.common.domain.EnumResult;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.TitleEnum;
import com.xatu.system.domain.Student;
import com.xatu.system.domain.Teacher;
import com.xatu.system.domain.vo.TeacherVo;
import com.xatu.system.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Api(value = "教师信息管理Controller", tags = {"教师信息管理Controller"})
@RestController
@RequestMapping("/sys/tea")
public class TeacherController {

    @Resource
    TeacherService teacherService;

    @ApiOperation(value = "获取教师列表")
    @PostMapping("/getTeacherList")
    public PageResult<TeacherVo> getStudentList(@RequestBody TeacherVo searchInfo){
        PageResult<Teacher> pageResult = teacherService.getTeacherList(searchInfo);
        List<Teacher> teacherList = pageResult.getData();
        List<TeacherVo> teacherVos = new ArrayList<>();
        for(Teacher teacher:teacherList){
            TeacherVo teacherVo = new TeacherVo(teacher);
            teacherVos.add(teacherVo);
        }
        return PageResult.success(teacherVos,pageResult.getTotal(),pageResult.getCurrent());
    }

    @ApiOperation(value = "获取全部职称")
    @GetMapping("/getTitles")
    public Result<List<EnumResult>> getTitles(){
        return Result.success(TitleEnum.getAllTitles());
    }

    @ApiOperation(value = "修改教师信息")
    @PutMapping ("/update")
    public Result<Boolean> updateTeacher(@RequestBody Teacher teacher){
        return teacherService.update(teacher);
    }

    @ApiOperation(value = "添加教师信息")
    @PostMapping("/add")
    public Result<Boolean> addTeacher (@RequestBody Teacher teacher) throws ParseException {
        return teacherService.add(teacher) ;
    }

    @ApiOperation(value = "删除教师信息")
    @DeleteMapping("/delete")
    public Result<Boolean> deleteStudent(@RequestParam Integer id){
        return teacherService.delete(id);
    }

    @ApiOperation(value = "批量删除教师信息")
    @DeleteMapping("/batchDelete")
    public Result<Boolean> batchDeleteStudent(@RequestParam String sid){
        return teacherService.batchDelete(sid);
    }

    @ApiOperation(value = "导入教师信息")
    @PostMapping("/import")
    public boolean exImport(@RequestParam("file") MultipartFile file){
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            isSuccess = teacherService.batchImport(fileName,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(isSuccess);
        return isSuccess;
    }
}
