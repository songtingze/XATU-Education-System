package com.xatu.system.domain.vo;

import com.xatu.common.enums.SchoolEnum;
import com.xatu.system.domain.Student;
import lombok.Data;


/**
 * @author Wang Lei
 */
@Data
public class StudentVo extends Student {
    private String userToken;
    private String schoolValue;

    public StudentVo (Student student) {
        this.setId(student.getId());
        this.setName(student.getName());
        this.setNumber(student.getNumber());
        this.setSchool(student.getSchool());
        this.setSchoolValue(SchoolEnum.getByCode(student.getSchool()).getDesc());
        this.setClassNumber(student.getClassNumber());
        this.setEnrollmentTime(student.getEnrollmentTime());
        this.setSex(student.getSex());
        this.setBirth(student.getBirth());
        this.setNation(student.getNation());
        this.setHousehold(student.getHousehold());
        this.setPhotoUrl(student.getPhotoUrl());
        this.setCreateTime(student.getCreateTime());
        this.setUpdateTime(student.getUpdateTime());
    }
}
