/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service.dto;

import com.jeeplus.test.shop.service.dto.TestCategoryDTO;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.excel.ExcelFieldDTOConverter;
import com.jeeplus.core.excel.annotation.ExcelFieldProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 商品DTO
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestGoodsDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 商品名称
     */
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	@ExcelProperty("商品名称") 
	private String name;
	        
	/**
     * 所属类型
     */
    @Query(tableColumn = "a.category_id", javaField = "category.id", type = QueryType.EQ)
	@ExcelProperty(value = "所属类型", converter = ExcelFieldDTOConverter.class)
	@ExcelFieldProperty(value = "category.name", service =  "com.jeeplus.test.shop.service.TestCategoryService", wrapper= "com.jeeplus.test.shop.service.mapstruct.TestCategoryWrapper")
	private TestCategoryDTO category;
	        
	/**
     * 价格
     */
	@ExcelProperty("价格") 
	private String price;
	        
	/**
     * 创建时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelProperty("创建时间") 
	private Date createDate;
	        
	/**
     * 更新时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelProperty("更新时间") 
	private Date updateDate;
	        
	/**
     * 备注信息
     */
	@ExcelProperty("备注信息") 
	private String remarks;

}
