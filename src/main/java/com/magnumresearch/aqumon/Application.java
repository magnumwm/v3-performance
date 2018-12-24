package com.magnumresearch.aqumon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EntityScan(basePackages = "com.magnumresearch.aqumon.performance")
@ComponentScan(basePackages = { "com.magnumresearch.aqumon.common.feign",
        "com.magnumresearch.aqumon.performance", "com.magnumresearch.aqumon.common.config" })
public class Application {

    public static void main(String[] args) { SpringApplication.run(Application.class, args); }
}

