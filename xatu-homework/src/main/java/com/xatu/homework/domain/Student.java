package com.xatu.homework.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    private String classNumber;
    private Date enrollmentTime;
    private String sex;
    private Data birth;
    private String nation;
    private String household;
    private String photoUrl;
    private Date createTime;
    private Date updateTime;
}
