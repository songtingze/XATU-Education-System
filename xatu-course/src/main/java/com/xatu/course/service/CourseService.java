package com.xatu.course.service;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.course.domain.vo.SelectCourseVO;

import java.util.List;
import java.util.Map;

public interface CourseService {
    /**
     * 获取选课范围内的课程列表
     */
    PageResult<SelectCourseVO> listSelectAvailableCourse(Integer studentNumber, PageQuery pageQuery);

    /**
     * 选课
     */
    Result<Void> selectCourse(Integer studentNumber, String courseNum, Integer courseIndex);

    /**
     * 获取冲突课程
     */
    Result<Map<String, List<SelectCourseVO>>> listConflictingCourse(Integer studentNumber);

    /**
     * 退课
     */
    Result<Void> unselectCourse(Integer studentNumber, String courseNum, Integer courseIndex);

    /**
     * 获取已选课程
     */
    PageResult<SelectCourseVO> listSelectedCourse(Integer studentNumber);
}
