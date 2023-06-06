package com.xatu.evaluation.service.impl;

import com.xatu.evaluation.Dao.CourseDao;
import com.xatu.evaluation.Dao.SelectCourseDao;
import com.xatu.evaluation.Dao.SingleCourseDao;
import com.xatu.evaluation.Dao.TeacherDao;
import com.xatu.evaluation.entity.*;
import com.xatu.evaluation.service.SelectCourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SelectCourseServiceImpl implements SelectCourseService {

    @Resource
    SelectCourseDao selectCourseDao;

    @Resource
    TeacherDao teacherDao;

    @Resource
    SingleCourseDao singleCourseDao;

    @Resource
    CourseDao courseDao;

    @Override
    public String studentScoreTeacher(int student, int score, String course) {
        SelectCourse selectCourse = selectCourseDao.findSelectCourseByStudentAndCourse(student, course);
        if (selectCourse != null) {
            selectCourse.setScore(score);
            selectCourse.setUpdateTime(new Date());
            selectCourseDao.save(selectCourse);
            return "打分成功！";
        } else {
            return "保存分数失败，请联系管理员";
        }
        //selectCourseDao.updateSelectCourseByStudentAndCourse(student, course, score);
    }

    @Override
    public List<CourseList> studentGetUnscoreCourse(int student) {
        List<CourseList> courseLists = new ArrayList<>();
        List<SelectCourse> selectCourses = selectCourseDao.getCourseUnscoreList(student);
        for (SelectCourse selectCourse : selectCourses) {
            CourseList courseList = new CourseList();
            courseList.setCourse(selectCourse.getCourse());
            SingleCourse singleCourse = singleCourseDao.findSingleCourseByCourseIndexAndCourseNum(selectCourse.getCourseIndex(), selectCourse.getCourse());
            Course course = courseDao.findCourseByNumber(selectCourse.getCourse());
            courseList.setCourseName(course.getName());
            Teacher teacher = teacherDao.findTeacherByNumber(singleCourse.getTeacher());
            courseList.setTeacherName(teacher.getName());
            courseLists.add(courseList);
        }
        return courseLists;
    }

    @Override
    public List<CourseList> studentGetScoreCourse(int student) {
        List<CourseList> courseLists = new ArrayList<>();
        List<SelectCourse> selectCourses = selectCourseDao.getCourseScoreList(student);
        for (SelectCourse selectCourse : selectCourses) {
            CourseList courseList = new CourseList();
            courseList.setCourse(selectCourse.getCourse());
            courseList.setScore(selectCourse.getScore());
            SingleCourse singleCourse = singleCourseDao.findSingleCourseByCourseIndexAndCourseNum(selectCourse.getCourseIndex(), selectCourse.getCourse());
            Course course = courseDao.findCourseByNumber(selectCourse.getCourse());
            courseList.setCourseName(course.getName());
            Teacher teacher = teacherDao.findTeacherByNumber(singleCourse.getTeacher());
            courseList.setTeacherName(teacher.getName());
            courseLists.add(courseList);
        }
        return courseLists;
    }


}
