/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataMainDTO;
import com.jeeplus.test.onetomany.domain.TestDataMain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestDataMainWrapper
 * @author 刘高峰
 * @version 2024-09-03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestDataMainWrapper extends EntityWrapper<TestDataMainDTO, TestDataMain> {

    TestDataMainWrapper INSTANCE = Mappers.getMapper(TestDataMainWrapper.class);
     @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "office.id", target = "officeId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping (source = "updateBy.id", target = "updateById")})
    TestDataMain toEntity(TestDataMainDTO dto);


    @Mappings({
            @Mapping(source = "userId", target = "user.id"),
            @Mapping(source = "officeId", target = "office.id"),
            @Mapping (source = "createById", target = "createBy.id"),
            @Mapping (source = "updateById", target = "updateBy.id")})
    TestDataMainDTO toDTO(TestDataMain entity);
}

