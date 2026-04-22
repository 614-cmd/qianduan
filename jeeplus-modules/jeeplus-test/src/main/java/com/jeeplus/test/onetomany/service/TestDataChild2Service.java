/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataChild2Wrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild2DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild2;
import com.jeeplus.test.onetomany.mapper.TestDataChild2Mapper;

/**
 * 飞机票Service
 * @author 刘高峰
 * @version 2024-09-03
 */
@Service
@Transactional
public class TestDataChild2Service extends ServiceImpl<TestDataChild2Mapper, TestDataChild2> {

	/**
	* 查询列表
	* @param testDataMainId
	* @return
	*/
	public List <TestDataChild2DTO> findList(String testDataMainId) {
		return super.lambdaQuery ().eq ( TestDataChild2::getTestDataMainId, testDataMainId ).list ().stream ().map (TestDataChild2Wrapper.INSTANCE::toDTO ).collect( Collectors.toList());
	}

}
