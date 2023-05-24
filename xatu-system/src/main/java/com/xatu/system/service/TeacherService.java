package com.xatu.system.service;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.Student;
import com.xatu.system.domain.Teacher;
import com.xatu.system.domain.vo.TeacherVo;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

public interface TeacherService {

    PageResult<Teacher> getTeacherList(TeacherVo teacherVo);

    public Result<Boolean> update(Teacher newTeacher);

    public Result<Boolean> add(Teacher newTeacher) throws ParseException;

    public Result<Boolean> delete(int id);

    public Result<Boolean> batchDelete(String sid);

    public boolean batchImport(String fileName, MultipartFile file) throws Exception;//导入Excel
}
