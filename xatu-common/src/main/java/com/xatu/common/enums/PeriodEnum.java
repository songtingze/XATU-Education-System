package com.xatu.common.enums;

import com.xatu.common.domain.EnumResult;

import java.util.ArrayList;
import java.util.List;

public enum PeriodEnum {
    SPRING(1, "春"),
    FALL(2,"秋"),
    SPRING_FALL(3,"春秋"),
    ;

    private int code;
    private String desc;

    PeriodEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PeriodEnum getByCode(int code) {
        for (PeriodEnum t : PeriodEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }
    public static int getByDesc(String desc) {
        for (PeriodEnum t : PeriodEnum.values()) {
            if (t.getDesc().equalsIgnoreCase(desc)) {
                return t.getCode();
            }
        }
        return -1;
    }

    public static Boolean isIn(int code) {
        for (PeriodEnum t : PeriodEnum.values()) {
            if (t.getCode()==code) {
                return true;
            }
        }
        return false;
    }

    public static List<EnumResult> getAllPeriods(){
        List<EnumResult> periods = new ArrayList<>();
        for (PeriodEnum t : PeriodEnum.values()) {
            periods.add(new EnumResult(t.code,t.desc));
        }
        return periods;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
