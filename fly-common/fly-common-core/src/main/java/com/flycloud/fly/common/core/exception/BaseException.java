package com.flycloud.fly.common.core.exception;

import com.flycloud.fly.common.core.constant.enums.IResponseCodeEnum;
import lombok.Getter;

/**
 * 基础异常类，系统其他所有自定义异常类都需要继承本类
 * @author fy
 * @date 2022/2/25
 **/

public class BaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * 异常响应码
	 */
	@Getter
	protected IResponseCodeEnum responseCodeEnum;

	/**
	 * 异常消息
	 */
	@Getter
	protected Object[] msgArgs;

	public BaseException(IResponseCodeEnum responseCodeEnum) {
		super(responseCodeEnum.getMsg());
		this.responseCodeEnum = responseCodeEnum;
	}

	public BaseException(int code, String msg) {
		super(msg);
		this.responseCodeEnum = new IResponseCodeEnum() {
			@Override
			public int getCode() {
				return code;
			}
			@Override
			public String getMsg() {
				return msg;
			}
		};
	}

	public BaseException(IResponseCodeEnum responseCodeEnum, Object[] args, String message) {
		super(message);
		this.responseCodeEnum = responseCodeEnum;
		this.msgArgs = args;
	}

	public BaseException(IResponseCodeEnum responseCodeEnum, Object[] args, String message, Throwable cause) {
		super(message, cause);
		this.responseCodeEnum = responseCodeEnum;
		this.msgArgs = args;
	}

}
