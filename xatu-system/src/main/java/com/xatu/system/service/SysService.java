package com.xatu.system.service;

import com.xatu.system.domain.Sys;

public interface SysService {
    Sys findSysByNumber(int number);
    Sys loginService(int number,String password);
}
