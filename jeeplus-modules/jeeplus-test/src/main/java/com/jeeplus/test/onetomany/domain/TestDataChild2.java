/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 飞机票Entity
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_data_child2")
public class TestDataChild2 extends BaseEntity {

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
	private Date starttime;
			
	/**
     * 到达时间
     */
	private Date endtime;
			
	/**
     * 代理价格
     */
	private Double price;
			
	/**
     * 外键
     */
	private String testDataMainId;
			
	/**
     * 备注信息
     */
	private String remarks;

}
