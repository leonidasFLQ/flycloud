package com.flycloud.fly.common.core.constant.enums;

/**
 * 异常返回码枚举接口
 * @author fy
 * @date 2022/2/25
 */

public abstract interface IResponseCodeEnum {

	public abstract int getCode();

	public abstract String getMsg();

}
