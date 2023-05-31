package com.xatu.common.enums;

import com.xatu.common.domain.EnumResult;

import java.util.ArrayList;
import java.util.List;

public enum HourPeriodEnum {
    FIRST(1,"08:00-09:50"),
    SECOND(2,"10:10-12:00"),
    THIRD(3,"12:10-14:00"),
    FOURTH(4,"14:10-16:00"),
    FIFTH(5,"16:20-18:10"),
    SIXTH(6,"19:00-20:50"),
    SEVENTH(7,"21:00-21:50"),
    ;

    private int code;
    private String desc;

    HourPeriodEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static HourPeriodEnum getByCode(int code) {
        for (HourPeriodEnum t : HourPeriodEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }
    public static int getByDesc(String desc) {
        for (HourPeriodEnum t : HourPeriodEnum.values()) {
            if (t.getDesc().equalsIgnoreCase(desc)) {
                return t.getCode();
            }
        }
        return -1;
    }

    public static Boolean isIn(int code) {
        for (HourPeriodEnum t : HourPeriodEnum.values()) {
            if (t.getCode()==code) {
                return true;
            }
        }
        return false;
    }

    public static List<EnumResult> getAllHourPeriods(){
        List<EnumResult> hourPeriods = new ArrayList<>();
        for (HourPeriodEnum t : HourPeriodEnum.values()) {
            hourPeriods.add(new EnumResult(t.code,t.desc));
        }
        return hourPeriods;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
