package com.xatu.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xatu.course.domain.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    List<Student> listByCourse(@Param("courseNum") String courseNum, @Param("courseIndex") Integer courseIndex);
}
