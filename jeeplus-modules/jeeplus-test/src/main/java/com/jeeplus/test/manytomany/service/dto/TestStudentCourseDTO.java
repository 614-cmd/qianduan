/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.service.dto;

import com.jeeplus.test.manytomany.service.dto.TestStudentDTO;
import com.jeeplus.test.manytomany.service.dto.TestCourseDTO;
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
 * 学生课程记录DTO
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestStudentCourseDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 学生
     */
    @Query(tableColumn = "a.student_id", javaField = "student.id", type = QueryType.EQ)
	@ExcelProperty(value = "学生", converter = ExcelFieldDTOConverter.class)
	@ExcelFieldProperty(value = "student.name", service =  "com.jeeplus.test.manytomany.service.TestStudentService", wrapper= "com.jeeplus.test.manytomany.service.mapstruct.TestStudentWrapper")
	private TestStudentDTO student;
	        
	/**
     * 课程
     */
    @Query(tableColumn = "a.course_id", javaField = "course.id", type = QueryType.EQ)
	@ExcelProperty(value = "课程", converter = ExcelFieldDTOConverter.class)
	@ExcelFieldProperty(value = "course.name", service =  "com.jeeplus.test.manytomany.service.TestCourseService", wrapper= "com.jeeplus.test.manytomany.service.mapstruct.TestCourseWrapper")
	private TestCourseDTO course;
	        
	/**
     * 分数
     */
    @Query(tableColumn = "a.score", javaField = "score", type = QueryType.GT)
	@ExcelProperty("分数") 
	private String score;
	        
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
