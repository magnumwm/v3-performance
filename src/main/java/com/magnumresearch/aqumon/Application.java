package com.magnumresearch.aqumon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.HandlerExceptionResolver;

@EnableFeignClients
@SpringBootApplication
@EntityScan(basePackages = "com.magnumresearch.aqumon.performance")
@ComponentScan(basePackages = { "com.magnumresearch.aqumon.common.feign", "com.magnumresearch.aqumon.performance",
        "com.magnumresearch.aqumon.algo", "com.magnumresearch.aqumon.notification",
        "com.magnumresearch.aqumon.common.config" })
public class Application {

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new io.sentry.spring.SentryExceptionResolver();
    }

    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new io.sentry.spring.SentryServletContextInitializer();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

