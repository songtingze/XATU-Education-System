package com.xatu.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Sys {
    // 注意属性名要与数据表中的字段名一致
    // 主键自增int(10)对应long
    @TableId(type = IdType.AUTO)
    private int id;

    // 用户名属性varchar对应String
    private String name;

    private int number;

    // 密码属性varchar对应String
    private String password;
}
