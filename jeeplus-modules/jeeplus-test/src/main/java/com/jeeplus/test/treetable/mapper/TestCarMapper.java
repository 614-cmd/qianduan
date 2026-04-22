/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.treetable.service.dto.TestCarDTO;
import com.jeeplus.test.treetable.domain.TestCar;

/**
 * 车辆MAPPER接口
 * @author 刘高峰
 * @version 2024-09-01
 */
public interface TestCarMapper extends BaseMapper<TestCar> {

    /**
     * 根据id获取车辆
     * @param id
     * @return
     */
    TestCarDTO findById(String id);

    /**
     * 获取车辆列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <TestCarDTO> findList(Page <TestCarDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
