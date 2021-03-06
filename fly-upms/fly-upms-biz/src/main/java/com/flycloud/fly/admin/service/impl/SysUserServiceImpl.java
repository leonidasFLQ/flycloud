package com.flycloud.fly.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flycloud.fly.admin.api.dto.UserDTO;
import com.flycloud.fly.admin.api.dto.UserInfo;
import com.flycloud.fly.admin.api.entity.*;
import com.flycloud.fly.admin.api.vo.UserExcelVO;
import com.flycloud.fly.admin.api.vo.UserVO;
import com.flycloud.fly.admin.mapper.SysDeptMapper;
import com.flycloud.fly.admin.mapper.SysRoleMapper;
import com.flycloud.fly.admin.mapper.SysUserMapper;
import com.flycloud.fly.admin.mapper.SysUserRoleMapper;
import com.flycloud.fly.admin.service.SysMenuService;
import com.flycloud.fly.admin.service.SysUserService;
import com.flycloud.fly.common.core.constant.CacheConstants;
import com.flycloud.fly.common.core.constant.CommonConstants;
import com.flycloud.fly.common.core.constant.enums.MenuTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	private final SysRoleMapper sysRoleMapper;

	private final SysDeptMapper sysDeptMapper;

	private final SysMenuService sysMenuService;

	private final SysUserRoleMapper sysUserRoleMapper;

	/**
	 * ??????????????????
	 * @param userDto DTO ??????
	 * @return success/fail
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		baseMapper.insert(sysUser);
		userDto.getRole().stream().map(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getUserId());
			userRole.setRoleId(roleId);
			return userRole;
		}).forEach(sysUserRoleMapper::insert);
		return Boolean.TRUE;
	}

	/**
	 * ??????????????????????????????
	 * @param sysUser ??????
	 * @return
	 */
	@Override
	public UserInfo getUserInfo(SysUser sysUser) {
		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);
		// ??????????????????
		List<SysRole> roleList = sysRoleMapper.listRolesByUserId(sysUser.getUserId());
		userInfo.setRoleList(roleList);
		// ?????????????????? ???ID???
		List<Integer> roleIds = roleList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
		userInfo.setRoles(ArrayUtil.toArray(roleIds, Integer.class));
		// ?????????????????????menu.permission???
		Set<String> permissions = roleIds.stream().map(sysMenuService::findMenuByRoleId).flatMap(Collection::stream)
				.filter(m -> MenuTypeEnum.BUTTON.getType().equals(m.getType())).map(SysMenu::getPermission)
				.filter(StrUtil::isNotBlank).collect(Collectors.toSet());
		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));

		return userInfo;
	}

	/**
	 * ????????????????????????????????????????????????
	 * @param page ????????????
	 * @param userDTO ????????????
	 * @return
	 */
	@Override
	public IPage getUserWithRolePage(Page page, UserDTO userDTO) {
		return baseMapper.getUserVosPage(page, userDTO);
	}

	/**
	 * ??????ID??????????????????
	 * @param id ??????ID
	 * @return ????????????
	 */
	@Override
	public UserVO getUserVoById(Integer id) {
		return baseMapper.getUserVoById(id);
	}

	/**
	 * ????????????
	 * @param sysUser ??????
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
	public Boolean removeUserById(SysUser sysUser) {
		sysUserRoleMapper.deleteByUserId(sysUser.getUserId());
		this.removeById(sysUser.getUserId());
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public Boolean updateUserInfo(UserDTO userDto) {
		UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());

		Assert.isTrue(ENCODER.matches(userDto.getPassword(), userVO.getPassword()), "??????????????????????????????");

		SysUser sysUser = new SysUser();
		if (StrUtil.isNotBlank(userDto.getNewpassword1())) {
			sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
		}
		sysUser.setPhone(userDto.getPhone());
		sysUser.setUserId(userVO.getUserId());
		sysUser.setAvatar(userDto.getAvatar());
		return this.updateById(sysUser);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public Boolean updateUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setUpdateTime(LocalDateTime.now());

		if (StrUtil.isNotBlank(userDto.getPassword())) {
			sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		}
		this.updateById(sysUser);

		sysUserRoleMapper
				.delete(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, userDto.getUserId()));
		userDto.getRole().forEach(roleId -> {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getUserId());
			userRole.setRoleId(roleId);
			userRole.insert();
		});
		return Boolean.TRUE;
	}

	/**
	 * ?????????????????????????????????
	 * @param username ?????????
	 * @return R
	 */
	@Override
	public List<SysUser> listAncestorUsersByUsername(String username) {
		SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));

		SysDept sysDept = sysDeptMapper.selectById(sysUser.getDeptId());
		if (sysDept == null) {
			return null;
		}

		Integer parentId = sysDept.getParentId();
		return this.list(Wrappers.<SysUser>query().lambda().eq(SysUser::getDeptId, parentId));
	}

	/**
	 * ?????????????????????
	 * @param userDTO ????????????
	 * @return list
	 */
	@Override
	public List<UserExcelVO> listUser(UserDTO userDTO) {
		List<UserVO> voList = baseMapper.selectVoList(userDTO);
		// ?????????execl ????????????
		List<UserExcelVO> userExcelVOList = voList.stream().map(userVO -> {
			UserExcelVO excelVO = new UserExcelVO();
			BeanUtils.copyProperties(userVO, excelVO);
			String roleNameList = userVO.getRoleList().stream().map(SysRole::getRoleName)
					.collect(Collectors.joining(StrUtil.COMMA));
			excelVO.setRoleNameList(roleNameList);
			return excelVO;
		}).collect(Collectors.toList());
		return userExcelVOList;
	}

//	/**
//	 * excel ????????????, ??????????????? ?????????????????????
//	 * @param excelVOList excel ????????????
//	 * @param bindingResult ????????????
//	 * @return ok fail
//	 */
//	@Override
//	public R importUser(List<UserExcelVO> excelVOList, BindingResult bindingResult) {
//		// ?????????????????????????????????
//		List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
//
//		// ?????????????????????
//		List<SysUser> userList = this.list();
//		List<SysDept> deptList = sysDeptMapper.selectList(Wrappers.emptyWrapper());
//		List<SysRole> roleList = sysRoleMapper.selectList(Wrappers.emptyWrapper());
//
//		// ???????????????????????? ?????? UserDto
//		for (int i = 0; i < excelVOList.size(); i++) {
//			UserExcelVO excel = excelVOList.get(i);
//			Set<String> errorMsg = new HashSet<>();
//			// ???????????????????????????
//			boolean exsitUserName = userList.stream()
//					.anyMatch(sysUser -> excel.getUsername().equals(sysUser.getUsername()));
//
//			if (exsitUserName) {
//				errorMsg.add(String.format("%s ??????????????????", excel.getUsername()));
//			}
//
//			// ?????????????????????????????????????????????
//			Optional<SysDept> deptOptional = deptList.stream()
//					.filter(dept -> excel.getDeptName().equals(dept.getName())).findFirst();
//			if (!deptOptional.isPresent()) {
//				errorMsg.add(String.format("%s ?????????????????????", excel.getDeptName()));
//			}
//
//			// ?????????????????????????????????????????????
//			List<String> roleNameList = StrUtil.split(excel.getRoleNameList(), StrUtil.COMMA);
//			List<SysRole> roleCollList = roleList.stream()
//					.filter(role -> roleNameList.stream().anyMatch(name -> role.getRoleName().equals(name)))
//					.collect(Collectors.toList());
//
//			if (roleCollList.size() != roleNameList.size()) {
//				errorMsg.add(String.format("%s ?????????????????????", excel.getRoleNameList()));
//			}
//
//			// ??????????????????
//			if (CollUtil.isEmpty(errorMsg)) {
//				insertExcelUser(excel, deptOptional, roleCollList);
//			}
//			else {
//				// ?????????????????????
//				errorMessageList.add(new ErrorMessage((long) (i + 2), errorMsg));
//			}
//
//		}
//
//		if (CollUtil.isNotEmpty(errorMessageList)) {
//			return R.failed(errorMessageList);
//		}
//		return R.ok();
//	}

	@Override
	public List<Integer> listUserIdByDeptIds(Set<Integer> deptIds) {
		return this.listObjs(
				Wrappers.lambdaQuery(SysUser.class).select(SysUser::getUserId).in(SysUser::getDeptId, deptIds),
				Integer.class::cast);
	}

	/**
	 * ??????excel User
	 */
	private void insertExcelUser(UserExcelVO excel, Optional<SysDept> deptOptional, List<SysRole> roleCollList) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(excel.getUsername());
		userDTO.setPhone(excel.getPhone());
		// ????????????????????????????????????
		userDTO.setPassword(userDTO.getPhone());
		// ??????????????????????????????ID
		userDTO.setDeptId(deptOptional.get().getDeptId());
		// ??????????????????????????????ID
		List<Integer> roleIdList = roleCollList.stream().map(SysRole::getRoleId).collect(Collectors.toList());
		userDTO.setRole(roleIdList);
		// ????????????
		this.saveUser(userDTO);
	}

}
