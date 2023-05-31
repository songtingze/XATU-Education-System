package com.xatu.course.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.CoursePeriodEnum;
import com.xatu.common.utils.CourseUtil;
import com.xatu.course.domain.SelectCourse;
import com.xatu.course.domain.Student;
import com.xatu.course.domain.vo.CourseScoreVO;
import com.xatu.course.mapper.SelectCourseMapper;
import com.xatu.course.mapper.SingleCourseMapper;
import com.xatu.course.mapper.StudentMapper;
import com.xatu.course.service.CourseScoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CourseScoreServiceImpl implements CourseScoreService {
    @Resource
    private SingleCourseMapper singleCourseMapper;
    @Resource
    private SelectCourseMapper selectCourseMapper;
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

    @Override
    @Transactional
    public Result<Void> importUsualScore(MultipartFile file, String numberColumn, String usualColumn, String course, Integer courseIndex) {
        try {
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            List<Map<String,Object>> readAll = reader.readAll();

            for (Map<String, Object> row : readAll) {
                int studentNumber = 0;
                int score = 0;
                Object key = row.get(numberColumn);
                Object value = row.get(usualColumn);
                if (key instanceof Long) {
                    studentNumber = (int)((long) key);
                } else if (key instanceof String) {
                    studentNumber = Integer.parseInt((String) key);
                }
                if (value instanceof Long) {
                    score = (int)((long) value);
                } else if (value instanceof String) {
                    score = Integer.parseInt((String) value);
                }

                SelectCourse entity = new SelectCourse();
                entity.setStudent(studentNumber);
                entity.setUsual(score);
                entity.setCourse(course);
                entity.setCourseIndex(courseIndex);
                selectCourseMapper.updateScoreByStudentNumber(entity);
            }
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error(CodeConstants.ERROR, "导入失败");
    }
}
