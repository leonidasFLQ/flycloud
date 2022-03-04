package com.flycloud.fly.admin.api.vo;

import lombok.Data;

/**
 * @author lengleng
 * @date 2020/2/10
 */
@Data
//@ApiModel(value = "前端角色展示对象")
public class RoleVo {

	/**
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * 菜单列表
	 */
	private String menuIds;

}
