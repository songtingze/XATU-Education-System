package com.xatu.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.domain.School;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.system.domain.Student;
import com.xatu.system.domain.vo.StudentVo;
import com.xatu.system.service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Lei
 */
@RestController
@RequestMapping("/sys/stu")
public class StudentController {
    @Resource
    StudentService studentService;

    @PostMapping("/getStudentList")
    public PageResult<StudentVo> getStudentList(@RequestBody StudentVo searchInfo){
        PageResult<Student> pageResult = studentService.getStudentList(searchInfo);
        List<Student> studentList = pageResult.getData();
        List<StudentVo> studentVos = new ArrayList<>();
        for(Student student:studentList){
            StudentVo studentVo = new StudentVo(student);
            studentVos.add(studentVo);
        }
        return PageResult.success(studentVos,pageResult.getTotal(),pageResult.getCurrent());

    }
    @PostMapping("/import")
    public boolean exImport(@RequestParam("file") MultipartFile file){
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            isSuccess = studentService.batchImport(fileName,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(isSuccess);
        return isSuccess;
    }
    @GetMapping("/getSchools")
    public Result<List<School>> getSchools(){
        return Result.success(SchoolEnum.getAllSchools());
    }
    @PutMapping ("/update")
    public Result<Boolean> updateStudent(@RequestBody Student student){
        return studentService.update(student);
    }
    @PostMapping("/add")
    public Result<Boolean> addStudent (@RequestBody Student student){
        return studentService.add(student);
    }
    @DeleteMapping("/delete")
    public Result<Boolean> deleteStudent(@RequestParam Integer id){
        return studentService.delete(id);
    }
    @DeleteMapping("/batchDelete")
    public Result<Boolean> batchDeleteStudent(@RequestParam String sid){
        return studentService.batchDelete(sid);
    }
}
