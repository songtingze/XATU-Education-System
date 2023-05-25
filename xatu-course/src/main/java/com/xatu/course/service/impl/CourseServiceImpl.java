package com.xatu.course.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.CourseAssessmentEnum;
import com.xatu.common.enums.CoursePeriodEnum;
import com.xatu.common.utils.CourseUtil;
import com.xatu.course.domain.SelectCourse;
import com.xatu.course.domain.SingleCourse;
import com.xatu.course.domain.Student;
import com.xatu.course.domain.vo.ScheduleCeilVO;
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
        int grade = CourseUtil.getGradeYear(enrollmentTime, now);
        CoursePeriodEnum period = CourseUtil.getCoursePeriodByDate(now);
//        grade = 2;

        Page<SingleCourseVO> pageResult = singleCourseMapper.selectAvailableByStudentNumber(studentNumber, grade, period.getCode(), pageQuery.build());
        Page<SelectCourseVO> result = Page.of(pageResult.getCurrent(), pageResult.getSize());
        List<SelectCourseVO> selectCourseVOList = pageResult.getRecords().stream().map(src -> {
            SelectCourseVO dst = new SelectCourseVO();
            BeanUtils.copyProperties(src, dst);
            dst.setSchedule(JSONUtil.parseObj(src.getSchedule()));
            dst.setAssessment(CourseAssessmentEnum.getByCode(src.getAssessment()).getDesc());
            dst.setPeriod(CoursePeriodEnum.getByCode(src.getPeriod()).getDesc());
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
            dst.setAssessment(CourseAssessmentEnum.getByCode(singleCourse.getAssessment()).getDesc());
            dst.setPeriod(CoursePeriodEnum.getByCode(singleCourse.getPeriod()).getDesc());
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
            dst.setAssessment(CourseAssessmentEnum.getByCode(src.getAssessment()).getDesc());
            dst.setPeriod(CoursePeriodEnum.getByCode(src.getPeriod()).getDesc());
            return dst;
        }).collect(Collectors.toList());
        return PageResult.success(result);
    }

    @Override
    public Result<List<Map<String, ScheduleCeilVO>>> getSchedule(Integer studentNumber) {
        // TODO 冲突课程只会显示一个
        List<Map<String, ScheduleCeilVO>> scheduleTable = new ArrayList<>();
        // 每天划分为7个时间段
        Map<String, Integer> timeToIndex = new HashMap<>();
        timeToIndex.put("1", 0);
        timeToIndex.put("2", 1);
        timeToIndex.put("3", 2);
        timeToIndex.put("4", 3);
        timeToIndex.put("5", 4);
        timeToIndex.put("6", 5);
        timeToIndex.put("7", 6);

        for (int i = 0; i < 7; i++) {
            scheduleTable.add(new HashMap<>());
        }
        List<SingleCourseVO> listResult = singleCourseMapper.selectSelectedByStudentNumber(studentNumber);
        // 遍历每一节课，放到对应位置
        for (SingleCourseVO course : listResult) {
            JSONObject schedule = JSONUtil.parseObj(course.getSchedule());
            String[] time = schedule.getStr("time").split(",");
            String dayOfWeek = time[0], duration = time[1];

            ScheduleCeilVO scheduleCeil = new ScheduleCeilVO();
            BeanUtils.copyProperties(course, scheduleCeil);
            scheduleCeil.setLocation(schedule.getStr("location"));
            if (scheduleTable.get(timeToIndex.get(duration)).containsKey(dayOfWeek)) {
                scheduleTable.get(timeToIndex.get(duration)).get(dayOfWeek).setIsConflicting(true);
            } else {
                scheduleTable.get(timeToIndex.get(duration)).put(dayOfWeek, scheduleCeil);
            }

        }
        return Result.success(scheduleTable);
    }

    @Override
    public PageResult<Student> getStudentList(String courseNum, Integer courseIndex) {
        List<Student> listResult = studentMapper.listByCourse(courseNum, courseIndex);
        return PageResult.success(listResult);
    }
}
