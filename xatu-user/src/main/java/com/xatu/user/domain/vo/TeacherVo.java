package com.xatu.user.domain.vo;

import com.xatu.user.domain.Student;
import com.xatu.user.domain.Teacher;
import lombok.Data;


/**
 * @author Wang Lei
 */
@Data
public class TeacherVo extends Teacher {
    private String userToken;

    public TeacherVo (Teacher teacher) {
        this.setId(teacher.getId());
        this.setName(teacher.getName());
        this.setNumber(teacher.getNumber());
        this.setPassword(teacher.getPassword());
        this.setSchool(teacher.getSchool());
        this.setTitle(teacher.getTitle());
        this.setEntryTime(teacher.getEntryTime());
        this.setSex(teacher.getSex());
        this.setBirth(teacher.getBirth());
        this.setNation(teacher.getNation());
        this.setPhotoUrl(teacher.getPhotoUrl());
        this.setCreateTime(teacher.getCreateTime());
        this.setUpdateTime(teacher.getUpdateTime());
    }
}
