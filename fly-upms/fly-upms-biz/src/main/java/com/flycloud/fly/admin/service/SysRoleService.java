package com.flycloud.fly.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flycloud.fly.admin.api.entity.SysRole;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
public interface SysRoleService extends IService<SysRole> {

	/**
	 * 通过角色ID，删除角色
	 * @param id
	 * @return
	 */
	Boolean removeRoleById(Integer id);

}
