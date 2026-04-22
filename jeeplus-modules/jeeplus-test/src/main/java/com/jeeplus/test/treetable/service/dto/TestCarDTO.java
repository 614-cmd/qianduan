/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.excel.ExcelFieldDTOConverter;
import com.jeeplus.core.excel.annotation.ExcelFieldProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 车辆Entity
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCarDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 品牌
     */
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	@ExcelProperty("品牌") 
	private String name;
	        
	/**
     * 车系
     */
	@Query(tableColumn = "a.kind_id", javaField = "kind.id", type = QueryType.EQ)
	@ExcelProperty(value = "车系", converter = ExcelFieldDTOConverter.class)
	@ExcelFieldProperty(value = "kind.name", service =  "com.jeeplus.test.treetable.service.TestCarKindService", wrapper= "com.jeeplus.test.treetable.service.mapstruct.TestCarKindWrapper")
	private TestCarKindDTO kind;
	        
	/**
     * 简介
     */
	@ExcelProperty("简介") 
	private String remarks;

}
