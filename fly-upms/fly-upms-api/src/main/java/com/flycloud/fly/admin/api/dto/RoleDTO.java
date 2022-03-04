package com.flycloud.fly.admin.api.dto;

import com.flycloud.fly.admin.api.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色Dto
 * @author fly
 * @date 2022/03/03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends SysRole {

	/**
	 * 角色部门Id
	 */
	private Integer roleDeptId;

	/**
	 * 部门名称
	 */
	private String deptName;

}
