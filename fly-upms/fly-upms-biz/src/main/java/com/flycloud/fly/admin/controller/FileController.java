//package com.flycloud.fly.admin.controller;
//
//import cn.hutool.core.io.IoUtil;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.flycloud.fly.admin.api.entity.SysFile;
//import com.flycloud.fly.common.core.util.R;
//import com.flycloud.fly.common.log.annotation.SysLog;
//import com.flycloud.fly.common.security.annotation.Inner;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 文件管理
// *
// * @author Luckly
// * @date 2021-09-11
// */
//@RestController
//@AllArgsConstructor
//@RequestMapping("/sys-file")
////@Api(value = "sys-file", tags = "文件管理")
//public class FileController {
//
//	private final SysFileService sysFileService;
//
//	/**
//	 * 分页查询
//	 * @param page 分页对象
//	 * @param sysFile 文件管理
//	 * @return
//	 */
//	@ApiOperation(value = "分页查询", notes = "分页查询")
//	@GetMapping("/page")
//	public R getSysFilePage(Page page, SysFile sysFile) {
//		return R.ok(sysFileService.page(page, Wrappers.query(sysFile)));
//	}
//
//	/**
//	 * 通过id删除文件管理
//	 * @param id id
//	 * @return R
//	 */
//	@ApiOperation(value = "通过id删除文件管理", notes = "通过id删除文件管理")
//	@SysLog("删除文件管理")
//	@DeleteMapping("/{id}")
//	@PreAuthorize("@pms.hasPermission('sys_file_del')")
//	public R removeById(@PathVariable Long id) {
//		return R.ok(sysFileService.deleteFile(id));
//	}
//
//	/**
//	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
//	 * @param file 资源
//	 * @return R(/ admin / bucketName / filename)
//	 */
//	@PostMapping(value = "/upload")
//	public R upload(@RequestPart("file") MultipartFile file) {
//		return sysFileService.uploadFile(file);
//	}
//
//	/**
//	 * 获取文件
//	 * @param bucket 桶名称
//	 * @param fileName 文件空间/名称
//	 * @param response
//	 * @return
//	 */
//	@Inner(false)
//	@GetMapping("/{bucket}/{fileName}")
//	public void file(@PathVariable String bucket, @PathVariable String fileName, HttpServletResponse response) {
//		sysFileService.getFile(bucket, fileName, response);
//	}
//
//	/**
//	 * 获取本地（resources）文件
//	 * @param fileName 文件名称
//	 * @param response 本地文件
//	 */
//	@SneakyThrows
//	@GetMapping("/local/{fileName}")
//	public void localFile(@PathVariable String fileName, HttpServletResponse response) {
//		ClassPathResource resource = new ClassPathResource("file/" + fileName);
//		response.setContentType("application/octet-stream; charset=UTF-8");
//		IoUtil.copy(resource.getInputStream(), response.getOutputStream());
//	}
//
//}
