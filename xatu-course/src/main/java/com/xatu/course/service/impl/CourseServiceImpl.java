package com.xatu.course.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.course.domain.SelectCourse;
import com.xatu.course.domain.SingleCourse;
import com.xatu.course.domain.Student;
import com.xatu.course.domain.vo.SelectCourseVO;
import com.xatu.course.domain.vo.SingleCourseVO;
import com.xatu.course.mapper.SelectCourseMapper;
import com.xatu.course.mapper.SingleCourseMapper;
import com.xatu.course.mapper.StudentMapper;
import com.xatu.course.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private SelectCourseMapper selectCourseMapper;

    @Resource
    private SingleCourseMapper singleCourseMapper;

    @Resource
    private StudentMapper studentMapper;

    @Override
    public PageResult<SelectCourseVO> listSelectAvailableCourse(Integer studentNumber, PageQuery pageQuery) {
        // 根据当前日期与入学日期的年份差，得到学生当前年级
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getNumber, studentNumber);
        Student student = studentMapper.selectOne(wrapper);
        Date enrollmentTime = student.getEnrollmentTime();
        Date now = new Date();
        int grade = (int) DateUtil.betweenYear(enrollmentTime, now, false) + 1;
//        grade = 2;

        Page<SingleCourseVO> pageResult = singleCourseMapper.selectAvailableByStudentNumber(studentNumber, grade, pageQuery.build());
        Page<SelectCourseVO> result = Page.of(pageResult.getCurrent(), pageResult.getSize());
        List<SelectCourseVO> selectCourseVOList = pageResult.getRecords().stream().map(src -> {
            SelectCourseVO dst = new SelectCourseVO();
            BeanUtils.copyProperties(src, dst);
            dst.setSchedule(JSONUtil.parseObj(src.getSchedule()));
            return dst;
        }).collect(Collectors.toList());
        result.setRecords(selectCourseVOList);
        return PageResult.success(result);
    }

    @Override
    @Transactional
    public Result<Void> selectCourse(Integer studentNumber, String courseNum, Integer courseIndex) {
        // 先修改课余量
        LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SingleCourse::getCourseNum, courseNum).eq(SingleCourse::getCourseIndex, courseIndex);
        SingleCourse targetCourse = singleCourseMapper.selectOne(wrapper);
        if (targetCourse.getRemain() > 0) {
            targetCourse.setRemain(targetCourse.getRemain() - 1);
        } else {
            return Result.error(CodeConstants.ERROR, "课堂非法");
        }
        singleCourseMapper.updateById(targetCourse);
        // 再选中课
        SelectCourse entity = new SelectCourse();
        entity.setStudent(studentNumber);
        entity.setCourse(courseNum);
        entity.setCourseIndex(courseIndex);
        Date now = new Date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        selectCourseMapper.insert(entity);
        return Result.success();
    }

    @Override
    public Result<Map<String, List<SelectCourseVO>>> listConflictingCourse(Integer studentNumber) {
        List<SingleCourseVO> listResult = singleCourseMapper.selectSelectedByStudentNumber(studentNumber);
        Map<String, List<SelectCourseVO>> conflictingMap = new HashMap<>();
        for (SingleCourseVO singleCourse : listResult) {
            // 遍历所选课程，以课程时间安排中的时间为key建立map，
            String schedule = singleCourse.getSchedule();
            String time = JSONUtil.parseObj(schedule).getStr("time");
            List<SelectCourseVO> conflictingList = conflictingMap.getOrDefault(time, new ArrayList<>());
            SelectCourseVO dst = new SelectCourseVO();
            BeanUtils.copyProperties(singleCourse, dst);
            dst.setSchedule(JSONUtil.parseObj(singleCourse.getSchedule()));
            conflictingList.add(dst);
            conflictingMap.put(time, conflictingList);
        }
        // 一个时间下有多个课程的保留，即为冲突课程
        conflictingMap.keySet().removeIf(schedule -> conflictingMap.get(schedule).size() == 1);
        return Result.success(conflictingMap);
    }

    @Override
    public Result<Void> unselectCourse(Integer studentNumber, String courseNum, Integer courseIndex) {
        // 先修改课余量
        LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SingleCourse::getCourseNum, courseNum).eq(SingleCourse::getCourseIndex, courseIndex);
        SingleCourse targetCourse = singleCourseMapper.selectOne(wrapper);
        if (targetCourse.getRemain() < targetCourse.getCapacity()) {
            targetCourse.setRemain(targetCourse.getRemain() + 1);
        } else {
            return Result.error(CodeConstants.ERROR, "课堂非法");
        }
        singleCourseMapper.updateById(targetCourse);
        // 再退课
        LambdaQueryWrapper<SelectCourse> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(SelectCourse::getStudent, studentNumber)
                .eq(SelectCourse::getCourse, courseNum)
                .eq(SelectCourse::getCourseIndex, courseIndex);
        selectCourseMapper.delete(wrapper1);
        return Result.success();
    }

    @Override
    public PageResult<SelectCourseVO> listSelectedCourse(Integer studentNumber) {
        List<SingleCourseVO> listResult = singleCourseMapper.selectSelectedByStudentNumber(studentNumber);
        List<SelectCourseVO> result = listResult.stream().map(src -> {
            SelectCourseVO dst = new SelectCourseVO();
            BeanUtils.copyProperties(src, dst);
            dst.setSchedule(JSONUtil.parseObj(src.getSchedule()));
            return dst;
        }).collect(Collectors.toList());
        return PageResult.success(result);
    }
}
