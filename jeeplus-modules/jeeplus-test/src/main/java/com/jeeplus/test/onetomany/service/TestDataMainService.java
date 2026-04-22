/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service;

import com.jeeplus.sys.constant.CommonConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.onetomany.service.dto.TestDataMainDTO;
import com.jeeplus.test.onetomany.service.dto.TestDataChild1DTO;
import com.jeeplus.test.onetomany.service.dto.TestDataChild2DTO;
import com.jeeplus.test.onetomany.service.dto.TestDataChild3DTO;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataMainWrapper;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataChild1Wrapper;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataChild2Wrapper;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataChild3Wrapper;
import com.jeeplus.test.onetomany.domain.TestDataMain;
import com.jeeplus.test.onetomany.domain.TestDataChild1;
import com.jeeplus.test.onetomany.domain.TestDataChild2;
import com.jeeplus.test.onetomany.domain.TestDataChild3;
import com.jeeplus.test.onetomany.mapper.TestDataMainMapper;

/**
 * 票务代理Service
 * @author 刘高峰
 * @version 2024-09-03
 */
@Service
@Transactional
public class TestDataMainService extends ServiceImpl<TestDataMainMapper, TestDataMain> {
	/**
	* 子表service
	*/
	@Autowired
	private TestDataChild1Service testDataChild1Service;
	/**
	* 子表service
	*/
	@Autowired
	private TestDataChild2Service testDataChild2Service;
	/**
	* 子表service
	*/
	@Autowired
	private TestDataChild3Service testDataChild3Service;

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TestDataMainDTO findById(String id) {
		TestDataMainDTO testDataMainDTO = baseMapper.findById ( id );
		testDataMainDTO.setTestDataChild1DTOList(testDataChild1Service.findList(id));
		testDataMainDTO.setTestDataChild2DTOList(testDataChild2Service.findList(id));
		testDataMainDTO.setTestDataChild3DTOList(testDataChild3Service.findList(id));
		return testDataMainDTO;
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <TestDataMainDTO> findPage(Page <TestDataMainDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	/**
	* 保存或者更新
	* @param  testDataMainDTO
	* @return
	*/
	public void saveOrUpdate(TestDataMainDTO testDataMainDTO) {
		TestDataMain testDataMain =  TestDataMainWrapper.INSTANCE.toEntity ( testDataMainDTO );
		super.saveOrUpdate (testDataMain);
		for (TestDataChild1DTO testDataChild1DTO : testDataMainDTO.getTestDataChild1DTOList ()){
			if ( CommonConstants.DELETED.equals ( testDataChild1DTO.getDelFlag()) ){
				testDataChild1Service.removeById ( testDataChild1DTO.getId () );
			}else{
				TestDataChild1 testDataChild1 = TestDataChild1Wrapper.INSTANCE.toEntity ( testDataChild1DTO );
				testDataChild1.setTestDataMainId ( testDataMain.getId () );
				testDataChild1Service.saveOrUpdate ( testDataChild1 );
			}
		}
		for (TestDataChild2DTO testDataChild2DTO : testDataMainDTO.getTestDataChild2DTOList ()){
			if ( CommonConstants.DELETED.equals ( testDataChild2DTO.getDelFlag()) ){
				testDataChild2Service.removeById ( testDataChild2DTO.getId () );
			}else{
				TestDataChild2 testDataChild2 = TestDataChild2Wrapper.INSTANCE.toEntity ( testDataChild2DTO );
				testDataChild2.setTestDataMainId ( testDataMain.getId () );
				testDataChild2Service.saveOrUpdate ( testDataChild2 );
			}
		}
		for (TestDataChild3DTO testDataChild3DTO : testDataMainDTO.getTestDataChild3DTOList ()){
			if ( CommonConstants.DELETED.equals ( testDataChild3DTO.getDelFlag()) ){
				testDataChild3Service.removeById ( testDataChild3DTO.getId () );
			}else{
				TestDataChild3 testDataChild3 = TestDataChild3Wrapper.INSTANCE.toEntity ( testDataChild3DTO );
				testDataChild3.setTestDataMainId ( testDataMain.getId () );
				testDataChild3Service.saveOrUpdate ( testDataChild3 );
			}
		}
	}

	/**
	 * 删除
	 * @param  id
	 * @return
	 */
	public void removeById(String id) {
		super.removeById ( id );
		testDataChild1Service.lambdaUpdate ().eq ( TestDataChild1::getTestDataMainId, id ).remove ();
		testDataChild2Service.lambdaUpdate ().eq ( TestDataChild2::getTestDataMainId, id ).remove ();
		testDataChild3Service.lambdaUpdate ().eq ( TestDataChild3::getTestDataMainId, id ).remove ();
	}

}
