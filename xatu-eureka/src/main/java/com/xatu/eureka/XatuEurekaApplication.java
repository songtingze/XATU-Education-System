package com.xatu.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication  //启动类
@EnableEurekaServer     //Eureka注册中心
public class XatuEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(XatuEurekaApplication.class, args);
    }

}
