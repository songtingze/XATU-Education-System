package com.xatu.common.enums;

import com.xatu.common.domain.School;

import java.util.ArrayList;
import java.util.List;

public enum SchoolEnum {

    /**
     * 电子信息工程学院
     */
    ELECTRONIC_AND_INFORMATION_ENGINEERING(0, "电子信息工程学院"),
    /**
     * 计算机与信息技术学院
     */
    COMPUTER_AND_INFORMATION_TECHNOLOGY(1, "计算机与信息技术学院"),
    /**
     * 经理管理学院
     */
    ECONOMICS_AND_MANAGEMENT(2, "经理管理学院"),
    /**
     * 交通运输学院
     */
    TRAFFIC_AND_TRANSPORTATION(3, "交通运输学院"),
    /**
     * 土木建筑工程学院
     */
    CIVIL_ENGINEERING(4, "土木建筑工程学院"),
    /**
     * 机械与电子控制工程学院
     */
    MECHANICAL_ELECTRONIC_AND_CONTROL_ENGINEERING(5, "机械与电子控制工程学院"),
    /**
     * 电气工程学院
     */
    ELECTRONIC_ENGINEERING(6, "电气工程学院"),
    /**
     * 数学与统计学院
     */
    MATHEMATICS_AND_STATISTICS(7, "数学与统计学院"),
    /**
     * 物理科学与工程学院
     */
    PHYSICAL_SCIENCE_AND_ENGINEERING(8, "物理科学与工程学院"),
    /**
     * 马克思主义学院
     */
    MARXISM(9, "马克思主义学院"),
    /**
     * 语言与传播学院
     */
    LANGUAGES_AND_COMMUNICATION_STUDIES(10, "语言与传播学院"),
    /**
     * 软件学院
     */
    SOFTWARE_ENGINEERING(11, "软件学院"),
    /**
     * 建筑与艺术学院
     */
    ARCHITECTURE_AND_DESIGN(12, "建筑与艺术学院"),
    /**
     * 法学院
     */
    LAW(13, "法学院"),
    /**
     * 环境学院
     */
    ENVIRONMENT(14, "环境学院"),
    /**
     * 国家保密学院
     */
    STATE_SECRETS_PROTECTION(15, "国家保密学院"),
    /**
     * 詹天佑学院
     */
    JEME_TIENYOW_HONORS(16, "詹天佑学院"),
    /**
     * 远程与继续教育学院
     */
    DISTANCE_LEARNING_AND_CONTINUING_EDUCATION(17, "远程与继续教育学院"),
    ;



    private int code;
    private String desc;

    SchoolEnum(int code, String desc) {
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
    public static int getByDesc(String desc) {
        for (SchoolEnum t : SchoolEnum.values()) {
            if (t.getDesc().equalsIgnoreCase(desc)) {
                return t.getCode();
            }
        }
        return -1;
    }

    public static Boolean isIn(int code) {
        for (SchoolEnum t : SchoolEnum.values()) {
            if (t.getCode()==code) {
                return true;
            }
        }
        return false;
    }

    public static List<School> getAllSchools(){
        List<School> schools = new ArrayList<>();
        for (SchoolEnum t : SchoolEnum.values()) {
            schools.add(new School(t.code,t.desc));
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
