package com.xatu.system.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xatu.system.domain.Sys;
import com.xatu.system.mapper.SysMapper;
import com.xatu.system.service.SysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysServiceImpl implements SysService {
    @Resource
    private SysMapper sysMapper;

    @Override
    public Sys findSysByNumber(int number) {
        LambdaQueryWrapper<Sys> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Sys::getNumber, number);
        return sysMapper.selectOne(wrapper);
    }

    @Override
    public Sys loginService(int number, String password) {
        LambdaQueryWrapper<Sys> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Sys::getNumber, number).eq(Sys::getPassword, password);
        Sys sys = sysMapper.selectOne(wrapper);
        // 重要信息置空
        if (sys != null) {
            sys.setPassword("");
        }
        return sys;
    }
}
