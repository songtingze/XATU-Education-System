package com.xatu.homework.controller;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.homework.domain.Homework;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.xatu.homework.service.HomeworkService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/homework")
public class HomeworkController {
    @Resource
    private HomeworkService homeworkService;

    @GetMapping("/selected/list")
    public PageResult<Homework> listSelectedCourse(@RequestParam String course, Integer current,Integer pageSize) {
        return homeworkService.listCourseHomework(course,current,pageSize);
    }

}
