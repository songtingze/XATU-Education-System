package com.xatu.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.homework.domain.Course;
import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.TeacherVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    List<CourseVO> selectCourseByStudentNumber(@Param("number") Integer number);

    IPage<CourseVO> selectCourseByStudentNumber(@Param("page") Page page,
                                                @Param("number") Integer number);

    List<CourseVO> selectCourseByTeacherNumber(@Param("number") Integer number);

    IPage<CourseVO> selectCourseByTeacherNumber(@Param("page") Page page,
                                                @Param("number") Integer number);

    TeacherVO selectTeacherByCourseNumber(@Param("number") String number,
                                          @Param("index") Integer index);

    IPage<Student> getStudentList(@Param("page")Page page,
                                  @Param("number")String courseNumber,
                                  @Param("index")Integer courseIndex);
}
