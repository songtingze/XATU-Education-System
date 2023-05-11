package com.xatu.course.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.course.domain.SingleCourse;
import com.xatu.course.domain.vo.SingleCourseVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SingleCourseMapper extends BaseMapper<SingleCourse> {
    Page<SingleCourseVO> selectAvailableByStudentNumber(Integer number, Page<SingleCourseVO> page);

    List<SingleCourseVO> selectSelectedByStudentNumber(Integer number);
}
