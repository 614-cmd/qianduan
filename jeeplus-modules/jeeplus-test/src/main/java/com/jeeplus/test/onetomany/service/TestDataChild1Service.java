/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataChild1Wrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild1DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild1;
import com.jeeplus.test.onetomany.mapper.TestDataChild1Mapper;

/**
 * 火车票Service
 * @author 刘高峰
 * @version 2024-09-03
 */
@Service
@Transactional
public class TestDataChild1Service extends ServiceImpl<TestDataChild1Mapper, TestDataChild1> {

	/**
	* 查询列表
	* @param testDataMainId
	* @return
	*/
	public List <TestDataChild1DTO> findList(String testDataMainId) {
		return super.lambdaQuery ().eq ( TestDataChild1::getTestDataMainId, testDataMainId ).list ().stream ().map (TestDataChild1Wrapper.INSTANCE::toDTO ).collect( Collectors.toList());
	}

}
