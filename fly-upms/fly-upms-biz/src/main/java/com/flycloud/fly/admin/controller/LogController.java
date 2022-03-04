package com.flycloud.fly.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flycloud.fly.admin.api.dto.SysLogDTO;
import com.flycloud.fly.admin.api.entity.SysLog;
import com.flycloud.fly.admin.service.SysLogService;
import com.flycloud.fly.common.core.util.R;
import com.flycloud.fly.common.security.annotation.Inner;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
//@Api(value = "log", tags = "日志管理模块")
public class LogController {

	private final SysLogService sysLogService;

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysLog 系统日志
	 * @return
	 */
	@GetMapping("/page")
	public R getLogPage(Page page, SysLogDTO sysLog) {
		return R.ok(sysLogService.getLogByPage(page, sysLog));
	}

	/**
	 * 删除日志
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_log_del')")
	public R removeById(@PathVariable Long id) {
		return R.ok(sysLogService.removeById(id));
	}

	/**
	 * 插入日志
	 * @param sysLog 日志实体
	 * @return success/false
	 */
	@Inner
	@PostMapping
	public R save(@Valid @RequestBody SysLog sysLog) {
		return R.ok(sysLogService.save(sysLog));
	}

	/**
	 * 导出excel 表格
	 * @param sysLog 查询条件
	 * @return EXCEL
	 */
//	@ResponseExcel
//	@GetMapping("/export")
//	@PreAuthorize("@pms.hasPermission('sys_log_import_export')")
//	public List<SysLog> export(SysLogDTO sysLog) {
//		return sysLogService.getLogList(sysLog);
//	}

}
