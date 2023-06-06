package com.xatu.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xatu.homework.domain.Attachment;
import com.xatu.homework.domain.ScheduleTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleTaskMapper extends BaseMapper<ScheduleTask> {
}
