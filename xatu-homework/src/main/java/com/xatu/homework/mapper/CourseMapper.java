package com.xatu.homework.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.homework.domain.Course;
import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.StudentVO;
import com.xatu.homework.domain.vo.TeacherVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    IPage<CourseVO> selectCourseByStudentNumber(@Param("page") Page page,
                                                @Param("studentNum") String atudentNum,
                                                @Param("courseNum") String courseNum,
                                                @Param("courseName") String courseName);

    IPage<CourseVO> selectCourseByTeacherNumber(@Param("page") Page page,
                                                @Param("teacherNum") String teacherNum,
                                                @Param("courseNum") String courseNum,
                                                @Param("courseName") String courseName);


    CourseVO selectCourseByNumber(@Param("courseNum") String courseNum,
                                  @Param("courseIndex") String courseIndex);


    List<CourseVO> selectCourseByHomework(@Param("studentNum") String studentNum);

    List<CourseVO> selectCourseByUCHomework(@Param("teacherNum") String teacherNum);

    TeacherVO selectTeacherByTeacherNumber(@Param("teacherNum") String teacherNum);


    int getStudentNum(@Param("courseNum") String courseNum,
                      @Param("courseIndex") String courseIndex);

    IPage<StudentVO> getStudentList(@Param("page")Page page,
                                    @Param("courseNumber")String courseNumber,
                                    @Param("courseIndex")String courseIndex);

}
