/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.treetable.service.dto.TestCarDTO;
import com.jeeplus.test.treetable.domain.TestCar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestCarWrapper
 * @author 刘高峰
 * @version 2024-09-01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestCarWrapper extends EntityWrapper<TestCarDTO, TestCar> {

    TestCarWrapper INSTANCE = Mappers.getMapper(TestCarWrapper.class);
     @Mappings({
            @Mapping(source = "kind.id", target = "kindId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping (source = "updateBy.id", target = "updateById")})
    TestCar toEntity(TestCarDTO dto);


    @Mappings({
            @Mapping(source = "kindId", target = "kind.id"),
            @Mapping (source = "createById", target = "createBy.id"),
            @Mapping (source = "updateById", target = "updateBy.id")})
    TestCarDTO toDTO(TestCar entity);
}

