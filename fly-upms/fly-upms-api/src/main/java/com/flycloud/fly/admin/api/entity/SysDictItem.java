package com.flycloud.fly.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.flycloud.fly.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典项
 *
 * @author lengleng
 * @date 2019/03/19
 */
@Data
//@ApiModel(value = "字典项")
@EqualsAndHashCode(callSuper = true)
public class SysDictItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	//@ApiModelProperty(value = "字典项id")
	private Integer id;

	/**
	 * 所属字典类id
	 */
	//@ApiModelProperty(value = "所属字典类id")
	private Integer dictId;

	/**
	 * 数据值
	 */
	//@ApiModelProperty(value = "数据值")
	private String value;

	/**
	 * 标签名
	 */
	//@ApiModelProperty(value = "标签名")
	private String label;

	/**
	 * 类型
	 */
	//@ApiModelProperty(value = "类型")
	private String type;

	/**
	 * 描述
	 */
	//@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 排序（升序）
	 */
	//@ApiModelProperty(value = "排序值，默认升序")
	private Integer sort;

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
