package com.flycloud.fly.common.core.constant;

/**
 * 通用常量类
 * @author fly
 * @date 2022/2/25
 **/

public interface CommonConstants {
	/**
	 * 生产环境标识
	 */
	String ENV_PROD = "prod";
	/**
	 * 测试环境标识
	 */
	String ENV_DEV = "dev";
	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;
	/**
	 * 失败标记
	 */
	Integer FAIL = 1;
	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";
	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";
}
