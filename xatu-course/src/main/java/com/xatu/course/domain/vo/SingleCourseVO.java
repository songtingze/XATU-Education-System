package com.xatu.course.domain.vo;

import lombok.Data;

@Data
public class SingleCourseVO {

    private String name;

    private String courseNum;

    private Integer courseIndex;

    private Integer capacity;

    private Integer remain;

    private String info;

    private Integer credit;

    private Integer creditHour;

    private Integer grade;

    private Integer period;

    private Integer assessment;

    private String teacher;

    private Integer dayTime;

    private Integer hourPeriod;

    private String location;

    private Integer selected;

    private Integer studentCount;
}
