package com.xatu.system.domain.vo;

import com.xatu.common.enums.SchoolEnum;
import com.xatu.common.enums.TitleEnum;
import com.xatu.system.domain.Teacher;
import lombok.Data;


/**
 * @author Wang Lei
 */
@Data
public class TeacherVo extends Teacher {
    private String userToken;
    private String schoolValue;
    private String titleValue;
    private int current;
    private int size;

    public TeacherVo(Teacher teacher) {
        this.setId(teacher.getId());
        this.setName(teacher.getName());
        this.setNumber(teacher.getNumber());
        this.setPassword(teacher.getPassword());
        this.setSchool(teacher.getSchool());
        this.setSchoolValue(SchoolEnum.getByCode(teacher.getSchool()).getDesc());
        this.setTitle(teacher.getTitle());
        this.setTitleValue(TitleEnum.getByCode(teacher.getTitle()).getDesc());
        this.setEntryTime(teacher.getEntryTime());
        this.setSex(teacher.getSex());
        this.setBirth(teacher.getBirth());
        this.setNation(teacher.getNation());
        this.setPhotoUrl(teacher.getPhotoUrl());
        this.setCreateTime(teacher.getCreateTime());
        this.setUpdateTime(teacher.getUpdateTime());
    }

    public TeacherVo(){

    }
}
