package com.flycloud.fly.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flycloud.fly.admin.api.dto.UserDTO;
import com.flycloud.fly.admin.api.dto.UserInfo;
import com.flycloud.fly.admin.api.entity.SysUser;
import com.flycloud.fly.admin.api.vo.UserExcelVO;
import com.flycloud.fly.admin.api.vo.UserVO;

import java.util.List;
import java.util.Set;

/**
 * @author lengleng
 * @date 2019/2/1
 */
public interface SysUserService extends IService<SysUser> {

	/**
	 * 查询用户信息
	 * @param sysUser 用户
	 * @return userInfo
	 */
	UserInfo getUserInfo(SysUser sysUser);

	/**
	 * 分页查询用户信息（含有角色信息）
	 * @param page 分页对象
	 * @param userDTO 参数列表
	 * @return
	 */
	IPage getUserWithRolePage(Page page, UserDTO userDTO);

	/**
	 * 删除用户
	 * @param sysUser 用户
	 * @return boolean
	 */
	Boolean removeUserById(SysUser sysUser);

	/**
	 * 更新当前用户基本信息
	 * @param userDto 用户信息
	 * @return Boolean 操作成功返回true,操作失败返回false
	 */
	Boolean updateUserInfo(UserDTO userDto);

	/**
	 * 更新指定用户信息
	 * @param userDto 用户信息
	 * @return
	 */
	Boolean updateUser(UserDTO userDto);

	/**
	 * 通过ID查询用户信息
	 * @param id 用户ID
	 * @return 用户信息
	 */
	UserVO getUserVoById(Integer id);

	/**
	 * 查询上级部门的用户信息
	 * @param username 用户名
	 * @return R
	 */
	List<SysUser> listAncestorUsersByUsername(String username);

	/**
	 * 保存用户信息
	 * @param userDto DTO 对象
	 * @return success/fail
	 */
	Boolean saveUser(UserDTO userDto);

	/**
	 * 查询全部的用户
	 * @param userDTO 查询条件
	 * @return list
	 */
	List<UserExcelVO> listUser(UserDTO userDTO);

	/**
	 * excel 导入用户
	 * @param excelVOList excel 列表数据
	 * @param bindingResult 错误数据
	 * @return ok fail
	 */
	//R importUser(List<UserExcelVO> excelVOList, BindingResult bindingResult);

	/**
	 * 根据部门 id 列表查询对应的用户 id 集合
	 * @param deptIds 部门 id 列表
	 * @return userIdList
	 */
	List<Integer> listUserIdByDeptIds(Set<Integer> deptIds);

}
