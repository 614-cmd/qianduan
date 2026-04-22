/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service.dto;

import java.util.List;
import com.google.common.collect.Lists;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车系DTO
 * @author 刘高峰
 * @version 2024-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestCarKindDTO extends TreeDTO<TestCarKindDTO> {

	private static final long serialVersionUID = 1L;

	/**
     * 名称
     */
	private String name;
	/**
     * 备注信息
     */
	private String remarks;
    /**
     *子表列表
     */
	private List<TestCarDTO> testCarDTOList = Lists.newArrayList();


}
