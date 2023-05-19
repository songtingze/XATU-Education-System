package com.xatu.homework.service;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.homework.domain.Homework;

public interface HomeworkService {


    PageResult<Homework> listCourseHomework(String course,Integer current,Integer pageSize);
}
