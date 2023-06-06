package com.xatu.evaluation.Dao;


import com.xatu.evaluation.entity.SingleCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleCourseDao extends JpaRepository<SingleCourse, Long> {
    SingleCourse findSingleCourseByCourseIndexAndCourseNum(int courseIndex, String courseNum);
}
