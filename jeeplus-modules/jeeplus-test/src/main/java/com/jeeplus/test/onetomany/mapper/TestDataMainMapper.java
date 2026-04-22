/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.onetomany.service.dto.TestDataMainDTO;
import com.jeeplus.test.onetomany.domain.TestDataMain;

/**
 * 票务代理MAPPER接口
 * @author 刘高峰
 * @version 2024-09-03
 */
public interface TestDataMainMapper extends BaseMapper<TestDataMain> {

    /**
     * 根据id获取票务代理
     * @param id
     * @return
     */
    TestDataMainDTO findById(String id);

    /**
     * 获取票务代理列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <TestDataMainDTO> findList(Page <TestDataMainDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
