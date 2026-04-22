/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.one.service.dto;

import com.jeeplus.sys.service.dto.OfficeDTO;
import jakarta.validation.constraints.NotEmpty;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.excel.converter.ExcelOfficeDTOConverter;
import com.jeeplus.core.excel.converter.ExcelUserDTOConverter;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 请假表单DTO
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestFormLeaveDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 归属部门
     */
    @Query(tableColumn = "a.office_id", javaField = "office.id", type = QueryType.LIKE)
    @ExcelProperty(value = "归属部门", converter = ExcelOfficeDTOConverter.class) 
	private OfficeDTO office;
	        
	/**
     * 员工
     */
    @Query(tableColumn = "a.user_id", javaField = "user.id", type = QueryType.LIKE)
    @ExcelProperty(value = "员工", converter = ExcelUserDTOConverter.class) 
	private UserDTO user;
	        
	/**
     * 地区
     */
	@ExcelProperty("地区") 
	private String area;
	        
	/**
     * 请假开始日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelProperty("请假开始日期") 
	private Date beginDate;
	        
	/**
     * 请假结束日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelProperty("请假结束日期") 
	private Date endDate;

}
