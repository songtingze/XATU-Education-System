package com.xatu.homework.service;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.homework.domain.Attachment;
import com.xatu.homework.domain.Homework;
import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.StudentHomework;
import com.xatu.homework.domain.vo.StudentHomeworkVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface HomeworkService {

    /**
     * 课程作业数
     */
    int getCourseHomeworkNum(String courseNumber, String courseIndex);
    /**
     * 获取发布课程作业列表
     */
    PageResult<Homework> listCourseHomework(String courseNumber,String courseIndex,Integer current,Integer pageSize);
    /**
     * 获取课程作业列表
     */
    PageResult<StudentHomeworkVO> listStudentHomework(String studentNumber, Integer current, Integer pageSize);
    /**
     * 获取学生作业列表
     */
    PageResult<StudentHomeworkVO> listTeacherHomework(String homework, Integer current, Integer pageSize);
    /**
     * 发布课程作业
     */
    Boolean insertHomework(Homework homework);
    /**
     * 添加课程作业
     */
    Boolean insertStudentHomework(StudentHomework studentHomework);
    /**
     * 修改课程作业
     */
    Boolean updateHomework(Homework homework);
    /**
     * 批改课程作业
     */
    Boolean updateHomeworkScore(String homework, String studentNumber, Float scores);
    /**
     * 提交课程作业
     */
    Boolean updateStudentHomework(String homework,String studentNumber,String content);

    /**
     * 删除课程作业
     */
    Boolean deleteHomework(String homeworkNum);
    /**
     * 自增作业编号
     */
    String getHomeworkNum(String courseNumber,String courseIndex);
    /**
     * 附件上传
     */
    Result upload(MultipartFile file) throws IOException;
    /**
     * 附件下载
     */
    Result download(ServletOutputStream outputStream, String fileIndex) throws IOException;

    /**
     * 根据编号获取作业
     */
    Result<Homework> getHomeworkByNumber(String homeworkNumber);


    /**
     * 定时任务，获取所有作业信息
     */
    List<Homework> getHomeworkList();
    /**
     * 定时任务，修改作业状态
     */
    Boolean updateHomeworkStatus(String homework);
    /**
     * 定时任务，获取学生作业信息
     */
    List<StudentHomework> studentHomeworkList(String homework);
    /**
     * 定时任务，更新学生作业信息
     */
    Boolean updateStudentHomeworkStatus(String homework,String studentNum);

}
