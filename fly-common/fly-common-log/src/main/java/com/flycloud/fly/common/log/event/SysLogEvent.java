package com.flycloud.fly.common.log.event;

import com.flycloud.fly.admin.api.entity.SysLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author lengleng 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SysLog source) {
		super(source);
	}

}
