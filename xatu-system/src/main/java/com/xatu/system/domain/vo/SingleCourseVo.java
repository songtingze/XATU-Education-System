package com.xatu.system.domain.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xatu.common.enums.CourseStateEnum;
import com.xatu.common.enums.HourPeriodEnum;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.common.enums.WeekEnum;
import com.xatu.system.domain.SingleCourse;
import com.xatu.system.service.CourseService;
import com.xatu.system.service.TeacherService;
import lombok.Data;

import javax.annotation.Resource;

@Data
public class SingleCourseVo extends SingleCourse {
    private String userToken;
    private String schoolValue;
    private int school;
    private String statusValue;
    private String courseName;
    private String teacherName;
    private String dayValue;
    private String hourPeriodValue;
    private int current;
    private int size;

    public SingleCourseVo(SingleCourse singleCourse){
        this.setId(singleCourse.getId());
        this.setCourseIndex(singleCourse.getCourseIndex());
        this.setCourseNum(singleCourse.getCourseNum());
        this.setTeacher(singleCourse.getTeacher());
        this.setDayTime(singleCourse.getDayTime());
        this.setDayValue(WeekEnum.getByCode(singleCourse.getDayTime()).getDesc());
        this.setHourPeriod(singleCourse.getHourPeriod());
        this.setHourPeriodValue(HourPeriodEnum.getByCode(singleCourse.getHourPeriod()).getDesc());
        this.setLocation(singleCourse.getLocation());
        this.setCapacity(singleCourse.getCapacity());
        this.setRemain(singleCourse.getRemain());
        this.setStatus(singleCourse.getStatus());
        this.setStatusValue(CourseStateEnum.getByCode(singleCourse.getStatus()).getDesc());
        this.setInfo(singleCourse.getInfo());
        this.setCreateTime(singleCourse.getCreateTime());
        this.setUpdateTime(singleCourse.getUpdateTime());
    }

    public  SingleCourseVo(){
        
    }
}
