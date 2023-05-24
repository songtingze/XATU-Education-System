package com.xatu.system.controller;

import com.xatu.common.domain.EnumResult;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.TitleEnum;
import com.xatu.system.domain.Student;
import com.xatu.system.domain.Teacher;
import com.xatu.system.domain.vo.TeacherVo;
import com.xatu.system.service.TeacherService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/tea")
public class TeacherController {

    @Resource
    TeacherService teacherService;

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

    @GetMapping("/getTitles")
    public Result<List<EnumResult>> getTitles(){
        return Result.success(TitleEnum.getAllTitles());
    }

    @PutMapping ("/update")
    public Result<Boolean> updateTeacher(@RequestBody Teacher teacher){
        return teacherService.update(teacher);
    }

    @PostMapping("/add")
    public Result<Boolean> addTeacher (@RequestBody Teacher teacher) throws ParseException {
        return teacherService.add(teacher) ;
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteStudent(@RequestParam Integer id){
        return teacherService.delete(id);
    }
    @DeleteMapping("/batchDelete")
    public Result<Boolean> batchDeleteStudent(@RequestParam String sid){
        return teacherService.batchDelete(sid);
    }
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
