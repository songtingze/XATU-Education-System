package com.xatu.course.service;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.course.domain.vo.SelectCourseVO;

public interface CourseService {
    /**
     * 获取选课范围内的课程列表
     */
    PageResult<SelectCourseVO> listSelectAvailableCourse(PageQuery pageQuery);
}
