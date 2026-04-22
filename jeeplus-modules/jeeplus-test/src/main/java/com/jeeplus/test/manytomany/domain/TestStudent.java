/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 学生Entity
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_student")
public class TestStudent extends BaseEntity {

	private static final long serialVersionUID = 1L;

			
	/**
     * 姓名
     */
	private String name;
			
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
