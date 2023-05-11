package com.xatu.user.domain.vo;

import com.xatu.user.domain.Student;
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
        this.setNumber(student.getNumber());
        this.setPassword(student.getPassword());
        this.setSchool(student.getSchool());
        this.setEnrollmentTime(student.getEnrollmentTime());
        this.setAge(student.getAge());
        this.setHousehold(student.getHousehold());
        this.setCreateTime(student.getCreateTime());
        this.setUpdateTime(student.getUpdateTime());
    }
}
