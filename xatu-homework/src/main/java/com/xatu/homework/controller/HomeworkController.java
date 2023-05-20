package com.xatu.homework.controller;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.homework.domain.Course;
import com.xatu.homework.domain.Homework;
import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.TeacherVO;
import org.springframework.web.bind.annotation.*;
import com.xatu.homework.service.HomeworkService;

import javax.annotation.Resource;

@RestController
@RequestMapping("")
public class HomeworkController {
    @Resource
    private HomeworkService homeworkService;

    /**
     * 获取学生选择课程
     */
    @GetMapping("/course/stu/list")
    public PageResult<CourseVO> getStudentCourseList(@RequestParam Integer studentNumber) {
        return homeworkService.listStudentSelectedCourse(studentNumber);
    }

    /**
     * 获取学生选课列表（分页）
     */
    @GetMapping("/course/stu/page")
    public PageResult<CourseVO> getStudentCourseList(@RequestParam Integer studentNumber,
                                                     @RequestParam(defaultValue = "1")Integer current,
                                                     @RequestParam(defaultValue = "10")Integer pageSize) {
        return homeworkService.listStudentSelectedCourse(studentNumber,current,pageSize);
    }

    /**
     * 获取教师教授课程
     */
    @GetMapping("/course/tec/list")
    public PageResult<CourseVO> getTeacherCourseList(@RequestParam Integer teacherNumber) {
        return homeworkService.listTeacherCourse(teacherNumber);
    }

    /**
     * 获取教师教授课程（分页）
     */
    @GetMapping("/course/tec/page")
    public PageResult<CourseVO> getTeacherCourseList(@RequestParam Integer teacherNumber,
                                                     @RequestParam(defaultValue = "1")Integer current,
                                                     @RequestParam(defaultValue = "10")Integer pageSize) {
        return homeworkService.listTeacherCourse(teacherNumber,current,pageSize);
    }

    /**
     * 获取课程信息
     */
    @GetMapping("/course/info")
    public Result<Course> getCourseByCourseNumber(@RequestParam String courseNumber) {
        return homeworkService.selectCourse(courseNumber);
    }


    /**
     * 获取课程教授老师信息
     */
    @GetMapping("/course/tec/info")
    public Result<TeacherVO> getTeacherByCourse(@RequestParam String courseNumber,
                                                @RequestParam Integer courseIndex) {
        return homeworkService.selectTeacher(courseNumber,courseIndex);
    }


    /**
     * 获取选取课程的全部学生
     */
    @GetMapping("/course/tec/studentlist")
    public PageResult<Student> getCourseStudentList(@RequestParam String courseNumber,
                                                    @RequestParam Integer courseIndex,
                                                    @RequestParam(defaultValue = "1")Integer current,
                                                    @RequestParam(defaultValue = "10")Integer pageSize
                                                    ) {
        return homeworkService.getStudentList(courseNumber,courseIndex,current,pageSize);
    }

    /**
     * 添加课程作业
     */
//    @PostMapping("/homework/tec/insert")
//    public PageResult<Homework> insertHomework() {
//
//    }

    /**
     * 修改课程作业发布
     */
//    @PostMapping("/homework/tec/update")
//    public PageResult<Homework> updateHomework() {
//
//    }

    /**
     * 修改课程作业分数
     */
//    @PostMapping("/homework/tec/score")
//    public PageResult<Homework> updateHomeworkScore() {
//
//    }

    /**
     * 删除课程作业
     */
//    @PostMapping("/homework/tec/delet")
//    public PageResult<Homework> deletHomework() {
//
//    }

    /**
     * 获取课程作业列表（分页）
     */
//    @PostMapping("/homework/list")
//    public PageResult<Homework> listHomework(@RequestParam String course,
//                                             @RequestParam(defaultValue = "1")Integer current,
//                                             @RequestParam(defaultValue = "10")Integer pageSize) {
//        return homeworkService.listCourseHomework(course,current,pageSize);
//    }

    /**
     * 提交作业
     */
//    @PostMapping("/homework/stu/submit")
//    public PageResult<Homework> submitHomework() {
//    }

    /**
     * 获取学生未完成作业课程列表
     */
//    @PostMapping("/homework/incomplete")
//    public PageResult<Homework> getStudentHomeworkCourseList() {
//
//    }


}
