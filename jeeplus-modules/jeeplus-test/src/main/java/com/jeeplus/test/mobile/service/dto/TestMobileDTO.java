/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.mobile.service.dto;

import jakarta.validation.constraints.NotEmpty;
import com.jeeplus.sys.service.dto.OfficeDTO;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.excel.converter.ExcelOfficeDTOConverter;
import com.jeeplus.core.excel.converter.ExcelDictDTOConverter;
import com.jeeplus.core.excel.annotation.ExcelDictProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 移动表单DTO
 * @author 刘高峰
 * @version 2023-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestMobileDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 姓名
     */
	@NotEmpty(message="姓名不能为空")
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	@ExcelProperty("姓名") 
	private String name;
	        
	/**
     * 年龄
     */
	@NotEmpty(message="年龄不能为空")
	@ExcelProperty("年龄") 
	private String age;
	        
	/**
     * 地区
     */
	@ExcelProperty("地区") 
	private String areaId;
	        
	/**
     * 公司
     */
    @ExcelProperty(value = "公司", converter = ExcelOfficeDTOConverter.class) 
	private OfficeDTO company;
	        
	/**
     * 部门
     */
    @ExcelProperty(value = "部门", converter = ExcelOfficeDTOConverter.class) 
	private OfficeDTO office;
	        
	/**
     * 性别
     */
	@ExcelProperty(value = "性别", converter = ExcelDictDTOConverter.class)
	@ExcelDictProperty("sex")
	private String sex;
	        
	/**
     * 头像
     */
	@ExcelProperty("头像") 
	private String teImage;
	        
	/**
     * 单选框
     */
	@ExcelProperty(value = "单选框", converter = ExcelDictDTOConverter.class)
	@ExcelDictProperty("yes_no")
	private String isSingle;
	        
	/**
     * 多选框
     */
	@ExcelProperty(value = "多选框", converter = ExcelDictDTOConverter.class)
	@ExcelDictProperty("oa_leave_type")
	private String cks;
	        
	/**
     * 多行文本框
     */
	@ExcelProperty("多行文本框") 
	private String textArea;
	        
	/**
     * 数字输入框
     */
	@ExcelProperty("数字输入框") 
	private String textNumber;
	        
	/**
     * 日期选择框
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelProperty("日期选择框") 
	private Date teDate;
	        
	/**
     * 邮箱
     */
	@ExcelProperty("邮箱") 
	private String email;
	        
	/**
     * 手机号
     */
	@ExcelProperty("手机号") 
	private String phone;
	        
	/**
     * 电话
     */
	@ExcelProperty("电话") 
	private String tel;

}
