package com.flycloud.fly.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.flycloud.fly.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典表
 *
 * @author lengleng
 * @date 2019/03/19
 */
@Data
//@ApiModel(value = "字典类型")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	//@ApiModelProperty(value = "字典编号")
	private Integer id;

	/**
	 * 类型
	 */
	//@ApiModelProperty(value = "字典类型")
	private String type;

	/**
	 * 描述
	 */
	//@ApiModelProperty(value = "字典描述")
	private String description;

	/**
	 * 是否是系统内置
	 */
	@TableField(value = "`system`")
	//@ApiModelProperty(value = "是否系统内置")
	private String system;

	/**
	 * 备注信息
	 */
	//@ApiModelProperty(value = "备注信息")
	private String remarks;

	/**
	 * 删除标记
	 */
	@TableLogic
	//@ApiModelProperty(value = "删除标记,1:已删除,0:正常")
	private String delFlag;

}
