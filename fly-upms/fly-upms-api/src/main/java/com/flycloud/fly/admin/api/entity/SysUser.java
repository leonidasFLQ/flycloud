package com.flycloud.fly.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.flycloud.fly.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "user_id", type = IdType.AUTO)
	//@ApiModelProperty(value = "主键id")
	private Integer userId;

	/**
	 * 用户名
	 */
	//@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 密码
	 */
	//@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 随机盐
	 */
//	@JsonIgnore
//	@ApiModelProperty(value = "随机盐")
	private String salt;

	/**
	 * 锁定标记
	 */
	//@ApiModelProperty(value = "锁定标记")
	private String lockFlag;

	/**
	 * 手机号
	 */
	//@ApiModelProperty(value = "手机号")
	private String phone;

	/**
	 * 头像
	 */
	//@ApiModelProperty(value = "头像地址")
	private String avatar;

	/**
	 * 部门ID
	 */
	//@ApiModelProperty(value = "用户所属部门id")
	private Integer deptId;

	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String delFlag;

}
