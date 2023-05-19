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
    private Integer student;
    private Float score;
    private String content;
    private Integer isAppendix;
    private String appendix;
    private String status;
    @TableField( fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
