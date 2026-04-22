/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service.mapstruct;


import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.test.treetable.service.dto.TestCarKindDTO;
import com.jeeplus.test.treetable.domain.TestCarKind;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 车系Wrapper
 * @author 刘高峰
 * @version 2024-09-01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestCarKindWrapper extends TreeWrapper<TestCarKindDTO, TestCarKind> {

    TestCarKindWrapper INSTANCE = Mappers.getMapper(TestCarKindWrapper.class);
}

