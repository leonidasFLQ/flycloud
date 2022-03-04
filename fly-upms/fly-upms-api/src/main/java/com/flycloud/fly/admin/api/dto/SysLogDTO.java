package com.flycloud.fly.admin.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日志查询传输对象
 * @author fly
 * @date 2022/03/03
 */
@Data
//@ApiModel(value = "日志查询对象")
public class SysLogDTO {

	/**
	 * 查询日志类型
	 */
	//@ApiModelProperty(value = "日志类型")
	private String type;

	/**
	 * 创建时间区间 [开始时间，结束时间]
	 */
	//@ApiModelProperty(value = "创建时间区间")
	private LocalDateTime[] createTime;

}
