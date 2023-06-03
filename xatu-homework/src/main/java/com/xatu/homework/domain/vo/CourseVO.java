package com.xatu.homework.domain.vo;

import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.vo.TeacherVO;
import lombok.Data;

import java.util.List;

@Data
public class CourseVO {

    private String courseName;
    private String courseNum;
    private Integer courseIndex;
    private String teacherName;
    private String teacherNum;
    private Integer school;
    private String schoolVal;
    private String info;

}
