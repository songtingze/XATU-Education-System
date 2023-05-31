package com.xatu.common.enums;

import com.xatu.common.domain.EnumResult;

import java.util.ArrayList;
import java.util.List;

public enum GradeEnum {
    FRESHMAN(0, "大一"),
    SOPHOMORE(1,"大二"),
    JUNIOR(2,"大三"),
    SENIOR(3, "大四"),
    FIFTH_GRADE(4,"大五"),
    ;

    private int code;
    private String desc;

    GradeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static GradeEnum getByCode(int code) {
        for (GradeEnum t : GradeEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }
    public static int getByDesc(String desc) {
        for (GradeEnum t : GradeEnum.values()) {
            if (t.getDesc().equalsIgnoreCase(desc)) {
                return t.getCode();
            }
        }
        return -1;
    }

    public static Boolean isIn(int code) {
        for (GradeEnum t : GradeEnum.values()) {
            if (t.getCode()==code) {
                return true;
            }
        }
        return false;
    }

    public static List<EnumResult> getAllGrades(){
        List<EnumResult> grades = new ArrayList<>();
        for (GradeEnum t : GradeEnum.values()) {
            grades.add(new EnumResult(t.code,t.desc));
        }
        return grades;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
