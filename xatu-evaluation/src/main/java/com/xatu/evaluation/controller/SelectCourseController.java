package com.xatu.evaluation.controller;

import com.xatu.evaluation.entity.CourseList;
import com.xatu.evaluation.service.SelectCourseService;
import com.xatu.evaluation.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/select/course")
public class SelectCourseController {
    @Resource
    private SelectCourseService selectCourseService;

    /**
     * 学生给课程打分接口
     * @param student
     * @param score
     * @param course
     * @return
     */
    @PostMapping("/studentScoreTeacher")
    public Result studentScoreTeacher(@RequestParam int student, @RequestParam int score, @RequestParam String course){
        String result;
        try {
            result = selectCourseService.studentScoreTeacher(student, score, course);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("123","打分异常！");
        }
        return Result.success(result);
    }

    @PostMapping("/studentGetUnscoreCourse")
    public Result studentGetUnscoreCourse(@RequestParam int student){
        List<CourseList> courseLists = null;
        try {
            courseLists = selectCourseService.studentGetUnscoreCourse(student);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("123","查询异常！");
        }
        return Result.success(courseLists,"查询成功");
    }

    @PostMapping("/studentGetScoreCourse")
    public Result studentGetScoreCourse(@RequestParam int student){
        List<CourseList> courseLists = null;
        try {
            courseLists = selectCourseService.studentGetScoreCourse(student);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("123","查询异常！");
        }
        return Result.success(courseLists,"查询成功");
    }

}
