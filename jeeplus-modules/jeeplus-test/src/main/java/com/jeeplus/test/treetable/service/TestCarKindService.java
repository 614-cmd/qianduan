/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.core.service.TreeService;
import com.jeeplus.test.treetable.domain.TestCarKind;
import com.jeeplus.test.treetable.mapper.TestCarKindMapper;

/**
 * 车系Service
 * @author 刘高峰
 * @version 2024-09-01
 */
@Service
@Transactional
public class TestCarKindService extends TreeService<TestCarKindMapper, TestCarKind> {

	public boolean saveOrUpdate(TestCarKind testCarKind) {
		return super.saveOrUpdate (testCarKind);
	}

	public boolean removeWithChildrenById(String id) {
		return super.removeWithChildrenById (id);
	}

}
