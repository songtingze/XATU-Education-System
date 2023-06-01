package com.xatu.system.service.serviceImpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.xatu.common.domain.PageQuery;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.ScheduleTaskStatusEnum;
import com.xatu.system.domain.ScheduleTask;
import com.xatu.system.domain.vo.ScheduleTaskVo;
import com.xatu.system.mapper.ScheduleTaskMapper;
import com.xatu.system.mapper.SingleCourseMapper;
import com.xatu.system.service.ScheduleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleTaskServiceImpl.class);

    @Resource
    private ScheduleTaskMapper scheduleTaskMapper;

    @Resource
    private SingleCourseMapper singleCourseMapper;

    @Override
    public PageResult<ScheduleTaskVo> listScheduleTask(String term, Integer period, PageQuery pageQuery) {
        LambdaQueryWrapper<ScheduleTask> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(term)) {
            wrapper.like(ScheduleTask::getTerm, term);
        }
        if (period != null) {
            wrapper.eq(ScheduleTask::getPeriod, period);
        }
        Page<ScheduleTask> pageResult = scheduleTaskMapper.selectPage(pageQuery.build(), wrapper);
        Page<ScheduleTaskVo> voResult = new PageDTO<>(pageResult.getCurrent(), pageResult.getSize());
        List<ScheduleTaskVo> voList = pageResult.getRecords().stream().map(ScheduleTaskVo::new).collect(Collectors.toList());
        voResult.setRecords(voList);
        voResult.setTotal(pageResult.getTotal());
        voResult.setCurrent(pageResult.getCurrent());
        return PageResult.success(voResult);
    }

    @Override
    public boolean addScheduleTask(ScheduleTask task) {
        Date now = new Date();
        task.setCreateTime(now);
        task.setUpdateTime(now);
        task.setStatus(ScheduleTaskStatusEnum.INIT.getCode());
        int res = scheduleTaskMapper.insert(task);
        return res > 0;
    }

    @Override
    public boolean updateScheduleTask(ScheduleTask task) {
        int res = scheduleTaskMapper.updateById(task);
        return res > 0;
    }

    @Override
    public boolean deleteScheduleTask(Integer id) {
        int res = scheduleTaskMapper.deleteById(id);
        return res > 0;
    }

    @Override
    public boolean checkScheduleTask() {
        Date now = new Date();
        LambdaQueryWrapper<ScheduleTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(ScheduleTask::getStatus, ScheduleTaskStatusEnum.FINISHED.getCode());
        List<ScheduleTask> taskList = scheduleTaskMapper.selectList(wrapper);
        for (ScheduleTask task : taskList) {
            int status = task.getStatus();
            if (ScheduleTaskStatusEnum.INIT.getCode() == status) {
                if (DateUtil.compare(now, task.getBeginTime()) >= 0) {
                    task.setStatus(ScheduleTaskStatusEnum.PROCESSING.getCode());
                    int res = scheduleTaskMapper.updateById(task);
                    singleCourseMapper.initCourseRemain();
                    if (res == 1) {
                        logger.info("[选课定时任务][id={}]选课开始", task.getId());
                    } else {
                        logger.info("[选课定时任务][id={}]选课开始失败", task.getId());
                    }
                }
            } else if (ScheduleTaskStatusEnum.PROCESSING.getCode() == status) {
                if (DateUtil.compare(now, task.getEndTime()) >= 0) {
                    task.setStatus(ScheduleTaskStatusEnum.FINISHED.getCode());
                    int res = scheduleTaskMapper.updateById(task);
                    if (res == 1) {
                        logger.info("[选课定时任务][id={}]选课结束", task.getId());
                    } else {
                        logger.info("[选课定时任务][id={}]选课结束失败", task.getId());
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean startCourseSelect(Integer id) {
        ScheduleTask task = scheduleTaskMapper.selectById(id);
        if (!task.getStatus().equals(ScheduleTaskStatusEnum.INIT.getCode())) {
            return false;
        }
        task.setStatus(ScheduleTaskStatusEnum.PROCESSING.getCode());
        int res = scheduleTaskMapper.updateById(task);
        int count = singleCourseMapper.initCourseRemain();
        System.out.println(count);
        return res > 0;
    }

    @Override
    public boolean stopCourseSelect(Integer id) {
        ScheduleTask task = scheduleTaskMapper.selectById(id);
        if (!task.getStatus().equals(ScheduleTaskStatusEnum.PROCESSING.getCode())) {
            return false;
        }
        task.setStatus(ScheduleTaskStatusEnum.FINISHED.getCode());
        int res = scheduleTaskMapper.updateById(task);
        return res > 0;
    }

    @Override
    public Result<Integer> getStatus() {
        LambdaQueryWrapper<ScheduleTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScheduleTask::getStatus, ScheduleTaskStatusEnum.PROCESSING.getCode());
        ScheduleTask entity = scheduleTaskMapper.selectOne(wrapper);
        if (entity == null) {
            return Result.success(0);
        }
        return Result.success(1);
    }
}
