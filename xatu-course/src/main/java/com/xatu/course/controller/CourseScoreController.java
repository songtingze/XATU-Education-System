package com.xatu.course.controller;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.course.domain.vo.CourseScoreVO;
import com.xatu.course.service.CourseScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api(value = "课程成绩Controller", tags = {"课程成绩Controller"})
@RestController
@RequestMapping("/course/score")
public class CourseScoreController {
    @Resource
    private CourseScoreService courseScoreService;

    @ApiOperation(value = "获取学生所有成绩列表")
    @GetMapping("/list")
    public PageResult<CourseScoreVO> listCourseScore(@ApiParam("学号") @RequestParam Integer studentNumber, @RequestParam(required = false) String course, PageQuery pageQuery) {
        return courseScoreService.getCourseScoreByStudentNumber(studentNumber, course, pageQuery);
    }

    @ApiOperation(value = "导入课堂学生的平时成绩")
    @PostMapping("/usual/import")
    public Result<Void> importUsualScore(@ApiParam("文件") MultipartFile file,
                                         @ApiParam("学号列") String numberColumn,
                                         @ApiParam("平时成绩列") String usualColumn,
                                         @ApiParam("课程号") String course,
                                         @ApiParam("课序号") Integer courseIndex) {
        return courseScoreService.importUsualScore(file, numberColumn, usualColumn, course, courseIndex);
    }
}
