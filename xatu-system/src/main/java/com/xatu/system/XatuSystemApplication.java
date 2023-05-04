package com.xatu.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //此注解是用于在Eureka注册客户端使用
public class XatuSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(XatuSystemApplication.class, args);
    }

}
