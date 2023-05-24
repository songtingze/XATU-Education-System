package com.xatu.common.domain;

import lombok.Data;

@Data
public class EnumResult {
    private int code;
    private String desc;

    public EnumResult(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
