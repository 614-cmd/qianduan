/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild1DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild1;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestDataChild1Wrapper
 * @author 刘高峰
 * @version 2024-09-03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestDataChild1Wrapper extends EntityWrapper<TestDataChild1DTO, TestDataChild1> {

    TestDataChild1Wrapper INSTANCE = Mappers.getMapper(TestDataChild1Wrapper.class);
     @Mappings({
            @Mapping(source = "testDataMain.id", target = "testDataMainId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping (source = "updateBy.id", target = "updateById")})
    TestDataChild1 toEntity(TestDataChild1DTO dto);


    @Mappings({
            @Mapping(source = "testDataMainId", target = "testDataMain.id"),
            @Mapping (source = "createById", target = "createBy.id"),
            @Mapping (source = "updateById", target = "updateBy.id")})
    TestDataChild1DTO toDTO(TestDataChild1 entity);
}

