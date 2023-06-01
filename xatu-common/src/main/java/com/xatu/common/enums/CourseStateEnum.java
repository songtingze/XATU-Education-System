package com.xatu.common.enums;

import com.xatu.common.domain.EnumResult;

import java.util.ArrayList;
import java.util.List;

public enum CourseStateEnum {
    /**
     * 选课进行中
     */
    SELECTING(0, "选课进行中"),
    /**
     * 初始状态
     */
    INIT(1, "初始状态");

    private int code;
    private String desc;

    CourseStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CourseStateEnum getByCode(int code) {
        for (CourseStateEnum t : CourseStateEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }
    public static int getByDesc(String desc) {
        for (CourseStateEnum t : CourseStateEnum.values()) {
            if (t.getDesc().equalsIgnoreCase(desc)) {
                return t.getCode();
            }
        }
        return -1;
    }

    public static List<EnumResult> getAllCourseStatus(){
        List<EnumResult> courseStatus = new ArrayList<>();
        for (CourseStateEnum t : CourseStateEnum.values()) {
            courseStatus.add(new EnumResult(t.code,t.desc));
        }
        return courseStatus;
    }

    public static Boolean isIn(int code) {
        for (CourseStateEnum t : CourseStateEnum.values()) {
            if (t.getCode()==code) {
                return true;
            }
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
