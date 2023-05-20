package com.xatu.common.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

public enum School {
    /**
     * 软件学院
     */
    SOFTWARE(0, "软件学院"),
    /**
     * 计算机学院
     */
    COMPUTER(1, "计算机学院");

    private int code;
    private String desc;

    School(int code, String desc) {
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
