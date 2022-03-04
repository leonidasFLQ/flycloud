package com.flycloud.fly.admin.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flycloud.fly.admin.api.entity.SysDept;

import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
public interface SysDeptService extends IService<SysDept> {

	/**
	 * 查询部门树菜单
	 * @return 树
	 */
	List<Tree<Integer>> listDeptTrees();

	/**
	 * 查询用户部门树
	 * @return
	 */
	List<Tree<Integer>> listCurrentUserDeptTrees();

	/**
	 * 添加信息部门
	 * @param sysDept
	 * @return
	 */
	Boolean saveDept(SysDept sysDept);

	/**
	 * 删除部门
	 * @param id 部门 ID
	 * @return 成功、失败
	 */
	Boolean removeDeptById(Integer id);

	/**
	 * 更新部门
	 * @param sysDept 部门信息
	 * @return 成功、失败
	 */
	Boolean updateDeptById(SysDept sysDept);

	/**
	 * 查找指定部门的子部门id列表
	 * @param deptId 部门id
	 * @return List<Integer>
	 */
	List<Integer> listChildDeptId(Integer deptId);

}
