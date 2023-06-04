package com.xatu.system.controller;

import com.xatu.common.domain.EnumResult;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.system.domain.Student;
import com.xatu.system.domain.vo.StudentVo;
import com.xatu.system.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Lei
 */
@Api(value = "学生信息管理Controller", tags = {"学生信息管理Controller"})
@RestController
@RequestMapping("/sys/stu")
public class StudentController {
    @Resource
    StudentService studentService;

    @ApiOperation(value = "获取学生列表")
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
    @ApiOperation(value = "导入学生信息")
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
    @ApiOperation(value = "获取所有学院信息")
    @GetMapping("/getSchools")
    public Result<List<EnumResult>> getSchools(){
        return Result.success(SchoolEnum.getAllSchools());
    }
    @ApiOperation(value = "修改学生信息")
    @PutMapping ("/update")
    public Result<Boolean> updateStudent(@RequestBody Student student){
        return studentService.update(student);
    }
    @ApiOperation(value = "添加学生信息")
    @PostMapping("/add")
    public Result<Boolean> addStudent (@RequestBody Student student) throws ParseException {
        return studentService.add(student) ;
    }
    @ApiOperation(value = "删除学生信息")
    @DeleteMapping("/delete")
    public Result<Boolean> deleteStudent(@RequestParam Integer id){
        return studentService.delete(id);
    }
    @ApiOperation(value = "批量删除学生信息")
    @DeleteMapping("/batchDelete")
    public Result<Boolean> batchDeleteStudent(@RequestParam String sid){
        return studentService.batchDelete(sid);
    }
}
