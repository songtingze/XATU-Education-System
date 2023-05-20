package com.xatu.common.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

public enum CourseState {
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

    CourseState(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static School getByCode(int code) {
        for (School t : School.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }

    public static Boolean isIn(int code) {
        for (School t : School.values()) {
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
