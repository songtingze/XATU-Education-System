package com.xatu.course.domain.vo;

import lombok.Data;

import java.util.Map;

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

    private String assessment;

    private String teacher;

    private Map<String, Object> schedule;

    private Integer selected;
}
