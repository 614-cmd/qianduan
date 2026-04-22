/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.shop.service.dto.TestGoodsDTO;
import com.jeeplus.test.shop.domain.TestGoods;
import com.jeeplus.test.shop.mapper.TestGoodsMapper;

/**
 * 商品Service
 * @author 刘高峰
 * @version 2024-09-03
 */
@Service
@Transactional
public class TestGoodsService extends ServiceImpl<TestGoodsMapper, TestGoods> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TestGoodsDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <TestGoodsDTO> findPage(Page <TestGoodsDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

}
