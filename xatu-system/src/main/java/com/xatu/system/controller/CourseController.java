package com.xatu.system.controller;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.Course;
import com.xatu.system.domain.vo.CourseVo;
import com.xatu.system.domain.vo.SelectionValue;
import com.xatu.system.service.CourseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/course")
public class CourseController {
    @Resource
    CourseService courseService;

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

    @GetMapping("/getSelections")
    public Result<SelectionValue> getIsOnlyMajor(){
        return Result.success(courseService.getSelections());
    }

    @PutMapping ("/update")
    public Result<Boolean> updateCourse(@RequestBody Course course){
        return courseService.update(course);
    }

    @PostMapping("/add")
    public Result<Boolean> addCourse (@RequestBody Course course) throws ParseException {
        return courseService.add(course) ;
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteCourse(@RequestParam Integer id){
        return courseService.delete(id);
    }
    @DeleteMapping("/batchDelete")
    public Result<Boolean> batchDeleteCourse(@RequestParam String sid){
        return courseService.batchDelete(sid);
    }

}
