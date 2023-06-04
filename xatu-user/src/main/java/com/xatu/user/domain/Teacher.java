package com.xatu.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author Wang Lei
 */

@Data
public class Teacher {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String number;
    private String password;
    private int school;
    private int title;
    private Date entryTime;
    private String sex;
    private Date birth;
    private String nation;
    private String photoUrl;
    private Date createTime;
    private Date updateTime;

}
