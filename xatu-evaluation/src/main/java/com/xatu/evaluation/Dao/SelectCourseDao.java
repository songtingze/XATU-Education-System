package com.xatu.evaluation.Dao;


import com.xatu.evaluation.entity.SelectCourse;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectCourseDao extends JpaRepository<SelectCourse, Long> {
    //SelectCourse updateSelectCourseByStudentAndCourse(int student, String course, SelectCourse selectCourse);

    SelectCourse findSelectCourseByStudentAndCourse(int student, String course);

    @Query(value = "select * from select_course sc where sc.score is null and student = :student", nativeQuery = true)
    List<SelectCourse> getCourseUnscoreList(int student);
    @Query(value = "select * from select_course sc where sc.score is not null and student = :student", nativeQuery = true)
    List<SelectCourse> getCourseScoreList(int student);
}
