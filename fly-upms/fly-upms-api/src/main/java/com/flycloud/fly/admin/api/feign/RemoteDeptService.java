package com.flycloud.fly.admin.api.feign;

import com.flycloud.fly.common.core.constant.SecurityConstants;
import com.flycloud.fly.common.core.constant.ServiceNameConstants;
import com.flycloud.fly.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author hccake
 */
@FeignClient(contextId = "remoteDeptService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteDeptService {

	/**
	 * 查收子级id列表
	 * @return 返回子级id列表
	 */
	@GetMapping("/dept/child-id/{deptId}")
	R<List<Integer>> listChildDeptId(@PathVariable("deptId") Integer deptId,
									 @RequestHeader(SecurityConstants.FROM) String from);

}
