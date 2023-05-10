package com.xatu.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.xatu"})
@EnableEurekaClient  //此注解是用于在Eureka注册客户端使用
//@ComponentScan()
public class XatuSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(XatuSystemApplication.class, args);
    }

}
