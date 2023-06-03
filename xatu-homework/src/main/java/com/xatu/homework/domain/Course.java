package com.xatu.homework.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Course {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String number;
    private String name;
    private String nameEn;
    private Integer school;
    private Integer credit;
    private Integer creditHour;
    private Integer grade;
    private Integer period;
    private Integer isOnlyMajor;
    private Integer assessment;
    private String info;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}
