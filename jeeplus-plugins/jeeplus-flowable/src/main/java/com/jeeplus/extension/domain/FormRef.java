/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 工作流数据Entity
 * @author 刘高峰
 * @version 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("act_extension_form_ref")
public class FormRef extends BaseEntity {

	private static final long serialVersionUID = 1L;


	/**
     * 流程key
     */
	private String procDefKey;

	/**
     * 流程定义ID
     */
	private String procDefId;

	/**
     * 流程实例ID
     */
	private String procInsId;

	/**
     * 任务ID
     */
	private String taskId;

	/**
     * 流程关联表名
     */
	private String businessTable;

	/**
     * 流程表数据ID
     */
	private String businessId;

}
