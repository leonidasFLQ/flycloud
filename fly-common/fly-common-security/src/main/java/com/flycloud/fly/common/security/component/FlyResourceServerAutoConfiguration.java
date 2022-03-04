package com.flycloud.fly.common.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author fly
 * @date 2022/03/03
 */
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class FlyResourceServerAutoConfiguration {

	@Bean("pms")
	public PermissionService permissionService() {
		return new PermissionService();
	}

	@Bean
	public FlyAccessDeniedHandler flyAccessDeniedHandler(ObjectMapper objectMapper) {
		return new FlyAccessDeniedHandler(objectMapper);
	}

	@Bean
	public FlyBearerTokenExtractor flyBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
		return new FlyBearerTokenExtractor(urlProperties);
	}

	@Bean
	public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper) {
		return new ResourceAuthExceptionEntryPoint(objectMapper);
	}

	@Bean
	@Primary
	public ResourceServerTokenServices resourceServerTokenServices(TokenStore tokenStore,
			UserDetailsService userDetailsService) {
		return new FlyLocalResourceServerTokenServices(tokenStore, userDetailsService);
	}

}
