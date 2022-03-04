package com.flycloud.fly.admin;

import com.flycloud.fly.common.feign.annotation.EnableFlyFeignClients;
import com.flycloud.fly.common.security.annotation.EnableFlyResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lengleng
 * @date 2018年06月21日 用户统一管理系统
 */
//@EnablePigSwagger2
@EnableFlyResourceServer
@EnableFlyFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FlyAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyAdminApplication.class, args);
	}

}
