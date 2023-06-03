package com.xatu.common.enums;

import com.xatu.common.domain.EnumResult;

import java.util.ArrayList;
import java.util.List;

public enum HomeworkStateEnum {
    COMMIT(0,"进行"),
    END(1,"截止")
    ;

    private int code;
    private String desc;

    HomeworkStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static HomeworkStateEnum getByCode(int code) {
        for (HomeworkStateEnum t : HomeworkStateEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }
    public static int getByDesc(String desc) {
        for (HomeworkStateEnum t : HomeworkStateEnum.values()) {
            if (t.getDesc().equalsIgnoreCase(desc)) {
                return t.getCode();
            }
        }
        return -1;
    }

    public static Boolean isIn(int code) {
        for (HomeworkStateEnum t : HomeworkStateEnum.values()) {
            if (t.getCode()==code) {
                return true;
            }
        }
        return false;
    }

    public static List<EnumResult> getAllGrades(){
        List<EnumResult> grades = new ArrayList<>();
        for (HomeworkStateEnum t : HomeworkStateEnum.values()) {
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
