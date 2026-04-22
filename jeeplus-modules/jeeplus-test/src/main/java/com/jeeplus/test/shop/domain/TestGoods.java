/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 商品Entity
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_goods")
public class TestGoods extends BaseEntity {

	private static final long serialVersionUID = 1L;

			
	/**
     * 商品名称
     */
	private String name;
			
	/**
     * 所属类型
     */
	private String categoryId;
			
	/**
     * 价格
     */
	private String price;
			
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
