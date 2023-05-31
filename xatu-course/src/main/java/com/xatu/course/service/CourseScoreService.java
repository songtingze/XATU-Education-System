package com.xatu.course.service;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.course.domain.vo.CourseScoreVO;
import org.springframework.web.multipart.MultipartFile;

public interface CourseScoreService {
    /**
     * 获取学生所有课程成绩
     */
    PageResult<CourseScoreVO> getCourseScoreByStudentNumber(Integer studentNumber, String course, PageQuery pageQuery);

    /**
     * 导入平时成绩
     */
    Result<Void> importUsualScore(MultipartFile file, String numberColumn, String usualColumn, String course, Integer courseIndex);
}
