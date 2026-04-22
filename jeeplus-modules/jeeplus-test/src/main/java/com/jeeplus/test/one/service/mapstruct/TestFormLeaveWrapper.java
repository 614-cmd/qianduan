/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.one.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.one.service.dto.TestFormLeaveDTO;
import com.jeeplus.test.one.domain.TestFormLeave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestFormLeaveWrapper
 * @author 刘高峰
 * @version 2024-09-03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestFormLeaveWrapper extends EntityWrapper<TestFormLeaveDTO, TestFormLeave> {

    TestFormLeaveWrapper INSTANCE = Mappers.getMapper(TestFormLeaveWrapper.class);
     @Mappings({
            @Mapping(source = "office.id", target = "officeId"),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping (source = "updateBy.id", target = "updateById")})
    TestFormLeave toEntity(TestFormLeaveDTO dto);


    @Mappings({
            @Mapping(source = "officeId", target = "office.id"),
            @Mapping(source = "userId", target = "user.id"),
            @Mapping (source = "createById", target = "createBy.id"),
            @Mapping (source = "updateById", target = "updateBy.id")})
    TestFormLeaveDTO toDTO(TestFormLeave entity);
}

