package com.xatu.course.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SingleCourse {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer courseIndex;

    private String courseNum;

    private Integer teacher;

    private String schedule;

    private Integer capacity;

    private Integer remain;

    private Integer level;

    private Integer status;

    private String info;

    private Date createTime;

    private Date updateTime;
}
