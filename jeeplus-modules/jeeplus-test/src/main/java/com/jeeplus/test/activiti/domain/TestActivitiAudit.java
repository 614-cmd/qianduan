/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.activiti.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 加薪申请Entity
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_activiti_audit")
public class TestActivitiAudit extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 流程实例ID
     */
	private String procInsId;
	/**
     * 员工
     */
	private String userId;
	/**
     * 部门
     */
	private String officeId;
	/**
     * 岗位
     */
	private String post;
	/**
     * 性别
     */
	private String sex;
	/**
     * 调整原因
     */
	private String content;
	/**
     * 调整前薪水
     */
    @TableField("old_salary")
	private String oldSalray;
	/**
     * 调整后薪水
     */
	private String newSalary;
	/**
     * 生效时间
     */
	private Date exeDate;
	/**
     * 备注信息
     */
	private String remarks;

}
