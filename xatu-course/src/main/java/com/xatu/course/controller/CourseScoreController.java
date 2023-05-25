package com.xatu.course.controller;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.course.domain.vo.CourseScoreVO;
import com.xatu.course.service.CourseScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/course/score")
public class CourseScoreController {
    @Resource
    private CourseScoreService courseScoreService;

    @GetMapping("/list")
    public PageResult<CourseScoreVO> listCourseScore(@RequestParam Integer studentNumber, @RequestParam(required = false) String course, PageQuery pageQuery) {
        return courseScoreService.getCourseScoreByStudentNumber(studentNumber, course, pageQuery);
    }
}
