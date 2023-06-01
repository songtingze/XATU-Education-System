package com.xatu.common.enums;

import com.xatu.common.domain.EnumResult;

import java.util.ArrayList;
import java.util.List;

public enum WeekEnum {
    MONDAY(1, "星期一"),
    TUESDAY(2, "星期二"),
    WEDNESDAY(3,"星期三"),
    THURSDAY(4,"星期四"),
    FRIDAY(5,"星期五"),
    SATURDAY(6,"星期六"),
    SUNDAY(7,"星期日"),
    ;

    private int code;
    private String desc;

    WeekEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WeekEnum getByCode(int code) {
        for (WeekEnum t : WeekEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }
    public static int getByDesc(String desc) {
        for (WeekEnum t : WeekEnum.values()) {
            if (t.getDesc().equalsIgnoreCase(desc)) {
                return t.getCode();
            }
        }
        return -1;
    }

    public static Boolean isIn(int code) {
        for (WeekEnum t : WeekEnum.values()) {
            if (t.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    public static List<EnumResult> getAllWeeks(){
        List<EnumResult> weeks = new ArrayList<>();
        for (WeekEnum t : WeekEnum.values()) {
            weeks.add(new EnumResult(t.code,t.desc));
        }
        return weeks;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
