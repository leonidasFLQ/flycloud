package com.flycloud.fly.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FlyGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlyGatewayApplication.class, args);
    }

}
