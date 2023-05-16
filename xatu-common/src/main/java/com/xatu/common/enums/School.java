package com.xatu.common.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

public enum School {
    /**
     * 软件学院
     */
    SOFTWARE("SOFTWARE", "软件学院"),
    /**
     * 计算机学院
     */
    COMPUTER("COMPUTER", "计算机学院");

    private String code;
    private String desc;

    School(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static School getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (School t : School.values()) {
                if (t.getCode().equals(code)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static Boolean isIn(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (School t : School.values()) {
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
