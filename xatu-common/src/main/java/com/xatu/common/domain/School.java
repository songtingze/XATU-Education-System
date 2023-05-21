package com.xatu.common.domain;

import lombok.Data;

@Data
public class School {
    private int code;
    private String desc;

    public School(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
