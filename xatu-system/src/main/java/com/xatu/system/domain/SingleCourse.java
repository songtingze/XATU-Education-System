package com.xatu.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SingleCourse {
    @TableId(type = IdType.AUTO)
    private int id;
    private int courseIndex;
    private String courseNum;
    private String teacher;
    private int dayTime;
    private int hourPeriod;
    private String location;
    private int capacity;
    private int remain;
    private int level;
    private int status;
    private String info;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date updateTime;
}
