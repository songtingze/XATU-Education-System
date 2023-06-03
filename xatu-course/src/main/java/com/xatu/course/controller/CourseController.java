package com.xatu.course.controller;

import com.xatu.common.domain.EnumResult;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.HourPeriodEnum;
import com.xatu.course.domain.Student;
import com.xatu.course.domain.vo.ScheduleCeilVO;
import com.xatu.course.domain.vo.SelectCourseVO;
import com.xatu.course.service.CourseService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(value = "选课Controller", tags = {"选课Controller"})
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    /**
     * 获取选课范围内的课程列表
     */
    @ApiOperation(value = "获取可选课列表")
    @GetMapping("/available/list")
    public PageResult<SelectCourseVO> listSelectAvailableCourse(@ApiParam("学号") @RequestParam Integer studentNumber, PageQuery pageQuery) {
        return courseService.listSelectAvailableCourse(studentNumber, pageQuery);
    }

    /**
     * 获取已选课程
     */
    @ApiOperation(value = "获取已选课程")
    @GetMapping("/selected/list")
    public PageResult<SelectCourseVO> listSelectedCourse(@ApiParam("学号") @RequestParam Integer studentNumber) {
        return courseService.listSelectedCourse(studentNumber);
    }

    /**
     * 选课
     */
    @ApiOperation(value = "选课")
    @PostMapping("/select")
    public Result<Void> selectCourse(@ApiParam("学号") @RequestParam Integer studentNumber,
                                     @ApiParam("课程号") @RequestParam String courseNum,
                                     @ApiParam("课序号") @RequestParam Integer courseIndex) {
        return courseService.selectCourse(studentNumber, courseNum, courseIndex);
    }

    /**
     * 退课
     */
    @ApiOperation(value = "退课")
    @PostMapping("/unselect")
    public Result<Void> unselectCourse(@ApiParam("学号") @RequestParam Integer studentNumber,
                                       @ApiParam("课程号") @RequestParam String courseNum,
                                       @ApiParam("课序号") @RequestParam Integer courseIndex) {
        return courseService.unselectCourse(studentNumber, courseNum, courseIndex);
    }

    /**
     * 获取冲突课程
     */
    @ApiOperation(value = "获取冲突课程")
    @GetMapping("/conflicting/list")
    public Result<Map<String, List<SelectCourseVO>>> listConflictingCourse(@ApiParam("学号") @RequestParam Integer studentNumber) {
        return courseService.listConflictingCourse(studentNumber);
    }

    /**
     * 获取课程表
     */
    @ApiOperation(value = "获取课程表")
    @GetMapping("/schedule/get")
    public Result<List<Map<Integer, ScheduleCeilVO>>> getSchedule(@ApiParam("学号") @RequestParam Integer studentNumber) {
        return courseService.getSchedule(studentNumber);
    }

    /**
     * 获取选了该课程的学生名单
     */
    @ApiOperation(value = "获取选了该课程的学生名单")
    @GetMapping("/student/list")
    public PageResult<Student> getCourseStudentList(@ApiParam("课程号") @RequestParam String courseNum,
                                                    @ApiParam("课序号") @RequestParam Integer courseIndex) {
        return courseService.getStudentList(courseNum, courseIndex);
    }

    /**
     * 获取教师课程表
     */
    @ApiOperation(value = "获取教师课程表")
    @GetMapping("/schedule/teacher/get")
    public Result<List<Map<Integer, ScheduleCeilVO>>> getTeacherSchedule(@ApiParam("教师工号") @RequestParam Integer teacherNumber) {
        return courseService.getTeacherSchedule(teacherNumber);
    }

    @ApiOperation(value = "获取课程表时间对照表")
    @GetMapping("/enums/hour")
    public Result<List<EnumResult>> getHourEnums() {
        return Result.success(HourPeriodEnum.getAllHourPeriods());
    }

}
