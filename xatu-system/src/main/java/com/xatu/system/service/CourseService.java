package com.xatu.system.service;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.Course;
import com.xatu.system.domain.vo.CourseVo;
import com.xatu.system.domain.vo.SelectionValue;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

public interface CourseService {

    PageResult<Course> getCourseList(CourseVo courseVo);

    public boolean batchImport(String fileName, MultipartFile file) throws Exception;//导入Excel

    public SelectionValue getSelections();

    public Result<Boolean> update(Course newCourse);

    public Result<Boolean> add(Course newCourse) throws ParseException;

    public Result<Boolean> delete(int id);

    public Result<Boolean> batchDelete(String sid);

    public Course getCourseByNumber(String number);
}
