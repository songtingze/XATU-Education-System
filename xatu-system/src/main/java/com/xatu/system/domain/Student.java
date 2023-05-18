package com.xatu.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Wang Lei
 */

@Data
public class Student {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String number;
    private String password;
    private String school;
    private String classNumber;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date enrollmentTime;
    private String sex;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birth;
    private String nation;
    private String household;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss",timezone = "GMT+8")
    private Date updateTime;
    private String photoUrl;

}
