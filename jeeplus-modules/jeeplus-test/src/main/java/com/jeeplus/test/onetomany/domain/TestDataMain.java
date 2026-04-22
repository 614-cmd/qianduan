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
 * 票务代理Entity
 * @author 刘高峰
 * @version 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_data_main")
public class TestDataMain extends BaseEntity {

	private static final long serialVersionUID = 1L;

			
	/**
     * 用户
     */
	private String userId;
			
	/**
     * 所属部门
     */
	private String officeId;
			
	/**
     * 所属区域
     */
	private String area;
			
	/**
     * 名称
     */
	private String name;
			
	/**
     * 性别
     */
	private String sex;
			
	/**
     * 身份证照片
     */
	private String file;
			
	/**
     * 加入日期
     */
	private Date inDate;
			
	/**
     * 备注信息
     */
	private String remarks;

}
