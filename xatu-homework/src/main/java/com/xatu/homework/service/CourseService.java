package com.xatu.homework.service;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.homework.domain.Course;
import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.StudentVO;
import com.xatu.homework.domain.vo.TeacherVO;

public interface CourseService {
    /**
     * 获取学生选课列表（分页）
     */
    PageResult<CourseVO> listStudentSelectedCourse(String studentNumber,String courseNum,String courseName,Integer current,Integer pageSize);

    /**
     * 获取教师教授课程（分页）
     */
    PageResult<CourseVO> listTeacherCourse(String teacherNumber,String courseNum,String courseName,Integer current,Integer pageSize);


    /**
     * 获取待完成作业课程
     */
    PageResult<CourseVO> listHomeworkCourse(String studentNum);
    /**
     * 获取课程信息
     */
    Result<CourseVO> selectCourse(String courseNumber,String courseIndex);
    /**
     * 获取课程教授老师信息
     */
    Result<TeacherVO> selectTeacher(String courseNumber, String courseIndex);
    /**
     * 获取选取课程的全部学生个数
     */
    int getCourseStudentNum(String courseNumber, String courseIndex);
    /**
     * 获取选取课程的全部学生
     */
    PageResult<StudentVO> getStudentList(String courseNumber, String courseIndex, Integer current, Integer pageSize);
    /**
     * 获取未批改作业课程列表
     */
    PageResult<CourseVO> listUncorrectHomeworkCourse(String teacherNum);

}
