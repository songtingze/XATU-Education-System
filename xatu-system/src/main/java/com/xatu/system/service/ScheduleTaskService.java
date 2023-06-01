package com.xatu.system.service;

import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.ScheduleTask;
import com.xatu.system.domain.vo.ScheduleTaskVo;

public interface ScheduleTaskService {
    PageResult<ScheduleTaskVo> listScheduleTask(String term, Integer period, PageQuery pageQuery);
    boolean addScheduleTask(ScheduleTask task);
    boolean updateScheduleTask(ScheduleTask task);
    boolean deleteScheduleTask(Integer id);
    boolean checkScheduleTask();
    boolean startCourseSelect(Integer id);
    boolean stopCourseSelect(Integer id);

    Result<Integer> getStatus();
}
