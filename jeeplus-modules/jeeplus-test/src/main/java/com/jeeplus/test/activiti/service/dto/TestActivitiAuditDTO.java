/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
package com.jeeplus.test.activiti.service.dto;

import com.jeeplus.sys.service.dto.UserDTO;
import jakarta.validation.constraints.NotEmpty;
import com.jeeplus.sys.service.dto.OfficeDTO;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 加薪申请Entity
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestActivitiAuditDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 流程实例ID
     */
	private String procInsId;
	/**
     * 员工
     */
    @Query(tableColumn = "a.user_id", javaField = "user.id", type = QueryType.EQ)
	private UserDTO user;
	/**
     * 部门
     */
	private OfficeDTO office;
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
	private String oldSalray;
	/**
     * 调整后薪水
     */
	private String newSalary;
	/**
     * 生效时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date exeDate;
	/**
     * 备注信息
     */
	private String remarks;

}
