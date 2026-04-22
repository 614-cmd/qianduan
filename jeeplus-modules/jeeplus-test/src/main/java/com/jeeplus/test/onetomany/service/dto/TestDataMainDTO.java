/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.dto;

import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.service.dto.OfficeDTO;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.excel.converter.ExcelUserDTOConverter;
import com.jeeplus.core.excel.converter.ExcelOfficeDTOConverter;
import com.jeeplus.core.excel.converter.ExcelDictDTOConverter;
import com.jeeplus.core.excel.annotation.ExcelDictProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 票务代理Entity
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestDataMainDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 用户
     */
    @Query(tableColumn = "a.user_id", javaField = "user.id", type = QueryType.EQ)
    @ExcelProperty(value = "用户", converter = ExcelUserDTOConverter.class) 
	private UserDTO user;
	        
	/**
     * 所属部门
     */
    @Query(tableColumn = "a.office_id", javaField = "office.id", type = QueryType.EQ)
    @ExcelProperty(value = "所属部门", converter = ExcelOfficeDTOConverter.class) 
	private OfficeDTO office;
	        
	/**
     * 所属区域
     */
    @Query(tableColumn = "a.area", javaField = "area", type = QueryType.EQ)
	@ExcelProperty("所属区域") 
	private String area;
	        
	/**
     * 名称
     */
    @Query(tableColumn = "a.name", javaField = "name", type = QueryType.LIKE)
	@ExcelProperty("名称") 
	private String name;
	        
	/**
     * 性别
     */
	@ExcelProperty(value = "性别", converter = ExcelDictDTOConverter.class) 
	@ExcelDictProperty("sex")
	private String sex;
	        
	/**
     * 身份证照片
     */
	@ExcelProperty("身份证照片") 
	private String file;
	        
	/**
     * 加入日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelProperty("加入日期") 
	private Date inDate;
	        
	/**
     * 备注信息
     */
	@ExcelProperty("备注信息") 
	private String remarks;
    /**
     *子表列表
     */
	private List<TestDataChild1DTO> testDataChild1DTOList = Lists.newArrayList();
    /**
     *子表列表
     */
	private List<TestDataChild2DTO> testDataChild2DTOList = Lists.newArrayList();
    /**
     *子表列表
     */
	private List<TestDataChild3DTO> testDataChild3DTOList = Lists.newArrayList();

}
