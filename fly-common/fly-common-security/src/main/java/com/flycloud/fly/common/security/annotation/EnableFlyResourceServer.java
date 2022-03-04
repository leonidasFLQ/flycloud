package com.flycloud.fly.common.security.annotation;

import com.flycloud.fly.common.security.component.FlyResourceServerAutoConfiguration;
import com.flycloud.fly.common.security.component.FlyResourceServerTokenRelayAutoConfiguration;
import com.flycloud.fly.common.security.component.FlySecurityBeanDefinitionRegister;
import com.flycloud.fly.common.security.feign.FlyFeignClientConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

@Documented
@Inherited
@EnableResourceServer
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ FlyResourceServerAutoConfiguration.class, FlySecurityBeanDefinitionRegister.class,
        FlyResourceServerTokenRelayAutoConfiguration.class, FlyFeignClientConfiguration.class })
public @interface EnableFlyResourceServer {

}
