package com.xatu.homework.domain;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Homework {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String homework;
    private String courseNum;
    private String courseIndex;
    private String teacher;
    private Float ratio;
    private Integer grade;
    private Date deadline;
    private String title;
    private String content;
    private Integer isAppendix;
    private String appendix;
    private Integer isRepeat;
    private Integer status;
    private Date createTime;
    private Date updateTime;

}
