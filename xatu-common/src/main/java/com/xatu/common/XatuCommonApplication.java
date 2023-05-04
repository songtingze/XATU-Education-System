package com.xatu.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //此注解是用于在Eureka注册客户端使用
public class XatuCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(XatuCommonApplication.class, args);
    }

}
