/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.validation.service.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.alibaba.excel.annotation.ExcelProperty;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 测试校验功能DTO
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestValidationDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 浮点数字
     */
    @Query(type = QueryType.GT)
	@ExcelProperty("浮点数字") 
	private Double num;
	        
	/**
     * 整数
     */
	@NotEmpty(message="整数不能为空")
    @Query(type = QueryType.EQ)
	@ExcelProperty("整数") 
	private Long num2;
	        
	/**
     * 字符串
     */
	@ExcelProperty("字符串") 
	private String str;
	        
	/**
     * 邮件
     */
	@ExcelProperty("邮件") 
	private String email;
	        
	/**
     * 网址
     */
	@ExcelProperty("网址") 
	private String url;
	        
	/**
     * 日期
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelProperty("日期") 
	private Date newDate;
	        
	/**
     * 备注信息
     */
	@ExcelProperty("备注信息") 
	private String remarks;
	        
	/**
     * 浮点数小于等于0
     */
	@ExcelProperty("浮点数小于等于0") 
	private String c1;
	        
	/**
     * 身份证号码
     */
	@ExcelProperty("身份证号码") 
	private String c2;
	        
	/**
     * QQ号码
     */
	@ExcelProperty("QQ号码") 
	private String c3;
	        
	/**
     * 手机号码
     */
	@ExcelProperty("手机号码") 
	private String c4;
	        
	/**
     * 中英文数字下划线
     */
	@ExcelProperty("中英文数字下划线") 
	private String c5;
	        
	/**
     * 合法字符(a-z A-Z 0-9)
     */
	@ExcelProperty("合法字符(a-z A-Z 0-9)") 
	private String c6;
	        
	/**
     * 英语
     */
	@ExcelProperty("英语") 
	private String en;
	        
	/**
     * 汉字
     */
	@ExcelProperty("汉字") 
	private String zn;
	        
	/**
     * 汉英字符
     */
	@ExcelProperty("汉英字符") 
	private String enzn;

}
