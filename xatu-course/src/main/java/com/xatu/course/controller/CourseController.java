package com.xatu.course.controller;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.course.domain.Student;
import com.xatu.course.domain.vo.ScheduleCeilVO;
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

    /**
     * 获取选课范围内的课程列表
     */
    @GetMapping("/available/list")
    public PageResult<SelectCourseVO> listSelectAvailableCourse(@RequestParam Integer studentNumber, PageQuery pageQuery) {
//        Integer studentNumber = 18301077;
        return courseService.listSelectAvailableCourse(studentNumber, pageQuery);
    }

    /**
     * 获取已选课程
     */
    @GetMapping("/selected/list")
    public PageResult<SelectCourseVO> listSelectedCourse(@RequestParam Integer studentNumber) {
//        Integer studentNumber = 18301077;
        return courseService.listSelectedCourse(studentNumber);
    }

    /**
     * 选课
     */
    @PostMapping("/select")
    public Result<Void> selectCourse(@RequestParam Integer studentNumber, @RequestParam String courseNum, @RequestParam Integer courseIndex) {
//        Integer studentNumber = 18301077;
        return courseService.selectCourse(studentNumber, courseNum, courseIndex);
    }

    /**
     * 退课
     */
    @PostMapping("/unselect")
    public Result<Void> unselectCourse(@RequestParam Integer studentNumber, @RequestParam String courseNum, @RequestParam Integer courseIndex) {
//        Integer studentNumber = 18301077;
        return courseService.unselectCourse(studentNumber, courseNum, courseIndex);
    }

    /**
     * 获取冲突课程
     */
    @GetMapping("/conflicting/list")
    public Result<Map<String, List<SelectCourseVO>>> listConflictingCourse(@RequestParam Integer studentNumber) {
//        Integer studentNumber = 18301077;
        return courseService.listConflictingCourse(studentNumber);
    }

    /**
     * 获取课程表
     */
    @GetMapping("/schedule/get")
    public Result<List<Map<String, ScheduleCeilVO>>> getSchedule(@RequestParam Integer studentNumber) {
        return courseService.getSchedule(studentNumber);
    }

    /**
     * 获取选了该课程的学生名单
     */
    @GetMapping("/student/list")
    public PageResult<Student> getCourseStudentList(@RequestParam String courseNum, @RequestParam Integer courseIndex) {
        return courseService.getStudentList(courseNum, courseIndex);
    }

}
