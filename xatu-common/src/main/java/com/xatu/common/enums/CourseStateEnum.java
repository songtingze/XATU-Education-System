package com.xatu.common.enums;

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

    public static SchoolEnum getByCode(int code) {
        for (SchoolEnum t : SchoolEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }

    public static Boolean isIn(int code) {
        for (SchoolEnum t : SchoolEnum.values()) {
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
