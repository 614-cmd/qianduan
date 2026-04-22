/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.note.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 富文本Entity
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_note")
public class TestNote extends BaseEntity {

	private static final long serialVersionUID = 1L;

			
	/**
     * 标题
     */
	private String title;
			
	/**
     * 富文本
     */
	private String contents;
			
	/**
     * 备注信息
     */
	private String remarks;

}
