package com.flycloud.fly.auth.handler;

import com.flycloud.fly.admin.api.entity.SysLog;
import com.flycloud.fly.common.core.util.SpringContextHolder;
import com.flycloud.fly.common.log.event.SysLogEvent;
import com.flycloud.fly.common.log.util.LogTypeEnum;
import com.flycloud.fly.common.log.util.SysLogUtils;
import com.flycloud.fly.common.security.handler.AbstractAuthenticationFailureEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@Component
public class FlyAuthenticationFailureEventHandler extends AbstractAuthenticationFailureEventHandler {

	/**
	 * 处理登录失败方法
	 * <p>
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication 登录的authenticationException 对象
	 */
	@Override
	public void handle(AuthenticationException authenticationException, Authentication authentication) {
		log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle("登录失败");
		logVo.setType(LogTypeEnum.ERROR.getType());
		logVo.setException(authenticationException.getMessage());
		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Long endTime = System.currentTimeMillis();
		logVo.setTime(endTime - startTime);
		logVo.setCreateBy(authentication.getName());
		logVo.setUpdateBy(authentication.getName());
		SpringContextHolder.publishEvent(new SysLogEvent(logVo));
	}

}
