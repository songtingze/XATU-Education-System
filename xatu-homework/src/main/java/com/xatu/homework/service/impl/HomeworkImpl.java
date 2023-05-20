package com.xatu.homework.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.homework.domain.Course;
import com.xatu.homework.domain.Homework;
import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.TeacherVO;
import com.xatu.homework.mapper.CourseMapper;
import com.xatu.homework.mapper.HomeworkMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.xatu.homework.service.HomeworkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkImpl implements HomeworkService {
    @Resource
    private HomeworkMapper homeworkMapper;
    @Resource
    private CourseMapper courseMapper;
    @Override
    public PageResult<CourseVO> listStudentSelectedCourse(Integer studentNumber){
        List<CourseVO> listResult = courseMapper.selectCourseByStudentNumber(studentNumber);
        return PageResult.success(listResult);
    }
    @Override
    public PageResult<CourseVO> listStudentSelectedCourse(Integer studentNumber,Integer current,Integer pageSize){
        Page page = new Page(current, pageSize);
        IPage<CourseVO> listResult = courseMapper.selectCourseByStudentNumber(page,studentNumber);
        return PageResult.success(listResult);
    }
    @Override
    public PageResult<CourseVO> listTeacherCourse(Integer teacherNumber){
        List<CourseVO> listResult = courseMapper.selectCourseByTeacherNumber(teacherNumber);
        return PageResult.success(listResult);
    }

    @Override
    public PageResult<CourseVO> listTeacherCourse(Integer teacherNumber,Integer current,Integer pageSize){
        Page page = new Page(current, pageSize);
        IPage<CourseVO> listResult = courseMapper.selectCourseByTeacherNumber(page,teacherNumber);
        return PageResult.success(listResult);
    }

    public Result<Course> selectCourse(String courseNumber){
        LambdaQueryWrapper<Course> lqw = new LambdaQueryWrapper<Course>();
        lqw.eq(Course::getNumber,courseNumber);
        Course course = (Course) courseMapper.selectOne(lqw);
        return Result.success(course);
    }

    public Result<TeacherVO> selectTeacher(String courseNumber,Integer courseIndex){
        TeacherVO teacherVO = courseMapper.selectTeacherByCourseNumber(courseNumber,courseIndex);
        return Result.success(teacherVO);
    }

    public PageResult<Student> getStudentList(String courseNumber, Integer courseIndex,Integer current,Integer pageSize){
        Page page = new Page(current, pageSize);
        IPage<Student> listResult = courseMapper.getStudentList(page,courseNumber,courseIndex);
        return PageResult.success(listResult);
    }
//    @Override
//    public PageResult<Homework> listCourseHomework(String course, Integer current,Integer pageSize) {
//        LambdaQueryWrapper<Homework> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Homework::getCourse, course);
//        IPage page = new Page(current, pageSize);
//        IPage<Homework> pageResult = homeworkMapper.selectPage(page,wrapper);
//        return PageResult.success(pageResult);
//
//    }
}
