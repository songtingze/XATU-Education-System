package com.xatu.course.domain.vo;

import lombok.Data;

@Data
public class SelectCourseVO {

    private String name;

    private String courseNum;

    private Integer courseIndex;

    private Integer capacity;

    private Integer remain;

    private String info;

    private Integer credit;

    private Integer creditHour;

    private Integer grade;

    private String period;

    private String assessment;

    private String teacher;

    private Integer dayTime;

    private Integer hourPeriod;

    private String hourPeriodDetail;

    private String location;

    private Integer selected;
}
