package com.xatu.system.service;

import com.xatu.system.domain.Sys;

public interface SysService {
    /**
     *
     * @param number
     * @return
     */
    Sys findSysByNumber(int number);
    Sys loginService(int number,String password);
}
