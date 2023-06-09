package com.xatu.course.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SelectCourse {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String course;

    private Integer courseIndex;

    private Integer student;

    private Integer score;

    private Integer usual;

    private Integer level;

    private Integer evaluation;

    private Integer status;

    private String term;

    private Integer period;

    private Date createTime;

    private Date updateTime;
}
