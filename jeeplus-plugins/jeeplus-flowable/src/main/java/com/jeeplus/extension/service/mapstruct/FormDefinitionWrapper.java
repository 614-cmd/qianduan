package com.jeeplus.extension.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.FormDefinition;
import com.jeeplus.extension.service.dto.FormDefinitionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface FormDefinitionWrapper extends EntityWrapper <FormDefinitionDTO, FormDefinition> {

    FormDefinitionWrapper INSTANCE = Mappers.getMapper ( FormDefinitionWrapper.class );


    @Mappings({
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping(source = "updateBy.id", target = "updateById"),
            @Mapping(source = "dataSource.id", target = "dataSourceId"),
            @Mapping(source = "category.id", target = "categoryId")})
    FormDefinition toEntity(FormDefinitionDTO dto);


    @Mappings({
            @Mapping(source = "createById", target = "createBy.id"),
            @Mapping(source = "updateById", target = "updateBy.id"),
            @Mapping(source = "dataSourceId", target = "dataSource.id"),
            @Mapping(source = "categoryId", target = "category.id")})
    FormDefinitionDTO toDTO(FormDefinition entity);

}
