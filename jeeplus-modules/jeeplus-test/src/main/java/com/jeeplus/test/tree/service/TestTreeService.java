/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.tree.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.core.service.TreeService;
import com.jeeplus.test.tree.domain.TestTree;
import com.jeeplus.test.tree.mapper.TestTreeMapper;

/**
 * 组织机构Service
 * @author 刘高峰
 * @version 2024-09-01
 */
@Service
@Transactional
public class TestTreeService extends TreeService<TestTreeMapper, TestTree> {

	public boolean saveOrUpdate(TestTree testTree) {
		return super.saveOrUpdate (testTree);
	}

	public boolean removeWithChildrenById(String id) {
		return super.removeWithChildrenById (id);
	}

}
