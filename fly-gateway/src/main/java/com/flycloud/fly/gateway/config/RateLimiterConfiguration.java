package com.flycloud.fly.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 路由限流配置（多维度限流）
 * 只需在配置文件spring.cloud.gateway.routes[0].filters[0].args.key-resolver做相应配置即可
 * @author fly
 * @date 2022/03/03
 */
@Configuration(proxyBeanMethods = false)
public class RateLimiterConfiguration {

	/**
	 * IP地址维度限流 Remote Host addr key resolver.
	 * 用于限流的键的解析器的 Bean 对象的名字。配置文件中使用 SpEL表达式根据#{@beanName}从 Spring 容器中获取 Bean 对象。
	 * @link {https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#the-requestratelimiter-gatewayfilter-factory}
	 */
	@Bean
	@Primary
	public KeyResolver remoteAddrKeyResolver() {
		return exchange -> Mono
				.just(Objects.requireNonNull(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()))
						.getAddress().getHostAddress());
	}

	/**
	 * 请求路径维度限流 URI key resolver.
	 */
	@Bean
	public KeyResolver uriKeyResolver() {
		return exchange -> Mono
				.just(Objects.requireNonNull(Objects.requireNonNull(exchange.getRequest().getURI().getPath())));
	}

	/**
	 * 用户维度限流
	 * User key resolver.
	 */
	@Bean
	public KeyResolver userKeyResolver() {
		return exchange -> Mono
				.just(Objects.requireNonNull(Objects.requireNonNull(exchange.getRequest().getQueryParams().getFirst("user"))));
	}

}
