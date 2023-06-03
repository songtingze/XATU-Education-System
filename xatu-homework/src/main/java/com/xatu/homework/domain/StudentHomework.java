package com.xatu.homework.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class StudentHomework {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String homework;
    private String student;
    private Float score;
    private String content;
    private Integer isAppendix;
    private String appendix;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}
