package com.xatu.common.enums;

public enum CourseAssessmentEnum {

    POINT(0, "百分制"),

    LEVEL(1, "五级制"),

    BINARY(2, "两级制")
    ;

    private int code;
    private String desc;

    CourseAssessmentEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CourseAssessmentEnum getByCode(int code) {
        for (CourseAssessmentEnum t : CourseAssessmentEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }

    public static Boolean isIn(int code) {
        for (CourseAssessmentEnum t : CourseAssessmentEnum.values()) {
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
