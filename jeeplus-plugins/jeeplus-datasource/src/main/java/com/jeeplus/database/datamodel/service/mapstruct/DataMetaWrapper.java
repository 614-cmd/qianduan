package com.jeeplus.database.datamodel.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.database.datamodel.domain.DataMeta;
import com.jeeplus.database.datamodel.service.dto.DataMetaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DataMetaWrapper extends EntityWrapper <DataMetaDTO, DataMeta> {

    DataMetaWrapper INSTANCE = Mappers.getMapper ( DataMetaWrapper.class );

    @Mappings({
            @Mapping(source = "dataSetDTO.id", target = "dataSetId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping(source = "updateBy.id", target = "updateById")})
    DataMeta toEntity(DataMetaDTO dto);


    @Mappings({
            @Mapping(source = "dataSetId", target = "dataSetDTO.id"),
            @Mapping(source = "createById", target = "createBy.id"),
            @Mapping(source = "updateById", target = "updateBy.id")})
    DataMetaDTO toDTO(DataMeta entity);

}
