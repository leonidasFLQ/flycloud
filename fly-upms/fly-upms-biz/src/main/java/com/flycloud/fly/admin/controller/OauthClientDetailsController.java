package com.flycloud.fly.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flycloud.fly.admin.api.entity.SysOauthClientDetails;
import com.flycloud.fly.admin.service.SysOauthClientDetailsService;
import com.flycloud.fly.common.core.util.R;
import com.flycloud.fly.common.log.annotation.SysLog;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2018-05-15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
//@Api(value = "client", tags = "客户端管理模块")
public class OauthClientDetailsController {

	private final SysOauthClientDetailsService sysOauthClientDetailsService;

	/**
	 * 通过ID查询
	 * @param clientId 客户端id
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/{clientId}")
	public R getByClientId(@PathVariable String clientId) {
		return R.ok(sysOauthClientDetailsService
				.list(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId)));
	}

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysOauthClientDetails 系统终端
	 * @return
	 */
	@GetMapping("/page")
	public R getOauthClientDetailsPage(Page page, SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(sysOauthClientDetailsService.page(page, Wrappers.query(sysOauthClientDetails)));
	}

	/**
	 * 添加
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@SysLog("添加终端")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_client_add')")
	public R add(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(sysOauthClientDetailsService.save(sysOauthClientDetails));
	}

	/**
	 * 删除
	 * @param id ID
	 * @return success/false
	 */
	@SysLog("删除终端")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public R removeById(@PathVariable String id) {
		return R.ok(sysOauthClientDetailsService.removeClientDetailsById(id));
	}

	/**
	 * 编辑
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@SysLog("编辑终端")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_client_edit')")
	public R update(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(sysOauthClientDetailsService.updateClientDetailsById(sysOauthClientDetails));
	}

}
