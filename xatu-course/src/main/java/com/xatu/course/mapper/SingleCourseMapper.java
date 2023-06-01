package com.xatu.course.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.course.domain.SingleCourse;
import com.xatu.course.domain.vo.CourseScoreVO;
import com.xatu.course.domain.vo.SingleCourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SingleCourseMapper extends BaseMapper<SingleCourse> {
    Page<SingleCourseVO> selectAvailableByStudentNumber(@Param("number") Integer number, @Param("grade") Integer grade, @Param("period") Integer period, @Param("term") String term, Page<SingleCourseVO> page);

    List<SingleCourseVO> selectSelectedByStudentNumber(@Param("number") Integer number, @Param("period") Integer period, @Param("term") String term);

    Page<CourseScoreVO> selectCourseScoreByStudentNumber(@Param("number") Integer number, @Param("course") String course, Page<SingleCourseVO> page);

    List<SingleCourseVO> selectCourseByTeacherNumber(@Param("number") Integer teacherNumber);
}
