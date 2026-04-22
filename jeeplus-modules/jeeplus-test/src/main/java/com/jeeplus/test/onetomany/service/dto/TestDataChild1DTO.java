/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 火车票Entity
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestDataChild1DTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	        
	/**
     * 出发地
     */
	private String startarea;
	        
	/**
     * 目的地
     */
	private String endarea;
	        
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
     * 代理价格
     */
	private Long price;
	        
	/**
     * 业务主表ID
     */
	private TestDataMainDTO testDataMain;
	        
	/**
     * 备注信息
     */
	private String remarks;

}
