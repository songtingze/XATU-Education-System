package com.xatu.system.service;

import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.Student;
import com.xatu.system.domain.vo.StudentVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Wang Lei
 */
public interface StudentService {
    /**
     *
     * @param student
     * @return
     */
    PageResult<Student> getStudentList(Student student,int current,int size);

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
}
