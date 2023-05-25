package com.xatu.common.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.xatu.common.enums.CoursePeriodEnum;

import java.util.Date;

/**
 * 课程工具类
 */
public class CourseUtil {
    /**
     * 获取指定日期为秋季学期还是春季学期
     * @param date 日期
     * @return 春季学期或秋季学期
     */
    public static CoursePeriodEnum getCoursePeriodByDate(Date date) {
        DateTime date1 = DateUtil.parseDate("2022-03-01");
        date1.setField(DateField.YEAR, DateUtil.year(date));

        DateTime date2 = DateUtil.parseDate("2022-09-01");
        date2.setField(DateField.YEAR, DateUtil.year(date));

        if (date.after(date1) && date.before(date2)) {
            return CoursePeriodEnum.SPRING;
        } else {
            return CoursePeriodEnum.AUTUMN;
        }
    }

    /**
     * 获取指定日期是第几学年
     * @param enrollmentTime 入学日期
     * @param now 指定日期
     * @return 第几学年，1-4
     */
    public static int getGradeYear(Date enrollmentTime, Date now) {
        return (int) DateUtil.betweenYear(enrollmentTime, now, false) + 1;
    }
}
