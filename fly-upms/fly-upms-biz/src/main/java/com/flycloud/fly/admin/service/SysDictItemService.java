package com.flycloud.fly.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flycloud.fly.admin.api.entity.SysDictItem;

/**
 * 字典项
 *
 * @author lengleng
 * @date 2019/03/19
 */
public interface SysDictItemService extends IService<SysDictItem> {

	/**
	 * 删除字典项
	 * @param id 字典项ID
	 * @return
	 */
	void removeDictItem(Integer id);

	/**
	 * 更新字典项
	 * @param item 字典项
	 * @return
	 */
	void updateDictItem(SysDictItem item);

}
