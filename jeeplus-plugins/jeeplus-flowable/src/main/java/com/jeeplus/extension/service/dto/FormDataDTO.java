/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 工作流数据DTO
 * @author 刘高峰
 * @version 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FormDataDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 流程key
     */
    @Query(type = QueryType.EQ)
	@ExcelProperty("流程key") 
	private String procDefKey;
	        
	/**
     * 流程定义ID
     */
    @Query(type = QueryType.EQ)
	@ExcelProperty("流程定义ID") 
	private String procDefId;
	        
	/**
     * 流程实例ID
     */
    @Query(type = QueryType.EQ)
	@ExcelProperty("流程实例ID") 
	private String procInsId;
	        
	/**
     * 任务ID
     */
    @Query(type = QueryType.EQ)
	@ExcelProperty("任务ID") 
	private String taskId;
	        
	/**
     * 流程关联表名
     */
    @Query(type = QueryType.EQ)
	@ExcelProperty("流程关联表名") 
	private String businessTable;
	        
	/**
     * 流程表数据ID
     */
    @Query(type = QueryType.EQ)
	@ExcelProperty("流程表数据ID") 
	private String businessId;

}
