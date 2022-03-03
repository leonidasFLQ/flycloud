package com.flycloud.fly.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * fly 服务网关
 * @author fly
 * @date 2022/03/02
 */

@EnableDiscoveryClient
@SpringBootApplication
public class FlyGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlyGatewayApplication.class, args);
    }

}
