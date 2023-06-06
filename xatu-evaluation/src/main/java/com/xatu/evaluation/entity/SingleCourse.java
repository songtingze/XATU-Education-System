package com.xatu.evaluation.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "single_course")
@Entity
public class SingleCourse {

    @Id
    private Integer id;

    private String courseNum;

    private Integer courseIndex;

    private String teacher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getCourseIndex() {
        return courseIndex;
    }

    public void setCourseIndex(Integer courseIndex) {
        this.courseIndex = courseIndex;
    }
}
