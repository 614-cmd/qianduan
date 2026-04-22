/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.pic.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 图片DTO
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestPicDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 标题
     */
    @Query(type = QueryType.LIKE)
	@ExcelProperty("标题") 
	private String title;
	        
	/**
     * 图片路径
     */
	@ExcelProperty("图片路径") 
	private String pic;
	        
	/**
     * 文件
     */
	@ExcelProperty("文件") 
	private String file;
	        
	/**
     * 备注信息
     */
	@ExcelProperty("备注信息") 
	private String remarks;

}
