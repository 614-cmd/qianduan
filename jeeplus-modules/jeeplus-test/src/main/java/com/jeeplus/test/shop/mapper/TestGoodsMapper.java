/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.shop.service.dto.TestGoodsDTO;
import com.jeeplus.test.shop.domain.TestGoods;

/**
 * 商品MAPPER接口
 * @author 刘高峰
 * @version 2024-09-03
 */
public interface TestGoodsMapper extends BaseMapper<TestGoods> {

    /**
     * 根据id获取商品
     * @param id
     * @return
     */
    TestGoodsDTO findById(String id);

    /**
     * 获取商品列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <TestGoodsDTO> findList(Page <TestGoodsDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
