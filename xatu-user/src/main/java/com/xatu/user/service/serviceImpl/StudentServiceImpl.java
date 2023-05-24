package com.xatu.user.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xatu.user.domain.Student;
import com.xatu.user.mapper.StudentMapper;
import com.xatu.user.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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

    @Override
    public Student changePhoto(int id, String photoUrl) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getId, id);
        Student student = studentMapper.selectOne(wrapper);
        if (student != null) {
            student.setPhotoUrl(photoUrl);
            student.setUpdateTime(new Date());
            studentMapper.updateById(student);
            student.setPassword("");
        }
        return student;
    }

    @Override
    public boolean changePassword(int id, String oldPwd, String newPwd) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getId, id);
        Student student = studentMapper.selectOne(wrapper);
        if (student != null) {
            if (student.getPassword().equals(oldPwd)) {
                student.setPassword(newPwd);
                student.setUpdateTime(new Date());
                studentMapper.updateById(student);
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    @Override
    public Student stuInfo (int id) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getId, id);
        Student student = studentMapper.selectOne(wrapper);
        if (student != null) {
            student.setPassword("");
        }
        return student;
    }
}
