package com.flycloud.fly.common.core.exception;

import com.flycloud.fly.common.core.exception.assertion.Assert;

/**
 * 只包装了 错误信息 的 {@link RuntimeException}.
 * 用于 {@link Assert} 中用于包装自定义异常信息
 * @author fly
 * @date 2022/2/25
 **/

public class WrapMessageException extends RuntimeException{

	public WrapMessageException(String msg) {
		super(msg);
	}

	public WrapMessageException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
