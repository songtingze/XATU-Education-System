package com.xatu.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xatu.common.enums.CoursePeriodEnum;
import com.xatu.common.enums.ScheduleTaskStatusEnum;
import com.xatu.system.domain.ScheduleTask;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class ScheduleTaskVo {
    private Integer id;
    /**
     * 选课学年
     */
    private String term;
    /**
     * 选课时段
     */
    private Integer period;
    /**
     * 说明
     */
    private String info;
    /**
     * 选课开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;
    /**
     * 选课结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    /**
     * 状态
     */
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private String periodStr;

    private String statusStr;

    public ScheduleTaskVo(ScheduleTask scheduleTask) {
        BeanUtils.copyProperties(scheduleTask, this);
        this.periodStr = CoursePeriodEnum.getByCode(scheduleTask.getPeriod()).getDesc();
        this.statusStr = ScheduleTaskStatusEnum.getByCode(scheduleTask.getStatus()).getDesc();
    }
}
