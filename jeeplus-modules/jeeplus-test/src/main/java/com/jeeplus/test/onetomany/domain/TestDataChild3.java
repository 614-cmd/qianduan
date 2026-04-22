/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 汽车票Entity
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_data_child3")
public class TestDataChild3 extends BaseEntity {

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
     * 代理价格
     */
	private Double price;
			
	/**
     * 出发时间
     */
	private Date starttime;
			
	/**
     * 到达时间
     */
	private Date endtime;
			
	/**
     * 外键
     */
	private String testDataMainId;
			
	/**
     * 创建时间
     */
    @TableField("create_time")
	private Date createDate;
			
	/**
     * 更新时间
     */
    @TableField("update_time")
	private Date updateDate;
			
	/**
     * 备注信息
     */
	private String remarks;

}
