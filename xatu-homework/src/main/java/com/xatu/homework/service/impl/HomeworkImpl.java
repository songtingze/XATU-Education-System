package com.xatu.homework.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.homework.domain.Homework;
import com.xatu.homework.mapper.HomeworkMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.xatu.homework.service.HomeworkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkImpl implements HomeworkService {
    @Resource
    private HomeworkMapper homeworkMapper;
    @Override
    public PageResult<Homework> listCourseHomework(String course, Integer current,Integer pageSize) {
        LambdaQueryWrapper<Homework> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Homework::getCourse, course);
        IPage page = new Page(current, pageSize);
        IPage<Homework> pageResult = homeworkMapper.selectPage(page,wrapper);
        return PageResult.success(pageResult);

    }
}
