package com.xatu.homework.controller;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.HomeworkStateEnum;
import com.xatu.common.enums.StudentHomeworkEnum;
import com.xatu.homework.domain.Homework;
import com.xatu.homework.domain.StudentHomework;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.StudentHomeworkVO;
import com.xatu.homework.domain.vo.StudentVO;
import com.xatu.homework.domain.vo.TeacherVO;
import com.xatu.homework.service.CourseService;
import com.xatu.homework.service.HomeworkService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/homework")
public class HomeworkController {
    @Resource
    private CourseService courseService;

    @Resource
    private HomeworkService homeworkService;


    /**
     * 获取学生选课列表（分页）
     */
    @GetMapping("/platform/stu/page")
    public PageResult<CourseVO> studentCourse(@RequestParam("userId") String studentNumber,
                                              String courseNum,
                                              String courseName,
                                              @RequestParam(defaultValue = "1") Integer current,
                                              @RequestParam(defaultValue = "20") Integer pageSize) {
        return courseService.listStudentSelectedCourse(studentNumber, courseNum, courseName, current, pageSize);
    }

    /**
     * 获取教师教授课程（分页）
     */
    @GetMapping("/platform/tec/page")
    public PageResult<CourseVO> teacherCourse(@RequestParam("userId") String teacherNumber,
                                              String courseNum,
                                              String courseName,
                                              @RequestParam(defaultValue = "1") Integer current,
                                              @RequestParam(defaultValue = "20") Integer pageSize) {
        return courseService.listTeacherCourse(teacherNumber, courseNum, courseName, current, pageSize);
    }


    /**
     * 获取未完成作业课程列表
     */
    @GetMapping("/platform/task")
    public PageResult<CourseVO> courseHomework(@RequestParam("userId") String studentNum) {
        return courseService.listHomeworkCourse(studentNum);
    }

    /**
     * 获取未批改作业课程列表
     */
    @GetMapping("/platform/tec/task")
    public PageResult<CourseVO> uncorrectHomework(@RequestParam("userId") String teacherNumber) {
        return courseService.listUncorrectHomeworkCourse(teacherNumber);
    }


    /**
     * 获取课程信息
     */
    @GetMapping("/platform/info")
    public Result<CourseVO> courseInfo(@RequestParam String courseNumber,
                                       @RequestParam String courseIndex) {
        return courseService.selectCourse(courseNumber, courseIndex);
    }



    /**
     * 获取课程教授老师信息
     */
    @GetMapping("/platform/tec/info")
    public Result<TeacherVO> teacherInfo(@RequestParam String courseNumber,
                                         @RequestParam String courseIndex) {
        return courseService.selectTeacher(courseNumber, courseIndex);
    }

    /**
     * 获取选课学生数
     */
    @GetMapping("/platform/studentNum")
    public Result<Integer> studentNum(@RequestParam String courseNumber,
                                      @RequestParam String courseIndex) {
        return Result.success(courseService.getCourseStudentNum(courseNumber, courseIndex));
    }


    /**
     * 获取选取课程的全部学生
     */
    @GetMapping("/platform/tec/studentList")
    public PageResult<StudentVO> studentList(@RequestParam String courseNumber,
                                             @RequestParam String courseIndex,
                                             @RequestParam(defaultValue = "1") Integer current,
                                             @RequestParam(defaultValue = "20") Integer pageSize
    ) {
        return courseService.getStudentList(courseNumber, courseIndex, current, pageSize);
    }

    /**
     * 课程作业数
     */

    @GetMapping("/count")
    public Result<Integer> homeworkNum(@RequestParam String courseNumber,
                                       @RequestParam String courseIndex) {
        return Result.success(homeworkService.getCourseHomeworkNum(courseNumber, courseIndex));

    }


    /**
     * 获取发布课程作业列表
     */
    @GetMapping("/list")
    public PageResult<Homework> homeworklist(@RequestParam String courseNumber,
                                             @RequestParam String courseIndex,
                                             @RequestParam(defaultValue = "1") Integer current,
                                             @RequestParam(defaultValue = "20") Integer pageSize) {
        return homeworkService.listCourseHomework(courseNumber, courseIndex, current, pageSize);

    }

    /**
     * 获取课程作业列表
     */
    @GetMapping("/stu/list")
    public PageResult<StudentHomeworkVO> studentHomeworkList(@RequestParam String studentNumber,
                                                           @RequestParam(defaultValue = "1") Integer current,
                                                           @RequestParam(defaultValue = "20") Integer pageSize) {
        return homeworkService.listStudentHomework(studentNumber, current, pageSize);
    }

    /**
     * 获取课程作业列表
     */
    @GetMapping("/tec/list")
    public PageResult<StudentHomeworkVO> teacherHomeworkList(@RequestParam String homework,
                                                             @RequestParam(defaultValue = "1") Integer current,
                                                             @RequestParam(defaultValue = "20") Integer pageSize) {
        return homeworkService.listTeacherHomework(homework, current, pageSize);
    }


    /**
     * 获取课程作业
     */
    @GetMapping("/search")
    public Result<Homework> search(@RequestParam String homeworkNumber) {
        return homeworkService.getHomeworkByNumber(homeworkNumber);

    }

    /**
     * 发布课程作业
     */
    @PostMapping("/insert")
    public Result<String> insert(@RequestParam String content,
                                 @RequestParam String deadline,
                                 @RequestParam Integer isRepeat,
                                 @RequestParam Float ratio,
                                 @RequestParam String title,
                                 @RequestParam String courseNum,
                                 @RequestParam String courseIndex,
                                 @RequestParam Integer grade,
                                 @RequestParam String teacher) throws ParseException {

        Homework homework = new Homework();
        String homeworkNumber = homeworkService.getHomeworkNum(courseNum, courseIndex);
        homework.setHomework(homeworkNumber);
        homework.setContent(content);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(deadline);
        homework.setDeadline(date);
        homework.setIsRepeat(isRepeat);
        homework.setTeacher(teacher);
        homework.setRatio(ratio);
        homework.setTitle(title);
        homework.setGrade(grade);
        homework.setCourseNum(courseNum);
        homework.setCourseIndex(courseIndex);
        homework.setStatus(HomeworkStateEnum.getByDesc("进行"));
        homework.setCreateTime(new Date());
        homework.setUpdateTime(new Date());
        if (!homeworkService.insertHomework(homework))
            return Result.error("500", "发布失败");

        PageResult<StudentVO> studentList = courseService.getStudentList(courseNum, courseIndex, 1, Integer.MAX_VALUE);
        for (StudentVO student : studentList.getData()) {
            StudentHomework studentHomework = new StudentHomework();
            studentHomework.setHomework(homework.getHomework());
            studentHomework.setStudent(student.getNumber());
            studentHomework.setStatus(StudentHomeworkEnum.getByDesc("未提交"));
            studentHomework.setCreateTime(new Date());
            studentHomework.setUpdateTime(new Date());
            if (!homeworkService.insertStudentHomework(studentHomework))
                return Result.error("500", "发布失败");
        }
        return Result.success("发布成功");
    }

    /**
     * 修改课程作业发布
     */
    @PostMapping("/update")
    public Result<String> update(@RequestParam String content,
                                 @RequestParam String deadline,
                                 @RequestParam Integer isRepeat,
                                 @RequestParam Float ratio,
                                 @RequestParam String title,
                                 @RequestParam String courseNum,
                                 @RequestParam String courseIndex,
                                 @RequestParam Integer grade,
                                 @RequestParam String teacher,
                                 @RequestParam String homeworkNum) throws ParseException {
        Homework homework = new Homework();
        homework.setHomework(homeworkNum);
        homework.setContent(content);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(deadline);
        homework.setDeadline(date);
        homework.setIsRepeat(isRepeat);
        homework.setTeacher(teacher);
        homework.setRatio(ratio);
        homework.setTitle(title);
        homework.setGrade(grade);
        homework.setCourseNum(courseNum);
        homework.setCourseIndex(courseIndex);
        homework.setStatus(HomeworkStateEnum.getByDesc("进行"));
        homework.setUpdateTime(new Date());
        if (!homeworkService.updateHomework(homework))
            return Result.error("500", "发布失败");
        return Result.success("发布成功");
    }

    /**
     * 修改课程作业分数
     */
    @PostMapping("/score")
    public Result<String> score(@RequestParam String homework,
                                @RequestParam String studentNumber,
                                @RequestParam Float score) {
        if (!homeworkService.updateHomeworkScore(homework,studentNumber,score))
            return Result.error("500", "提交失败");
        return Result.success("提交成功");

    }


    /**
     * 删除课程作业
     */
    @GetMapping("/delete")
    public Result<String> delete(@RequestParam String homeworkNum) {
        if (!homeworkService.deleteHomework(homeworkNum)) return Result.error("500", "删除失败");
        return Result.success("删除成功");
    }

    /**
     * 提交作业
     */
    @PostMapping("/submit")
    public Result<String> submitHomework(@RequestParam String homework,
                                         @RequestParam String studentNumber,
                                         @RequestParam String content) {
        if (!homeworkService.updateStudentHomework(homework,studentNumber,content))
            return Result.error("500", "提交失败");
        return Result.success("提交成功");
    }


    /**
     * 上传附件
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
//        System.out.println("upload--file is"+file);
        return homeworkService.upload(file);
    }

    /**
     * 下载附件
     */
    @GetMapping("/download")
    public Result downloadFile(HttpServletResponse response, @RequestParam String fileIndex, @RequestParam String fileName) throws IOException {
        // 清空输出流
        response.reset();
        response.setContentType("application/x-download;charset=UTF-8");
//        response.setHeader("Content-Disposition", "attachment;fileIndex="+ new String(fileIndex.getBytes("utf-8"), "utf-8"));
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        return homeworkService.download(response.getOutputStream(), fileIndex);
    }
    /**
     * 作业截止判断
     */
    @Scheduled(cron = "0 0 * * * ?") // cron表达式：每小时00分钟执行
    public void ChangeHomeworkStatus() {
        System.out.println("定时任务");
        List<Homework> list= homeworkService.getHomeworkList();
        if(list.size() == 0) return;
        else{
            for(Homework homework : list){
                Date date = new Date();
                int result = date.compareTo(homework.getDeadline());
               if (result > 0 && homework.getStatus() == HomeworkStateEnum.getByDesc("进行")) {
                    // date 大于 deadline
                   homeworkService.updateHomeworkStatus(homework.getHomework());
                   List<StudentHomework> studentHomeworkList= homeworkService.studentHomeworkList(homework.getHomework());
                   for(StudentHomework studentHomework : studentHomeworkList){
                       if(studentHomework.getStatus() == StudentHomeworkEnum.getByDesc("未提交")){
                           homeworkService.updateStudentHomeworkStatus(studentHomework.getHomework(),studentHomework.getStudent());
                       }
                   }
                }
            }
        }

    }


    /**
     * 获取学生未完成作业课程列表
     */
//    @PostMapping("/homework/incomplete")
//    public PageResult<Homework> getStudentHomeworkCourseList() {
//
//    }


}

