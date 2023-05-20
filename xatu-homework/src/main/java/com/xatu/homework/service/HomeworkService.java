package com.xatu.homework.service;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.homework.domain.Homework;
import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.Course;
import com.xatu.homework.domain.vo.TeacherVO;

public interface HomeworkService {

    /**
     * 获取学生已选课程
     */
    PageResult<CourseVO> listStudentSelectedCourse(Integer studentNumber);
    /**
     * 获取学生选课列表（分页）
     */
    PageResult<CourseVO> listStudentSelectedCourse(Integer studentNumber,Integer current,Integer pageSize);
    /**
     * 获取教师教授课程
     */
    PageResult<CourseVO> listTeacherCourse(Integer teacherNumber);
    /**
     * 获取教师教授课程（分页）
     */
    PageResult<CourseVO> listTeacherCourse(Integer teacherNumber,Integer current,Integer pageSize);
    /**
     * 获取课程信息
     */
    Result<Course> selectCourse(String courseNumber);
    /**
     * 获取课程教授老师信息
     */
    Result<TeacherVO> selectTeacher(String courseNumber,Integer courseIndex);
    /**
     * 获取选取课程的全部学生
     */
    PageResult<Student> getStudentList(String courseNumber, Integer courseIndex,Integer current,Integer pageSize);

//    /**
//     * 获取学生已选课程
//     */
//    PageResult<Homework> listCourseHomework(String course,Integer current,Integer pageSize);

}
