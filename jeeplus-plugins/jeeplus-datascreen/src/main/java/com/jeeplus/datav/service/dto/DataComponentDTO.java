/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.service.dto;

import jakarta.validation.constraints.NotEmpty;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 组件DTO
 * @author 刘高峰
 * @version 2023-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataComponentDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 组件名称
     */
	@NotEmpty(message="组件名称不能为空")
    @Query(type = QueryType.LIKE)
	@ExcelProperty("组件名称") 
	private String name;
	        
	/**
     * 组件内容
     */
	@NotEmpty(message="组件内容不能为空")
	@ExcelProperty("组件内容") 
	private String content;

}
