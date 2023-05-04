package com.xatu.system.service.serviceImpl;

import com.xatu.system.Dao.SysDao;
import com.xatu.system.domain.Sys;
import com.xatu.system.service.SysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysServiceImpl implements SysService {
    @Resource
    private SysDao sysDao;

    @Override
    public Sys findSysByNumber(int number) {
        return sysDao.findSysByNumber(number);
    }

    @Override
    public Sys loginService(int number, String password) {
        Sys sys = sysDao.findSysByNumberAndPassword(number,password);
        // 重要信息置空
        if(sys != null) {
            sys.setPassword("");
        }
        return sys;
    }
}
