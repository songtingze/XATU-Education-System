package com.xatu.system.controller;

import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.system.domain.ScheduleTask;
import com.xatu.system.domain.vo.ScheduleTaskVo;
import com.xatu.system.service.ScheduleTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys/task")
public class ScheduleTaskController {
    @Resource
    private ScheduleTaskService scheduleTaskService;

    @GetMapping("/list")
    public PageResult<ScheduleTaskVo> list(String term, Integer period, PageQuery pageQuery) {
        return scheduleTaskService.listScheduleTask(term, period, pageQuery);
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody ScheduleTask task) {
        boolean res = scheduleTaskService.addScheduleTask(task);
        if (res) {
            return Result.success();
        } else {
            return Result.error(CodeConstants.ERROR, "添加失败");
        }
    }

    @PostMapping("/update")
    public Result<Void> update(@RequestBody ScheduleTask task) {
        boolean res = scheduleTaskService.updateScheduleTask(task);
        if (res) {
            return Result.success();
        } else {
            return Result.error(CodeConstants.ERROR, "添加失败");
        }
    }

    @PostMapping("/delete")
    public Result<Void> delete(Integer id) {
        boolean res = scheduleTaskService.deleteScheduleTask(id);
        if (res) {
            return Result.success();
        } else {
            return Result.error(CodeConstants.ERROR, "删除失败");
        }
    }

    @PostMapping("/start")
    public Result<Void> startTask(Integer id) {
        boolean res = scheduleTaskService.startCourseSelect(id);
        if (res) {
            return Result.success();
        }
        return Result.error(CodeConstants.ERROR, "失败");
    }

    @PostMapping("/stop")
    public Result<Void> stopTask(Integer id) {
        boolean res = scheduleTaskService.stopCourseSelect(id);
        if (res) {
            return Result.success();
        }
        return Result.error(CodeConstants.ERROR, "失败");
    }

    @GetMapping("/status")
    public Result<Integer> getStatus() {
        return scheduleTaskService.getStatus();
    }
}
