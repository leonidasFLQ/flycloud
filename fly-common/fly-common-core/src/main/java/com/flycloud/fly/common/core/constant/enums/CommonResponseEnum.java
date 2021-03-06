package com.flycloud.fly.common.core.constant.enums;

import com.flycloud.fly.common.core.exception.assertion.CommonExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用异常错误码和错误信息枚举类
 * @author fly
 * @date 2022/2/25
 **/

@Getter
@AllArgsConstructor
public enum CommonResponseEnum implements CommonExceptionAssert {

	/**
	 * 服务器繁忙，请稍后重试
	 */
	SERVER_BUSY(-502, "服务器繁忙，请稍后重试"),
	/**
	 * 服务器异常，无法识别的异常，尽可能对通过判断减少未定义异常抛出
	 */
	SERVER_ERROR(-501, "网络异常"),

	REQUEST_HEADER_CLIRNTID_IS_NULL_ERROR(-503, "请求头中client信息为空"),

	DECODE_AUTH_TOKEN_ERROR(-504,"解码auth认证token失败"),

	AUTH_TOKEN_ERROR(-505,"无效的auth认证token"),
	;

	/**
	 * 异常错误码
	 */
	private int code;
	/**
	 * 异常错误信息
	 */
	private String msg;


}
