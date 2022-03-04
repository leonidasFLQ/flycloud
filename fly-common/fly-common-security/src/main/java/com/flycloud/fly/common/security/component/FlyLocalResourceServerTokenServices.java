package com.flycloud.fly.common.security.component;

import com.flycloud.fly.common.core.constant.enums.SecurityResponseEnum;
import com.flycloud.fly.common.core.exception.BaseException;
import com.flycloud.fly.common.security.service.FlyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author lengleng
 * @date 2020/9/29
 */
@RequiredArgsConstructor
public class FlyLocalResourceServerTokenServices implements ResourceServerTokenServices {

	private final TokenStore tokenStore;

	private final UserDetailsService userDetailsService;

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
		OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
		if (oAuth2Authentication == null) {
			return null;
		}

		OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
		if (!(oAuth2Authentication.getPrincipal() instanceof FlyUser)) {
			return oAuth2Authentication;
		}

		// 根据 username 查询 spring cache 最新的值 并返回
		FlyUser flyUser = (FlyUser) oAuth2Authentication.getPrincipal();

		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(flyUser.getUsername());
		}
		catch (UsernameNotFoundException notFoundException) {
			throw new BaseException(SecurityResponseEnum.USER_NOT_FOUND.getCode(),SecurityResponseEnum.USER_NOT_FOUND.getMsg());
		}
		Authentication userAuthentication = new UsernamePasswordAuthenticationToken(userDetails, "N/A",
				userDetails.getAuthorities());
		OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
		authentication.setAuthenticated(true);
		return authentication;
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}

}
