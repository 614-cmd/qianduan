/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service.dto;

import jakarta.validation.constraints.NotEmpty;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品类型DTO
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCategoryDTO extends TreeDTO<TestCategoryDTO> {

	private static final long serialVersionUID = 1L;

	/**
     * 类型名
     */
	@NotEmpty(message="类型名不能为空")
	private String name;
	/**
     * 备注信息
     */
	private String remarks;


}
