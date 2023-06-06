package com.xatu.evaluation.Dao;


import com.xatu.evaluation.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {
    Course findCourseByNumber(String number);
}
