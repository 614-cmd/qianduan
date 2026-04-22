/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.tree.service.mapstruct;


import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.test.tree.service.dto.TestTreeDTO;
import com.jeeplus.test.tree.domain.TestTree;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestTreeWrapper
 * @author 刘高峰
 * @version 2024-09-01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestTreeWrapper extends TreeWrapper<TestTreeDTO, TestTree> {

    TestTreeWrapper INSTANCE = Mappers.getMapper(TestTreeWrapper.class);
}

