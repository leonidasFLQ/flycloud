package com.flycloud.fly.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flycloud.fly.admin.api.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 * @param roleId 角色ID
	 * @return
	 */
	Set<SysMenu> listMenusByRoleId(Integer roleIds);

}
