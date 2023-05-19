package com.xatu.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.homework.domain.Homework;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HomeworkMapper extends BaseMapper<Homework> {
    Page<Homework> selectByCourse(@Param("course") String course , Page<Homework> page);

}
