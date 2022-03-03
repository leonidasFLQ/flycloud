package com.flycloud.fly.common.feign.sentinel.parser;

import javax.servlet.http.HttpServletRequest;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;

/**
 * sentinel 请求头解析判断
 * @author fly
 * @date 2022/03/03
 */
public class FlyHeaderRequestOriginParser implements RequestOriginParser {

	/**
	 * 请求头获取allow
	 */
	private static final String ALLOW = "Allow";

	/**
	 * Parse the origin from given HTTP request.
	 * @param request HTTP request
	 * @return parsed origin
	 */
	@Override
	public String parseOrigin(HttpServletRequest request) {
		return request.getHeader(ALLOW);
	}

}
