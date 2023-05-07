package com.xatu.course.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.course.domain.vo.SelectCourseVO;
import com.xatu.course.mapper.SelectCourseMapper;
import com.xatu.course.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private SelectCourseMapper selectCourseMapper;

    @Override
    public PageResult<SelectCourseVO> listSelectAvailableCourse(PageQuery pageQuery) {
        Page<SelectCourseVO> pageResult = new PageDTO<>();
        return PageResult.success(pageResult);
    }
}
