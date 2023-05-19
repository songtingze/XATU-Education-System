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
    private String school;
    private Integer credit;
    private Integer creditHour;
    private String assessment;
    private String grade;
    private String info;
    private Integer status;
    @TableField( fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
