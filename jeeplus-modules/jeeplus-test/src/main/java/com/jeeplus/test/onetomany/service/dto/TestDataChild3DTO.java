/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 汽车票Entity
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestDataChild3DTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 出发地
     */
    @Query(type = QueryType.EQ)
	private String startarea;
	        
	/**
     * 目的地
     */
    @Query(type = QueryType.EQ)
	private String endarea;
	        
	/**
     * 代理价格
     */
	private Double price;
	        
	/**
     * 出发时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date starttime;
	        
	/**
     * 到达时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endtime;
	        
	/**
     * 外键
     */
	private TestDataMainDTO testDataMain;
	        
	/**
     * 创建时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	        
	/**
     * 更新时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	        
	/**
     * 备注信息
     */
	private String remarks;

}
