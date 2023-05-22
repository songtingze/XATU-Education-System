package com.xatu.system.service;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.Student;
import com.xatu.system.domain.vo.StudentVo;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

/**
 * @author Wang Lei
 */
public interface StudentService {
    /**
     *
     * @param studentVo
     * @return
     */
    PageResult<Student> getStudentList(StudentVo studentVo);

    /**
     *
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    public boolean batchImport(String fileName, MultipartFile file) throws Exception;//导入Excel

    /**
     *
     * @param newStudent
     * @return
     */
    public Result<Boolean> update(Student newStudent);

    /**
     *
     * @param newStudent
     * @return
     */
    public Result<Boolean> add(Student newStudent) throws ParseException;

    /**
     *
     * @param id
     * @return
     */
    public Result<Boolean> delete(int id);

    /**
     *
     * @param sid
     * @return
     */
    public Result<Boolean> batchDelete(String sid);
}
