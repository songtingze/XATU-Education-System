package com.xatu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class XatuGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XatuGatewayApplication.class, args);
    }

}
