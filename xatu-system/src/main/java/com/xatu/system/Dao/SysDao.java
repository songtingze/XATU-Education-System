package com.xatu.system.Dao;

import com.xatu.system.domain.Sys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDao extends JpaRepository<Sys, Long> {
    Sys findSysByNumber(int number);
    Sys findSysByNumberAndPassword(int number,String password);
}
