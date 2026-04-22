/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.onetomany.service.dto.TestDataChild2DTO;
import com.jeeplus.test.onetomany.domain.TestDataChild2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestDataChild2Wrapper
 * @author 刘高峰
 * @version 2024-09-03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestDataChild2Wrapper extends EntityWrapper<TestDataChild2DTO, TestDataChild2> {

    TestDataChild2Wrapper INSTANCE = Mappers.getMapper(TestDataChild2Wrapper.class);
     @Mappings({
            @Mapping(source = "testDataMain.id", target = "testDataMainId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping (source = "updateBy.id", target = "updateById")})
    TestDataChild2 toEntity(TestDataChild2DTO dto);


    @Mappings({
            @Mapping(source = "testDataMainId", target = "testDataMain.id"),
            @Mapping (source = "createById", target = "createBy.id"),
            @Mapping (source = "updateById", target = "updateBy.id")})
    TestDataChild2DTO toDTO(TestDataChild2 entity);
}

