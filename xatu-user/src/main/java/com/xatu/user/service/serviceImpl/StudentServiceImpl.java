package com.xatu.user.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xatu.user.domain.Student;
import com.xatu.user.mapper.StudentMapper;
import com.xatu.user.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Wang Lei
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Override
    public Student login(String number, String password) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getNumber, number).eq(Student::getPassword, password);
        Student student = studentMapper.selectOne(wrapper);
        // 重要信息置空
        if (student != null) {
            student.setPassword("");
        }
        return student;
    }
}
