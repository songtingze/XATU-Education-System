package com.xatu.common.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

public enum CourseState {
    /**
     * 选课进行中
     */
    SELECTING("SELECTING", "选课进行中"),
    /**
     * 初始状态
     */
    INIT("INIT", "初始状态");

    private String code;
    private String desc;

    CourseState(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CourseState getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (CourseState t : CourseState.values()) {
                if (t.getCode().equals(code)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static Boolean isIn(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (CourseState t : CourseState.values()) {
                if (t.getCode().equals(code)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
