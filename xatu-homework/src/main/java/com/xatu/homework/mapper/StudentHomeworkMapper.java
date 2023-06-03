package com.xatu.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.homework.domain.Homework;
import com.xatu.homework.domain.StudentHomework;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.StudentHomeworkVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentHomeworkMapper extends BaseMapper<StudentHomework> {
    IPage<StudentHomeworkVO> selectstudentHomeworkList(@Param("page") Page page,
                                                       @Param("studentNum") String studentNumber);

    IPage<StudentHomeworkVO> selectTeacherHomeworkList(@Param("page") Page page,
                                                       @Param("homework") String homework);
}
