package com.xatu.common.enums;

public enum ScheduleTaskStatusEnum {
    INIT(0, "未开始"),
    PROCESSING(1, "进行中"),
    FINISHED(2, "已结束")
    ;

    private int code;
    private String desc;

    ScheduleTaskStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ScheduleTaskStatusEnum getByCode(int code) {
        for (ScheduleTaskStatusEnum t : ScheduleTaskStatusEnum.values()) {
            if (t.getCode() == code) {
                return t;
            }
        }
        return null;
    }

    public static Boolean isIn(int code) {
        for (ScheduleTaskStatusEnum t : ScheduleTaskStatusEnum.values()) {
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
