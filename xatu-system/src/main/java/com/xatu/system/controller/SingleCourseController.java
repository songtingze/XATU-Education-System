package com.xatu.system.controller;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.system.domain.Course;
import com.xatu.system.domain.SingleCourse;
import com.xatu.system.domain.vo.CourseVo;
import com.xatu.system.domain.vo.SelectionValue;
import com.xatu.system.domain.vo.SingleCourseVo;
import com.xatu.system.service.CourseService;
import com.xatu.system.service.SingleCourseService;
import com.xatu.system.service.TeacherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/singleCourse")
public class SingleCourseController {
    @Resource
    private SingleCourseService singleCourseService;
    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;

    @PostMapping("/getSingleCourseList")
    public PageResult<SingleCourseVo> getStudentList(@RequestBody SingleCourseVo searchInfo){
        PageResult<SingleCourse> pageResult = singleCourseService.getSingleCourseList(searchInfo);
        List<SingleCourse> singleCourseList = pageResult.getData();
        List<SingleCourseVo> singleCourseVos = new ArrayList<>();
        for(SingleCourse singleCourse:singleCourseList){
            SingleCourseVo singleCourseVo = new SingleCourseVo(singleCourse);
            singleCourseVo.setCourseName(courseService.getCourseByNumber(singleCourse.getCourseNum()).getName());
            singleCourseVo.setTeacherName(teacherService.getTeacherByNumber(singleCourse.getTeacher()).getName());
            singleCourseVo.setSchool(courseService.getCourseByNumber(singleCourse.getCourseNum()).getSchool());
            singleCourseVo.setSchoolValue(SchoolEnum.getByCode(singleCourseVo.getSchool()).getDesc());
            singleCourseVos.add(singleCourseVo);
        }
        return PageResult.success(singleCourseVos,pageResult.getTotal(),pageResult.getCurrent());

    }

    @PostMapping("/import")
    public boolean exImport(@RequestParam("file") MultipartFile file){
        boolean isSuccess = false;
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            isSuccess = singleCourseService.batchImport(fileName,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(isSuccess);
        return isSuccess;
    }

    @PutMapping ("/update")
    public Result<Boolean> updateSingleCourse(@RequestBody SingleCourse singleCourse){
        return singleCourseService.update(singleCourse);
    }

    @PostMapping("/add")
    public Result<Boolean> addCourse (@RequestBody Course course) throws ParseException {
        return courseService.add(course) ;
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteSingleCourse(@RequestParam Integer id){
        return singleCourseService.delete(id);
    }
    @DeleteMapping("/batchDelete")
    public Result<Boolean> batchDeleteSingleCourse(@RequestParam String sid){
        return singleCourseService.batchDelete(sid);
    }

}
