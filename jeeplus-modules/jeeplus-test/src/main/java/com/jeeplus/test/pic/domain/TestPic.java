/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.pic.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 图片Entity
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_pic")
public class TestPic extends BaseEntity {

	private static final long serialVersionUID = 1L;

			
	/**
     * 标题
     */
	private String title;
			
	/**
     * 图片路径
     */
	private String pic;
			
	/**
     * 文件
     */
    @TableField("t_file")
	private String file;
			
	/**
     * 备注信息
     */
	private String remarks;

}
