package com.xatu.system.domain.vo;

import com.xatu.system.domain.Sys;
import lombok.Data;

@Data
public class SysVo extends Sys {
    private String userToken;

    public SysVo(Sys sys){
        this.setId(sys.getId());
        this.setNumber(sys.getNumber());
        this.setName(sys.getName());
        this.setPassword(sys.getPassword());
    }
}
