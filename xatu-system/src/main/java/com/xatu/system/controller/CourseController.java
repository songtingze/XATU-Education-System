package com.xatu.system.controller;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.Course;
import com.xatu.system.domain.vo.CourseVo;
import com.xatu.system.domain.vo.SelectionValue;
import com.xatu.system.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Api(value = "课程信息管理Controller", tags = {"课程信息管理Controller"})
@RestController
@RequestMapping("/sys/course")
public class CourseController {
    @Resource
    CourseService courseService;

    @ApiOperation(value = "获取课程列表")
    @PostMapping("/getCourseList")
    public PageResult<CourseVo> getStudentList(@RequestBody CourseVo searchInfo){
        PageResult<Course> pageResult = courseService.getCourseList(searchInfo);
        List<Course> courseList = pageResult.getData();
        List<CourseVo> courseVos = new ArrayList<>();
        for(Course course:courseList){
            CourseVo courseVo = new CourseVo(course);
            courseVos.add(courseVo);
        }
        return PageResult.success(courseVos,pageResult.getTotal(),pageResult.getCurrent());

    }

    @ApiOperation(value = "导入课程信息")
    @PostMapping("/import")
    public boolean exImport(@RequestParam("file") MultipartFile file){
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            isSuccess = courseService.batchImport(fileName,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(isSuccess);
        return isSuccess;
    }

    @ApiOperation(value = "获取所有下拉框信息")
    @GetMapping("/getSelections")
    public Result<SelectionValue> getSelections(){
        return Result.success(courseService.getSelections());
    }

    @ApiOperation(value = "修改课程信息")
    @PutMapping ("/update")
    public Result<Boolean> updateCourse(@RequestBody Course course){
        return courseService.update(course);
    }

    @ApiOperation(value = "添加课程信息")
    @PostMapping("/add")
    public Result<Boolean> addCourse (@RequestBody Course course) throws ParseException {
        return courseService.add(course) ;
    }

    @ApiOperation(value = "删除课程信息")
    @DeleteMapping("/delete")
    public Result<Boolean> deleteCourse(@RequestParam Integer id){
        return courseService.delete(id);
    }

    @ApiOperation(value = "批量删除课程信息")
    @DeleteMapping("/batchDelete")
    public Result<Boolean> batchDeleteCourse(@RequestParam String sid){
        return courseService.batchDelete(sid);
    }

}
