package com.xatu.evaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient  //此注解是用于在Eureka注册客户端使用
@EnableSwagger2
public class XatuEvaluationApplication {

    public static void main(String[] args) {
        SpringApplication.run(XatuEvaluationApplication.class, args);
    }

}
