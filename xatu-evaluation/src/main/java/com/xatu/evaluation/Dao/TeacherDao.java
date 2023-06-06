package com.xatu.evaluation.Dao;


import com.xatu.evaluation.entity.SelectCourse;
import com.xatu.evaluation.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, Long> {
    //SelectCourse updateSelectCourseByStudentAndCourse(int student, String course, SelectCourse selectCourse);

    Teacher findTeacherByNumber(String number);
}
