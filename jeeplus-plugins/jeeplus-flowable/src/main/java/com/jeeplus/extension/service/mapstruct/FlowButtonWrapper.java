package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.FlowButton;
import com.jeeplus.extension.service.dto.FlowButtonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface FlowButtonWrapper extends EntityWrapper <FlowButtonDTO, FlowButton> {

    FlowButtonWrapper INSTANCE = Mappers.getMapper ( FlowButtonWrapper.class );

    @Mappings({
            @Mapping(source = "taskDef.id", target = "taskDefId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping(source = "updateBy.id", target = "updateById")})
    FlowButton toEntity(FlowButtonDTO dto);


    @Mappings({
            @Mapping(source = "taskDefId", target = "taskDef.id"),
            @Mapping(source = "createById", target = "createBy.id"),
            @Mapping(source = "updateById", target = "updateBy.id")})
    FlowButtonDTO toDTO(FlowButton entity);

}
