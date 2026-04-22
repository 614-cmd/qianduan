/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.core.service.TreeService;
import com.jeeplus.test.shop.domain.TestCategory;
import com.jeeplus.test.shop.mapper.TestCategoryMapper;

/**
 * 商品类型Service
 * @author 刘高峰
 * @version 2024-09-01
 */
@Service
@Transactional
public class TestCategoryService extends TreeService<TestCategoryMapper, TestCategory> {

	public boolean saveOrUpdate(TestCategory testCategory) {
		return super.saveOrUpdate (testCategory);
	}

	public boolean removeWithChildrenById(String id) {
		return super.removeWithChildrenById (id);
	}

}
