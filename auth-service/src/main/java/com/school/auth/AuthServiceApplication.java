package com.school.auth;

import com.school.auth.config.CustomErrorDecoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import feign.codec.ErrorDecoder;
import feign.Request;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.school.auth.clients")
@EnableJpaRepositories(basePackages = "com.school.auth.repositories")
@EntityScan(basePackages = "com.school.auth.models")
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(5000, 10000);
    }
}
