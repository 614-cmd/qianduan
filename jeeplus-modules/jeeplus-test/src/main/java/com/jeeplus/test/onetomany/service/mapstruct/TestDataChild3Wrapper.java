/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild3DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestDataChild3Wrapper
 * @author 刘高峰
 * @version 2024-09-03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestDataChild3Wrapper extends EntityWrapper<TestDataChild3DTO, TestDataChild3> {

    TestDataChild3Wrapper INSTANCE = Mappers.getMapper(TestDataChild3Wrapper.class);
     @Mappings({
            @Mapping(source = "testDataMain.id", target = "testDataMainId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping (source = "updateBy.id", target = "updateById")})
    TestDataChild3 toEntity(TestDataChild3DTO dto);


    @Mappings({
            @Mapping(source = "testDataMainId", target = "testDataMain.id"),
            @Mapping (source = "createById", target = "createBy.id"),
            @Mapping (source = "updateById", target = "updateBy.id")})
    TestDataChild3DTO toDTO(TestDataChild3 entity);
}

