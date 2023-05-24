package com.xatu.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Teacher {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String number;
    private String password;
    private int school;
    private int title;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date entryTime;
    private String sex;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birth;
    private String nation;
    private String photoUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date updateTime;
}
