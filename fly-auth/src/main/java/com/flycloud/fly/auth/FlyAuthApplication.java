package com.flycloud.fly.auth;

import com.flycloud.fly.common.feign.annotation.EnableFlyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableFlyFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FlyAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlyAuthApplication.class, args);
    }

}
