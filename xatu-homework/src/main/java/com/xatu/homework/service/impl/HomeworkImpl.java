package com.xatu.homework.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.HomeworkStateEnum;
import com.xatu.common.enums.StudentHomeworkEnum;
import com.xatu.homework.domain.*;
import com.xatu.homework.domain.vo.CourseVO;
import com.xatu.homework.domain.vo.StudentHomeworkVO;
import com.xatu.homework.domain.vo.TeacherVO;
import com.xatu.homework.mapper.AttachmentMapper;
import com.xatu.homework.mapper.CourseMapper;
import com.xatu.homework.mapper.HomeworkMapper;
import com.xatu.homework.mapper.StudentHomeworkMapper;
import org.apache.commons.lang.StringUtils;
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
    private StudentHomeworkMapper studentHomeworkMapper;
    @Resource
    private AttachmentMapper attachmentMapper;
    //获取配置文件中的配置 为属性动态赋值 注解@Value

    private static final String localPathDir = "C:/Users/hp/Desktop/dir";  // Windows路径

    private String localUrlPath;  // Linux路径

    @Override
    public  int getCourseHomeworkNum(String courseNumber, String courseIndex){
        int num = homeworkMapper.getStudentNum(courseNumber,courseIndex);
        return num;
    }
    @Override
    public PageResult<Homework> listCourseHomework(String courseNumber,String courseIndex, Integer current,Integer pageSize) {
        LambdaQueryWrapper<Homework> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Homework::getCourseNum, courseNumber);
        wrapper.eq(Homework::getCourseIndex, courseIndex);
        IPage page = new Page(current, pageSize);
        IPage<Homework> pageResult = homeworkMapper.selectPage(page,wrapper);
        return PageResult.success(pageResult);
    }
    @Override
    public PageResult<StudentHomeworkVO> listStudentHomework(String studentNumber, Integer current, Integer pageSize){
        Page page = new Page(current, pageSize);
        IPage<StudentHomeworkVO> pageResult = studentHomeworkMapper.selectstudentHomeworkList(page,studentNumber);
        return PageResult.success(pageResult);
    }
    @Override
    public PageResult<StudentHomeworkVO> listTeacherHomework(String homework, Integer current, Integer pageSize){
        Page page = new Page(current, pageSize);
        IPage<StudentHomeworkVO> pageResult = studentHomeworkMapper.selectTeacherHomeworkList(page,homework);
        return PageResult.success(pageResult);
    }
    @Override
    public Result<Homework> getHomeworkByNumber(String homeworkNumber){
        LambdaQueryWrapper<Homework> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Homework::getHomework, homeworkNumber);
        Homework homework = homeworkMapper.selectOne(wrapper);
        return Result.success(homework);

    }
    @Override
    public Boolean insertHomework(Homework homework){
        int count = homeworkMapper.insert(homework);
        if(count!= 0)  return true;
        else return false;
    }

    @Override
    public Boolean insertStudentHomework(StudentHomework studentHomework){
        int count = studentHomeworkMapper.insert(studentHomework);
        if(count!= 0)  return true;
        else return false;
    }

    @Override
    public Boolean updateHomework(Homework homework){
        UpdateWrapper<Homework> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("homework", homework.getHomework());
        int count = homeworkMapper.update(homework, updateWrapper);
        if(count!= 0)  return true;
        else return false;
    }
    @Override
    public Boolean updateHomeworkScore(String homework, String studentNumber, Float scores){
        UpdateWrapper<StudentHomework> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("homework", homework)
                .eq("student",studentNumber);
        StudentHomework studentHomework = new StudentHomework();
        studentHomework.setScore(scores);
        int count = studentHomeworkMapper.update(studentHomework, updateWrapper);
        if(count!= 0)  return true;
        else return false;
    }

    @Override
    public Boolean updateStudentHomework(String homework,String studentNumber,String content){
        UpdateWrapper<StudentHomework> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("homework", homework)
                     .eq("student",studentNumber);
        StudentHomework studentHomework = new StudentHomework();
        studentHomework.setContent(content);
        studentHomework.setStatus(StudentHomeworkEnum.getByDesc("已提交"));
        studentHomework.setUpdateTime(new Date());
        int count = studentHomeworkMapper.update(studentHomework, updateWrapper);
        if(count!= 0)  return true;
        else return false;
    }

    @Override
    public Boolean deleteHomework(String homeworkNum){
        QueryWrapper<Homework> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("homework",homeworkNum);
        int count = homeworkMapper.delete(wrapper1);

        QueryWrapper<StudentHomework> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("homework",homeworkNum);
        count += studentHomeworkMapper.delete(wrapper2);

        if(count !=0) return true;
        else return false;
    }

    @Override
    public String getHomeworkNum(String courseNumber,String courseIndex){
        List<Homework> homeworkList= homeworkMapper.getHomeworkNum(courseNumber,courseIndex);
        if(homeworkList.size() == 0) return courseNumber+courseIndex+"001";
        else{
            String homeworkNum = homeworkList.get(0).getHomework();
            String numStr = homeworkNum.substring(homeworkNum.length() - 3);
            if (!StringUtils.isEmpty(numStr)) {
                int n = numStr.length();
                int num = Integer.parseInt(numStr) + 1;
                String added = String.valueOf(num);
                n = Math.min(n, added.length());
                return homeworkNum.subSequence(0, homeworkNum.length() - n) + added;
            } else {
                throw new NumberFormatException();
            }
        }

    }

    @Override
    public Result upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        String datePath =
                new SimpleDateFormat("/yyyyMMdd/").format(new Date());

        String localDir = localPathDir + datePath;

        File dirFile = new File(localDir);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");

        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        String realFileName = uuid + "." +fileType;

        String virtualPath = datePath + realFileName;
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
    @Override
    public List<Homework> getHomeworkList(){
        List<Homework> list = homeworkMapper.selectList(null);
        return list;
    }

    @Override
    public Boolean updateHomeworkStatus(String homeworkNum){
        UpdateWrapper<Homework> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("homework", homeworkNum);
        Homework homework = new Homework();
        homework.setStatus(HomeworkStateEnum.getByDesc("截止"));
        int count = homeworkMapper.update(homework, updateWrapper);
        if(count!= 0)  return true;
        else return false;
    }

    @Override
    public List<StudentHomework> studentHomeworkList(String homework){
        LambdaQueryWrapper<StudentHomework> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentHomework::getHomework, homework);
        List<StudentHomework> list= studentHomeworkMapper.selectList(wrapper);
        return list;
    }

    @Override
    public Boolean updateStudentHomeworkStatus(String homework,String studentNum){
        UpdateWrapper<StudentHomework> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("homework", homework)
                     .eq("student", studentNum);
        StudentHomework studentHomework = new StudentHomework();
        float score = 0;
        studentHomework.setScore(score);
        int count = studentHomeworkMapper.update(studentHomework, updateWrapper);
        if(count!= 0)  return true;
        else return false;
    }

}
