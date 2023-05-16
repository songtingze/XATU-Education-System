package com.xatu.system.domain.vo;

import com.xatu.system.domain.Student;
import lombok.Data;


/**
 * @author Wang Lei
 */
@Data
public class StudentVo extends Student {
    private String userToken;

    public StudentVo (Student student) {
        this.setId(student.getId());
        this.setName(student.getName());
        this.setPassword(student.getPassword());
        this.setSchool(student.getSchool());
        this.setEnrollmentTime(student.getEnrollmentTime());
        this.setAge(student.getAge());
        this.setHousehold(student.getHousehold());
        this.setPhotoUrl(student.getPhotoUrl());
        this.setCreateTime(student.getCreateTime());
        this.setUpdateTime(student.getUpdateTime());
    }
}
