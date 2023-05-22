package com.xatu.user.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xatu.user.domain.Teacher;
import com.xatu.user.mapper.TeacherMapper;
import com.xatu.user.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Wang Lei
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(String number, String password) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getNumber, number).eq(Teacher::getPassword, password);
        Teacher teacher = teacherMapper.selectOne(wrapper);
        // 重要信息置空
        if (teacher != null) {
            teacher.setPassword("");
        }
        return teacher;
    }

    @Override
    public Teacher changePhoto(int id, String photoUrl) {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getId, id);
        Teacher teacher = teacherMapper.selectOne(wrapper);
        if (teacher != null) {
            teacher.setPhotoUrl(photoUrl);
            teacher.setUpdateTime(new Date());
            teacherMapper.updateById(teacher);
            teacher.setPassword("");
        }
        return teacher;
    }
}