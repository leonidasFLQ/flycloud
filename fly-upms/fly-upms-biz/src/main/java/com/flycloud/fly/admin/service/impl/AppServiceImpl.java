package com.flycloud.fly.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.flycloud.fly.admin.api.entity.SysUser;
import com.flycloud.fly.admin.mapper.SysUserMapper;
import com.flycloud.fly.admin.service.AppService;
import com.flycloud.fly.common.core.constant.CacheConstants;
import com.flycloud.fly.common.core.constant.SecurityConstants;
import com.flycloud.fly.common.core.util.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lengleng
 * @date 2018/11/14
 * <p>
 * 手机登录相关业务实现
 */
@Slf4j
@Service
@AllArgsConstructor
public class AppServiceImpl implements AppService {

	private final RedisTemplate redisTemplate;

	private final SysUserMapper userMapper;

	/**
	 * 发送手机验证码 TODO: 调用短信网关发送验证码,测试返回前端
	 * @param phone 手机号
	 * @return code
	 */
	@Override
	public R<Boolean> sendSmsCode(String phone) {
		List<SysUser> userList = userMapper.selectList(Wrappers.<SysUser>query().lambda().eq(SysUser::getPhone, phone));

		if (CollUtil.isEmpty(userList)) {
			log.info("手机号未注册:{}", phone);
			return R.ok(Boolean.FALSE, "手机号未注册");
		}

		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + phone);

		if (codeObj != null) {
			log.info("手机号验证码未过期:{}，{}", phone, codeObj);
			return R.ok(Boolean.FALSE, "验证码发送过频繁");
		}

		String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));
		log.info("手机号生成验证码成功:{},{}", phone, code);
		redisTemplate.opsForValue().set(CacheConstants.DEFAULT_CODE_KEY + phone, code, SecurityConstants.CODE_TIME,
				TimeUnit.SECONDS);
		return R.ok(Boolean.TRUE, code);
	}

}
