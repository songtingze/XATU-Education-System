package com.xatu.system.domain.vo;

import com.xatu.common.enums.*;
import com.xatu.system.domain.Course;
import lombok.Data;

@Data
public class CourseVo extends Course {
    private String userToken;
    private String schoolValue;
    private String assessmentValue;
    private String gradeValue;
    private String periodValue;
    private String isOnlyMajorValue;
    private String statusValue;
    private String creditValue;
    private String creditHourValue;
    private int current;
    private int size;

    public CourseVo(Course course){
        this.setId(course.getId());
        this.setNumber(course.getNumber());
        this.setName(course.getName());
        this.setNameEn(course.getNameEn());
        this.setSchool(course.getSchool());
        this.setSchoolValue(SchoolEnum.getByCode(course.getSchool()).getDesc());
        this.setCredit(course.getCredit());
        this.setCreditHour(course.getCreditHour());
        this.setGrade(course.getGrade());
        this.setGradeValue(GradeEnum.getByCode(course.getGrade()).getDesc());
        this.setPeriod(course.getPeriod());
        this.setPeriodValue(PeriodEnum.getByCode(course.getPeriod()).getDesc());
        this.setIsOnlyMajor(course.getIsOnlyMajor());
        this.setIsOnlyMajorValue(course.getIsOnlyMajor()==0?"全校任选":"仅限"+this.getSchoolValue()+"选");
        this.setAssessment(course.getAssessment());
        this.setAssessmentValue(CourseAssessmentEnum.getByCode(course.getAssessment()).getDesc());
        this.setInfo(course.getInfo());
        this.setStatus(course.getStatus());
        this.setStatusValue(CourseStateEnum.getByCode(course.getStatus()).getDesc());
        this.setCreateTime(course.getCreateTime());
        this.setUpdateTime(course.getUpdateTime());
    }

    public CourseVo(){

    }
}
