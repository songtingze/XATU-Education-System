package com.xatu.homework.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class StudentVO {
    private String name;
    private String number;
    private int school;
    private String schoolVal;
    private String classNumber;

}
