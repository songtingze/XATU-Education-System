package com.xatu.course.service;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.course.domain.vo.CourseScoreVO;

public interface CourseScoreService {
    /**
     * 获取学生所有课程成绩
     */
    PageResult<CourseScoreVO> getCourseScoreByStudentNumber(Integer studentNumber, String course, PageQuery pageQuery);
}
