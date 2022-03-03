package com.flycloud.fly.common.feign.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FlyFeignClientsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义feign客户端注解
 * @author fly
 * @date 2022/03/03
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
@Import(FlyFeignClientsRegistrar.class)
public @interface EnableFlyFeignClients {

    String[] value() default {};

    String[] basePackages() default { "com.flycloud" };

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};

}
