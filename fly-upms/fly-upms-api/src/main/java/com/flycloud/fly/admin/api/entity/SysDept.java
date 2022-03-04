package com.flycloud.fly.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.flycloud.fly.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(value = "dept_id", type = IdType.AUTO)
	//@ApiModelProperty(value = "部门id")
	private Integer deptId;

	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	//@ApiModelProperty(value = "部门名称")
	private String name;

	/**
	 * 排序
	 */
	//@ApiModelProperty(value = "排序值")
	private Integer sort;

	/**
	 * 父级部门id
	 */
	//@ApiModelProperty(value = "父级部门id")
	private Integer parentId;

	/**
	 * 是否删除 -1：已删除 0：正常
	 */
	//@TableLogic
	private String delFlag;

}
