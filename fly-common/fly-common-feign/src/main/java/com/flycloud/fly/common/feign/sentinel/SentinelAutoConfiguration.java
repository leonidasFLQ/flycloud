package com.flycloud.fly.common.feign.sentinel;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.flycloud.fly.common.feign.sentinel.ext.FlySentinelFeign;
import com.flycloud.fly.common.feign.sentinel.ext.FlySentinelFilterConfiguration;
import com.flycloud.fly.common.feign.sentinel.handle.FlyUrlBlockHandler;
import com.flycloud.fly.common.feign.sentinel.parser.FlyHeaderRequestOriginParser;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * sentinel 配置
 * @author fly
 * @date 2022/03/03
 */
@Configuration(proxyBeanMethods = false)
@Import(FlySentinelFilterConfiguration.class)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class SentinelAutoConfiguration {

	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return FlySentinelFeign.builder();
	}

	@Bean
	@ConditionalOnMissingBean
	public BlockExceptionHandler blockExceptionHandler() {
		return new FlyUrlBlockHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public RequestOriginParser requestOriginParser() {
		return new FlyHeaderRequestOriginParser();
	}

}
