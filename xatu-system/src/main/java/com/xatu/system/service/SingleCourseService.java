package com.xatu.system.service;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.Course;
import com.xatu.system.domain.SingleCourse;
import com.xatu.system.domain.vo.SelectionValue;
import com.xatu.system.domain.vo.SingleCourseVo;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

public interface SingleCourseService {

    PageResult<SingleCourse> getSingleCourseList(SingleCourseVo singleCourseVo);

    public boolean batchImport(String fileName, MultipartFile file) throws Exception;//导入Excel

    public Result<Boolean> update(SingleCourse newSingleCourse);

    public Result<Boolean> add(SingleCourse newSingleCourse) throws ParseException;

    public Result<Boolean> delete(int id);

    public Result<Boolean> batchDelete(String sid);
}
