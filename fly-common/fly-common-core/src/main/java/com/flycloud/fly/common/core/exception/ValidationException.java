package com.flycloud.fly.common.core.exception;

import com.flycloud.fly.common.core.constant.enums.IResponseCodeEnum;

/**
 * 校验异常
 * 调用接口时，参数格式不合法可以抛出该异常
 * @author fy
 * @date 2022/2/25
 **/

public class ValidationException extends BaseException{

	private static final long serialVersionUID = 1L;

	public ValidationException(IResponseCodeEnum responseCodeEnum, Object[] args, String message) {
		super(responseCodeEnum, args, message);
	}

	public ValidationException(IResponseCodeEnum responseCodeEnum, Object[] args, String message, Throwable cause) {
		super(responseCodeEnum, args, message, cause);
	}
}
