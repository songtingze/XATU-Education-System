package com.xatu.homework.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.homework.domain.Course;
import com.xatu.homework.domain.Homework;
import com.xatu.homework.domain.vo.CourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HomeworkMapper extends BaseMapper<Homework> {
    int getStudentNum(@Param("courseNum") String courseNum,
                      @Param("courseIndex") String courseIndex);

    List<Homework> getHomeworkNum(@Param("courseNum") String courseNumber,
                                  @Param("courseIndex") String courseIndex);
}
