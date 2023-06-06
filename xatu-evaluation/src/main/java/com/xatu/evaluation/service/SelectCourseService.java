package com.xatu.evaluation.service;

import com.xatu.evaluation.entity.CourseList;

import java.util.List;

public interface SelectCourseService {
    String studentScoreTeacher(int student, int score, String course);

    List<CourseList> studentGetUnscoreCourse(int student);

    List<CourseList> studentGetScoreCourse(int student);
}
