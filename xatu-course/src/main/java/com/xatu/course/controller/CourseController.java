package com.xatu.course.controller;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.course.domain.vo.SelectCourseVO;
import com.xatu.course.service.CourseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    public PageResult<SelectCourseVO> listSelectAvailableCourse(PageQuery pageQuery) {
        return courseService.listSelectAvailableCourse(pageQuery);
    }

}
