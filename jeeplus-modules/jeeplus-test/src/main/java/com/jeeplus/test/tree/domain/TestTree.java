/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.tree.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织机构Entity
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_tree")
public class TestTree extends TreeEntity<TestTree> {

	private static final long serialVersionUID = 1L;

	/**
     * 名称
     */
	private String name;
	/**
     * 备注信息
     */
	private String remarks;

}
