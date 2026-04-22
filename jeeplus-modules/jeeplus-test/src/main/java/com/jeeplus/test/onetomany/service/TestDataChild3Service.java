/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataChild3Wrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild3DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild3;
import com.jeeplus.test.onetomany.mapper.TestDataChild3Mapper;

/**
 * 汽车票Service
 * @author 刘高峰
 * @version 2024-09-03
 */
@Service
@Transactional
public class TestDataChild3Service extends ServiceImpl<TestDataChild3Mapper, TestDataChild3> {

	/**
	* 查询列表
	* @param testDataMainId
	* @return
	*/
	public List <TestDataChild3DTO> findList(String testDataMainId) {
		return super.lambdaQuery ().eq ( TestDataChild3::getTestDataMainId, testDataMainId ).list ().stream ().map (TestDataChild3Wrapper.INSTANCE::toDTO ).collect( Collectors.toList());
	}

}
