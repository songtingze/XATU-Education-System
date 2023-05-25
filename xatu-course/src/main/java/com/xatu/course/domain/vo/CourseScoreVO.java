package com.xatu.course.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CourseScoreVO {

    private String term;

    private String name;

    private String courseNum;

    private Integer courseIndex;

    private Integer credit;

    private Integer grade;

    private String period;

    private String teacher;

    private Integer score;

    private Integer usual;

    private Integer level;

    private Date termTime;
}
