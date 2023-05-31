package com.xatu.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Course {
    @TableId(type = IdType.AUTO)
    private int id;
    private String number;
    private String name;
    private String nameEn;
    private int school;
    private int credit;
    private int creditHour;
    private int assessment;
    private int grade;
    private int period;
    private int isOnlyMajor;
    private String info;
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date updateTime;

}