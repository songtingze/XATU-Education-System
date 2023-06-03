package com.xatu.homework.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class  StudentHomeworkVO {
    private String homework;
    private String student;
    private String studentName;
    private Float score;
    private String content;
    private Integer status;
    private String teacher;
    private Integer grade;
    private Date deadline;
    private String title;
    private String homeworkContent;
    private Integer isRepeat;
    private Integer homeworkStatus;

    private String updateTime;

}
