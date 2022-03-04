package com.flycloud.fly.admin.service;


import com.flycloud.fly.common.core.util.R;

/**
 * @author lengleng
 * @date 2018/11/14
 */
public interface AppService {

	/**
	 * 发送手机验证码
	 * @param mobile mobile
	 * @return code
	 */
	R<Boolean> sendSmsCode(String mobile);

}
