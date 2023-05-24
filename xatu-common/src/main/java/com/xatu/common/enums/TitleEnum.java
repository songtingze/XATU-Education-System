package com.xatu.common.enums;

import com.xatu.common.domain.EnumResult;

import java.util.ArrayList;
import java.util.List;

public enum TitleEnum {

    LECTURER(1, "讲师"),
    ASSOCIATE_PROFESSOR(2,"副教授"),
    PROFESSOR(3,"教授"),
    ;

    private int code;
    private String desc;

    TitleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TitleEnum getByCode(int code) {
        for (TitleEnum t : TitleEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }
    public static int getByDesc(String desc) {
        for (TitleEnum t : TitleEnum.values()) {
            if (t.getDesc().equalsIgnoreCase(desc)) {
                return t.getCode();
            }
        }
        return -1;
    }

    public static Boolean isIn(int code) {
        for (TitleEnum t : TitleEnum.values()) {
            if (t.getCode()==code) {
                return true;
            }
        }
        return false;
    }

    public static List<EnumResult> getAllTitles(){
        List<EnumResult> schools = new ArrayList<>();
        for (TitleEnum t : TitleEnum.values()) {
            schools.add(new EnumResult(t.code,t.desc));
        }
        return schools;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
