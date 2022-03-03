package com.flycloud.fly.common.core.exception.assertion;

import cn.hutool.core.util.ArrayUtil;
import com.flycloud.fly.common.core.constant.enums.IResponseCodeEnum;
import com.flycloud.fly.common.core.exception.ArgumentException;
import com.flycloud.fly.common.core.exception.BaseException;

import java.text.MessageFormat;

/**
 * 参数异常断言接口
 * @author fy
 * @date 2022/2/25
 **/

public interface ArgumentExceptionAssert extends IResponseCodeEnum, Assert{

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
