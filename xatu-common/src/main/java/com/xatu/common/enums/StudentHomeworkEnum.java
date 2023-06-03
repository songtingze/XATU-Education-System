package com.xatu.common.enums;

import com.xatu.common.domain.EnumResult;

import java.util.ArrayList;
import java.util.List;

public enum StudentHomeworkEnum {
    NOTCOMMITTED(0,"未提交"),
    COMMITTED(1,"已提交")
    ;

    private int code;
    private String desc;

    StudentHomeworkEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static StudentHomeworkEnum getByCode(int code) {
        for (StudentHomeworkEnum t : StudentHomeworkEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }
    public static int getByDesc(String desc) {
        for (StudentHomeworkEnum t : StudentHomeworkEnum.values()) {
            if (t.getDesc().equalsIgnoreCase(desc)) {
                return t.getCode();
            }
        }
        return -1;
    }

    public static Boolean isIn(int code) {
        for (StudentHomeworkEnum t : StudentHomeworkEnum.values()) {
            if (t.getCode()==code) {
                return true;
            }
        }
        return false;
    }

    public static List<EnumResult> getAllGrades(){
        List<EnumResult> grades = new ArrayList<>();
        for (StudentHomeworkEnum t : StudentHomeworkEnum.values()) {
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
