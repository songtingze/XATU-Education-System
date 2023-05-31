package com.xatu.course.controller;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.course.domain.vo.CourseScoreVO;
import com.xatu.course.service.CourseScoreService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/usual/import")
    public Result<Void> importUsualScore(MultipartFile file, String numberColumn, String usualColumn, String course, Integer courseIndex) {
        return courseScoreService.importUsualScore(file, numberColumn, usualColumn, course, courseIndex);
    }
}
