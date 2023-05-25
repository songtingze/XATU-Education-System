package com.xatu.common.enums;

/**
 * 开课时段
 */
public enum CoursePeriodEnum {

    SPRING(0x1, "春"),

    AUTUMN(0x2, "秋"),

    SPRING_AND_AUTUMN(0x3, "春秋")
    ;

    private int code;
    private String desc;

    CoursePeriodEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CoursePeriodEnum getByCode(int code) {
        for (CoursePeriodEnum t : CoursePeriodEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }

    public static Boolean isIn(int code) {
        for (CoursePeriodEnum t : CoursePeriodEnum.values()) {
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
