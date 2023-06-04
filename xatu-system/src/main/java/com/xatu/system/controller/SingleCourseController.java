package com.xatu.system.controller;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.system.domain.Course;
import com.xatu.system.domain.SingleCourse;
import com.xatu.system.domain.Teacher;
import com.xatu.system.domain.vo.CourseVo;
import com.xatu.system.domain.vo.SelectionValue;
import com.xatu.system.domain.vo.SingleCourseVo;
import com.xatu.system.service.CourseService;
import com.xatu.system.service.SingleCourseService;
import com.xatu.system.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Api(value = "课堂信息管理Controller", tags = {"课堂信息管理Controller"})
@RestController
@RequestMapping("/sys/singleCourse")
public class SingleCourseController {
    @Resource
    private SingleCourseService singleCourseService;
    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;

    @ApiOperation(value = "获取课堂列表")
    @PostMapping("/getSingleCourseList")
    public PageResult<SingleCourseVo> getStudentList(@RequestBody SingleCourseVo searchInfo){
        PageResult<SingleCourse> pageResult = singleCourseService.getSingleCourseList(searchInfo);
        List<SingleCourse> singleCourseList = pageResult.getData();
        List<SingleCourseVo> singleCourseVos = new ArrayList<>();
        for(SingleCourse singleCourse:singleCourseList){
            SingleCourseVo singleCourseVo = new SingleCourseVo(singleCourse);
            singleCourseVo.setCourseName(courseService.getCourseByNumber(singleCourse.getCourseNum()).getName());
            singleCourseVo.setTeacherName(teacherService.getTeacherByNumber(singleCourse.getTeacher()).getName());
            singleCourseVo.setSchool(courseService.getCourseByNumber(singleCourse.getCourseNum()).getSchool());
            singleCourseVo.setSchoolValue(SchoolEnum.getByCode(singleCourseVo.getSchool()).getDesc());
            singleCourseVos.add(singleCourseVo);
        }
        return PageResult.success(singleCourseVos,pageResult.getTotal(),pageResult.getCurrent());

    }

    @ApiOperation(value = "导入课堂信息")
    @PostMapping("/import")
    public boolean exImport(@RequestParam("file") MultipartFile file){
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            isSuccess = singleCourseService.batchImport(fileName,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(isSuccess);
        return isSuccess;
    }

    @ApiOperation(value = "修改课堂信息")
    @PutMapping ("/update")
    public Result<Boolean> updateSingleCourse(@RequestBody SingleCourse singleCourse){
        return singleCourseService.update(singleCourse);
    }

    @ApiOperation(value = "添加课堂信息")
    @PostMapping("/add")
    public Result<Boolean> addSingleCourse (@RequestBody SingleCourse singleCourse) throws ParseException {
        return singleCourseService.add(singleCourse) ;
    }

    @ApiOperation(value = "删除课堂信息")
    @DeleteMapping("/delete")
    public Result<Boolean> deleteSingleCourse(@RequestParam Integer id){
        return singleCourseService.delete(id);
    }

    @ApiOperation(value = "批量删除课堂信息")
    @DeleteMapping("/batchDelete")
    public Result<Boolean> batchDeleteSingleCourse(@RequestParam String sid){
        return singleCourseService.batchDelete(sid);
    }

    @ApiOperation(value = "获取所有课程")
    @GetMapping("/getAllCourses")
    public Result<List<Course>> getAllCourses(){
        return singleCourseService.getAllCourse();
    }

    @ApiOperation(value = "获取学院所有教师")
    @GetMapping("/getAllTeachers")
    public Result<List<Teacher>> getAllTeachers(@RequestParam String courseNum){
        return singleCourseService.getAllTeacherBySchool(courseNum);
    }

}
