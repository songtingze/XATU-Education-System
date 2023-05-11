package com.xatu.course.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.course.domain.SelectCourse;
import com.xatu.course.domain.SingleCourse;
import com.xatu.course.domain.vo.SelectCourseVO;
import com.xatu.course.domain.vo.SingleCourseVO;
import com.xatu.course.mapper.SelectCourseMapper;
import com.xatu.course.mapper.SingleCourseMapper;
import com.xatu.course.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private SelectCourseMapper selectCourseMapper;

    @Resource
    private SingleCourseMapper singleCourseMapper;

    @Override
    public PageResult<SelectCourseVO> listSelectAvailableCourse(Integer studentNumber, PageQuery pageQuery) {
        Page<SingleCourseVO> pageResult = singleCourseMapper.selectAvailableByStudentNumber(studentNumber, pageQuery.build());
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
        wrapper.eq(SingleCourse::getCourseNum, courseNum).eq(SingleCourse::getIndex, courseIndex);
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
        selectCourseMapper.insert(entity);
        return Result.success();
    }

    @Override
    public Result<Map<String, List<SelectCourseVO>>> listConflictingCourse(Integer studentNumber) {
        List<SingleCourseVO> listResult = singleCourseMapper.selectSelectedByStudentNumber(studentNumber);
        Map<String, List<SelectCourseVO>> conflictingMap = new HashMap<>();
        for (SingleCourseVO singleCourse : listResult) {
            String schedule = singleCourse.getSchedule();
            List<SelectCourseVO> conflictingList = conflictingMap.getOrDefault(schedule, new ArrayList<>());
            SelectCourseVO dst = new SelectCourseVO();
            BeanUtils.copyProperties(singleCourse, dst);
            dst.setSchedule(JSONUtil.parseObj(singleCourse.getSchedule()));
            conflictingList.add(dst);
            conflictingMap.put(schedule, conflictingList);
        }
        conflictingMap.keySet().removeIf(schedule -> conflictingMap.get(schedule).size() == 1);
        return Result.success(conflictingMap);
    }

    @Override
    public Result<Void> unselectCourse(Integer studentNumber, String courseNum, Integer courseIndex) {
        // 先修改课余量
        LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SingleCourse::getCourseNum, courseNum).eq(SingleCourse::getIndex, courseIndex);
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
