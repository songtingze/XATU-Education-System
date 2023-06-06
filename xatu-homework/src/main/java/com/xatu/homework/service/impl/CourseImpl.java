package com.xatu.homework.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.CourseAssessmentEnum;
import com.xatu.common.enums.CoursePeriodEnum;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.common.enums.TitleEnum;
import com.xatu.homework.domain.Attachment;
import com.xatu.homework.domain.Course;
import com.xatu.homework.domain.ScheduleTask;
import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.StudentVO;
import com.xatu.homework.domain.vo.TeacherVO;
import com.xatu.homework.mapper.AttachmentMapper;
import com.xatu.homework.mapper.CourseMapper;
import com.xatu.homework.mapper.ScheduleTaskMapper;
import com.xatu.homework.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseImpl implements CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private ScheduleTaskMapper scheduleTaskMapper;
    @Resource
    private AttachmentMapper attachmentMapper;
    //获取配置文件中的配置 为属性动态赋值 注解@Value
    @Override
    public PageResult<CourseVO> listStudentSelectedCourse(String studentNumber,String courseNum,String courseName,Integer current,Integer pageSize){
        Page page = new Page(current, pageSize);
        LambdaQueryWrapper<ScheduleTask> lqw = new LambdaQueryWrapper<ScheduleTask>();
        lqw.eq(ScheduleTask::getStatus,1);
        ScheduleTask scheduleTask = scheduleTaskMapper.selectOne(lqw);
        int period = scheduleTask.getPeriod();
        IPage<CourseVO> listResult = courseMapper.selectCourseByStudentNumber(page,studentNumber,courseNum,courseName,period);
        List<CourseVO> selectCourseVOList = listResult.getRecords().stream().map(src -> {
            CourseVO dst = new CourseVO();
            BeanUtils.copyProperties(src, dst);
            dst.setSchoolVal(SchoolEnum.getByCode(src.getSchool()).getDesc());
            return dst;
        }).collect(Collectors.toList());
        listResult.setRecords(selectCourseVOList);
        return PageResult.success(listResult);
    }
    @Override
    public PageResult<CourseVO> listTeacherCourse(String teacherNumber,String courseNum,String courseName,Integer current,Integer pageSize){
        Page page = new Page(current, pageSize);
        LambdaQueryWrapper<ScheduleTask> lqw = new LambdaQueryWrapper<ScheduleTask>();
        lqw.eq(ScheduleTask::getStatus,1);
        ScheduleTask scheduleTask = scheduleTaskMapper.selectOne(lqw);
        int period = scheduleTask.getPeriod();
        IPage<CourseVO> listResult = courseMapper.selectCourseByTeacherNumber(page,teacherNumber,courseNum,courseName,period);
        List<CourseVO> selectCourseVOList = listResult.getRecords().stream().map(src -> {
            CourseVO dst = new CourseVO();
            BeanUtils.copyProperties(src, dst);
            dst.setSchoolVal(SchoolEnum.getByCode(src.getSchool()).getDesc());
            return dst;
        }).collect(Collectors.toList());
        listResult.setRecords(selectCourseVOList);
        return PageResult.success(listResult);
    }

    @Override
    public  PageResult<CourseVO> listHomeworkCourse(String studentNum){
        List<CourseVO> listResult = courseMapper.selectCourseByHomework(studentNum);
        return PageResult.success(listResult);
    }
    @Override
    public Result<CourseVO> selectCourse(String courseNumber,String courseIndex){
        CourseVO course = courseMapper.selectCourseByNumber(courseNumber,courseIndex);
        course.setSchoolVal(SchoolEnum.getByCode(course.getSchool()).getDesc());
        return Result.success(course);
    }
    @Override
    public Result<TeacherVO> selectTeacher(String courseNumber, String courseIndex){
        CourseVO course = courseMapper.selectCourseByNumber(courseNumber,courseIndex);
        String teacherNum = course.getTeacherNum();
        TeacherVO teacherVO = courseMapper.selectTeacherByTeacherNumber(teacherNum);
        teacherVO.setSchoolVal(SchoolEnum.getByCode(teacherVO.getSchool()).getDesc());
        teacherVO.setTitleVal(TitleEnum.getByCode(teacherVO.getTitle()).getDesc());
        return Result.success(teacherVO);
    }
    @Override
    public int getCourseStudentNum(String courseNumber, String courseIndex){
        int num = courseMapper.getStudentNum(courseNumber,courseIndex);
        return num;
    }
    @Override
    public PageResult<StudentVO> getStudentList(String courseNumber, String courseIndex, Integer current, Integer pageSize){
        Page page = new Page(current, pageSize);
        IPage<StudentVO> listResult = courseMapper.getStudentList(page,courseNumber,courseIndex);
        List<StudentVO> studentList = listResult.getRecords().stream().map(src -> {
            StudentVO dst = new StudentVO();
            BeanUtils.copyProperties(src, dst);
            dst.setSchoolVal(SchoolEnum.getByCode(src.getSchool()).getDesc());
            return dst;
        }).collect(Collectors.toList());
        listResult.setRecords(studentList);
        return PageResult.success(listResult);
    }

    @Override
    public PageResult<CourseVO> listUncorrectHomeworkCourse(String teacherNum){
        List<CourseVO> listResult = courseMapper.selectCourseByUCHomework(teacherNum);
        return PageResult.success(listResult);
    }
}
