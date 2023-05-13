package com.xatu.course.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Student {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String number;
    private String password;
    private String school;
    private Date enrollmentTime;
    private Integer age;
    private String household;
    private Date createTime;
    private Date updateTime;
}
