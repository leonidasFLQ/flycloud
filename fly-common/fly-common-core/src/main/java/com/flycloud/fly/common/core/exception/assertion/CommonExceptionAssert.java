package com.flycloud.fly.common.core.exception.assertion;

import com.flycloud.fly.common.core.constant.enums.IResponseCodeEnum;
import com.flycloud.fly.common.core.exception.ArgumentException;
import com.flycloud.fly.common.core.exception.BaseException;
import cn.hutool.core.util.ArrayUtil;

import java.text.MessageFormat;

/**
 * 通用异常断言接口
 * @author fly
 * @date 2022/2/25
 **/

public interface CommonExceptionAssert extends IResponseCodeEnum, Assert{

	@Override
	default BaseException newException(Object... args) {
		String msg = this.getMsg();
		if (ArrayUtil.isNotEmpty(args)) {
			msg = MessageFormat.format(this.getMsg(), args);
		}

		return new ArgumentException(this, args, msg);
	}

	@Override
	default BaseException newException(Throwable t, Object... args) {
		String msg = this.getMsg();
		if (ArrayUtil.isNotEmpty(args)) {
			msg = MessageFormat.format(this.getMsg(), args);
		}

		return new ArgumentException(this, args, msg, t);
	}

}
