package com.xatu.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.enums.CoursePeriodEnum;
import com.xatu.common.utils.CourseUtil;
import com.xatu.course.domain.Student;
import com.xatu.course.domain.vo.CourseScoreVO;
import com.xatu.course.mapper.SingleCourseMapper;
import com.xatu.course.mapper.StudentMapper;
import com.xatu.course.service.CourseScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CourseScoreServiceImpl implements CourseScoreService {
    @Resource
    private SingleCourseMapper singleCourseMapper;
    @Resource
    private StudentMapper studentMapper;

    @Override
    public PageResult<CourseScoreVO> getCourseScoreByStudentNumber(Integer studentNumber, String course, PageQuery pageQuery) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getNumber, studentNumber);
        Student student = studentMapper.selectOne(wrapper);
        Date enrollmentTime = student.getEnrollmentTime();

        Page<CourseScoreVO> pageResult = singleCourseMapper.selectCourseScoreByStudentNumber(studentNumber, course, pageQuery.build());
        List<CourseScoreVO> data = pageResult.getRecords();
        for (CourseScoreVO courseScore : data) {
            CoursePeriodEnum period = CoursePeriodEnum.getByCode(Integer.parseInt(courseScore.getPeriod()));
            courseScore.setPeriod(period.getDesc());
            int grade = CourseUtil.getGradeYear(enrollmentTime, courseScore.getTermTime());
            String term = "" + grade + "年级" + CourseUtil.getCoursePeriodByDate(courseScore.getTermTime()).getDesc();
            courseScore.setTerm(term);
        }
        return PageResult.success(pageResult);
    }
}
