/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.service.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 课程DTO
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCourseDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 课程名
     */
    @Query(type = QueryType.LIKE)
	@ExcelProperty("课程名") 
	private String name;
	        
	/**
     * 备注信息
     */
	@ExcelProperty("备注信息") 
	private String remarks;
	        
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

}
