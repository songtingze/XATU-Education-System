package com.xatu.system.schedule;

import com.xatu.system.service.ScheduleTaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CourseSelectTask {

    @Resource
    private ScheduleTaskService scheduleTaskService;

    /**
     * 检查选课定时任务
     */
    @Scheduled(cron = "0 0 * * * ?")    // 每小时执行一次
    public void checkCourseSelectSchedule() {
        boolean res = scheduleTaskService.checkScheduleTask();
    }
}
