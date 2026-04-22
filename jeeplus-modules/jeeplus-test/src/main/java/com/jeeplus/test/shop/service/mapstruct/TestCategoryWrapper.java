/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.service.mapstruct;


import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.test.shop.service.dto.TestCategoryDTO;
import com.jeeplus.test.shop.domain.TestCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestCategoryWrapper
 * @author 刘高峰
 * @version 2024-09-01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestCategoryWrapper extends TreeWrapper<TestCategoryDTO, TestCategory> {

    TestCategoryWrapper INSTANCE = Mappers.getMapper(TestCategoryWrapper.class);
}

