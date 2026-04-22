/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.manytomany.service.dto.TestStudentDTO;
import com.jeeplus.test.manytomany.domain.TestStudent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestStudentWrapper
 * @author 刘高峰
 * @version 2024-09-01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestStudentWrapper extends EntityWrapper<TestStudentDTO, TestStudent> {

    TestStudentWrapper INSTANCE = Mappers.getMapper(TestStudentWrapper.class);
}

