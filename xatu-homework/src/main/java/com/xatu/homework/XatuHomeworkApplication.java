package com.xatu.homework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient  //此注解是用于在Eureka注册客户端使用
@EnableScheduling //开启定时任务
public class XatuHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(XatuHomeworkApplication.class, args);
    }

}
