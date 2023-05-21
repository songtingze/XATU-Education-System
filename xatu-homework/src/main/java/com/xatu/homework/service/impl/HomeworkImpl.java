package com.xatu.homework.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.homework.domain.Attachment;
import com.xatu.homework.domain.Course;
import com.xatu.homework.domain.Homework;
import com.xatu.homework.domain.Student;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.TeacherVO;
import com.xatu.homework.mapper.AttachmentMapper;
import com.xatu.homework.mapper.CourseMapper;
import com.xatu.homework.mapper.HomeworkMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.xatu.homework.service.HomeworkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HomeworkImpl implements HomeworkService {
    @Resource
    private HomeworkMapper homeworkMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private AttachmentMapper attachmentMapper;
    //获取配置文件中的配置 为属性动态赋值 注解@Value

    private static final String localPathDir = "C:/Users/hp/Desktop/dir";  // Windows路径

    private String localUrlPath;  // Linux路径
    @Override
    public PageResult<CourseVO> listStudentSelectedCourse(Integer studentNumber){
        List<CourseVO> listResult = courseMapper.selectCourseByStudentNumber(studentNumber);
        return PageResult.success(listResult);
    }
    @Override
    public PageResult<CourseVO> listStudentSelectedCourse(Integer studentNumber,Integer current,Integer pageSize){
        Page page = new Page(current, pageSize);
        IPage<CourseVO> listResult = courseMapper.selectCourseByStudentNumber(page,studentNumber);
        return PageResult.success(listResult);
    }
    @Override
    public PageResult<CourseVO> listTeacherCourse(Integer teacherNumber){
        List<CourseVO> listResult = courseMapper.selectCourseByTeacherNumber(teacherNumber);
        return PageResult.success(listResult);
    }

    @Override
    public PageResult<CourseVO> listTeacherCourse(Integer teacherNumber,Integer current,Integer pageSize){
        Page page = new Page(current, pageSize);
        IPage<CourseVO> listResult = courseMapper.selectCourseByTeacherNumber(page,teacherNumber);
        return PageResult.success(listResult);
    }
    @Override
    public Result<Course> selectCourse(String courseNumber){
        LambdaQueryWrapper<Course> lqw = new LambdaQueryWrapper<Course>();
        lqw.eq(Course::getNumber,courseNumber);
        Course course = (Course) courseMapper.selectOne(lqw);
        return Result.success(course);
    }
    @Override
    public Result<TeacherVO> selectTeacher(String courseNumber,Integer courseIndex){
        TeacherVO teacherVO = courseMapper.selectTeacherByCourseNumber(courseNumber,courseIndex);
        return Result.success(teacherVO);
    }
    @Override
    public PageResult<Student> getStudentList(String courseNumber, Integer courseIndex,Integer current,Integer pageSize){
        Page page = new Page(current, pageSize);
        IPage<Student> listResult = courseMapper.getStudentList(page,courseNumber,courseIndex);
        return PageResult.success(listResult);
    }
    @Override
    public Result upload(MultipartFile file) throws IOException {
        //1.1 获取文件名称
        String fileName = file.getOriginalFilename();

        //2. 目录结构
        //2.1 实现分目录存储  可以以时间维度年月日进行分隔 /yyyy/MM/dd/
        String datePath =
                new SimpleDateFormat("/yyyyMMdd/").format(new Date());
        //2.2 最终本地图片存储路径
        //    进行目录的拼接  "/Users/zhaoguoxing/Desktop/files/20220322";
        String localDir = localPathDir + datePath;
        //2.3 需要创建目录
        File dirFile = new File(localDir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        //3.文件名称重复  采用UUID防止文件重名 uuid.pdf
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //3.1.获取文件类型
        //fileName = abc.jpg  fileType=.pdf
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        //3.2.重新拼接文件名  uuid.pdf
        String realFileName = uuid + "." +fileType;

        //virtualPath 半个路径，没有具体盘符或根目录 /2021/11/11/uuid.pdf
        //4.最终文件存储的路径+文件名 = /2021/11/11/uuid.pdf
        String virtualPath = datePath + realFileName;
        //可以在这里将路径存储到数据库 实际保存文件地址
        Attachment attachment = new Attachment();
        attachment.setFile(uuid);
        attachment.setName(fileName);
        attachment.setType(fileType);
        attachment.setFileUrl(virtualPath);
        Date now = new Date();
        attachment.setCreateTime(now);
        attachment.setUpdateTime(now);
        int result = attachmentMapper.insert(attachment);

        //4.最终文件存储的路径+文件名 = /2021/11/11/uuid.pdf
        String filePathAll = localDir + realFileName;
        //5.实现文件上传
        File realFile = new File(filePathAll);
        file.transferTo(realFile);

        if(result == 1) return Result.success(uuid,"上传成功");
        else return Result.error("500","上传失败");
    }
    @Override
    public Result download(ServletOutputStream os, String fileIndex) throws IOException {
        //下载文件的路径
        LambdaQueryWrapper<Attachment> lqw = new LambdaQueryWrapper<Attachment>();
        lqw.eq(Attachment::getFile,fileIndex);
        Attachment attachment = attachmentMapper.selectOne(lqw);
        System.out.println(attachment);
        String downPath = localPathDir+attachment.getFileUrl();
        System.out.println(downPath);
        //读取目标文件
        File f = new File(downPath);
        //创建输入流
        InputStream is = new FileInputStream(f);
        //做一些业务判断，我这里简单点直接输出，你也可以封装到实体类返回具体信息
        if (is == null) {
            return Result.error("500","文件不存在");
        }
        //利用IOUtils将输入流的内容 复制到输出流
        //org.apache.tomcat.util.http.fileupload.IOUtils
        //项目搭建是自动集成了这个类 直接使用即可
        IOUtils.copy(is, os);
        os.flush();
        is.close();
        os.close();
        return Result.success("1","下载成功");
    }
//    @Override
//    public PageResult<Homework> listCourseHomework(String course, Integer current,Integer pageSize) {
//        LambdaQueryWrapper<Homework> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Homework::getCourse, course);
//        IPage page = new Page(current, pageSize);
//        IPage<Homework> pageResult = homeworkMapper.selectPage(page,wrapper);
//        return PageResult.success(pageResult);
//
//    }
}
