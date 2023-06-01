package com.xatu.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ScheduleTask {
    @TableId(type = IdType.AUTO)
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
    private Date beginTime;
    /**
     * 选课结束时间
     */
    private Date endTime;
    /**
     * 状态
     */
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
