package com.xatu.course.controller;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.course.domain.vo.SelectCourseVO;
import com.xatu.course.service.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    @GetMapping("/available/list")
    public PageResult<SelectCourseVO> listSelectAvailableCourse(PageQuery pageQuery) {
        Integer studentNumber = 18301077;
        return courseService.listSelectAvailableCourse(studentNumber, pageQuery);
    }

    @GetMapping("/selected/list")
    public PageResult<SelectCourseVO> listSelectedCourse() {
        Integer studentNumber = 18301077;
        return courseService.listSelectedCourse(studentNumber);
    }

    @PostMapping("/select")
    public Result<Void> selectCourse(@RequestParam String courseNum, @RequestParam Integer courseIndex) {
        Integer studentNumber = 18301077;
        return courseService.selectCourse(studentNumber, courseNum, courseIndex);
    }

    @GetMapping("/conflicting/list")
    public Result<Map<String, List<SelectCourseVO>>> listConflictingCourse() {
        Integer studentNumber = 18301077;
        return courseService.listConflictingCourse(studentNumber);
    }

}
