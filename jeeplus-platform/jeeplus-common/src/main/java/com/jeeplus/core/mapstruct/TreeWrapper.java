package com.jeeplus.core.mapstruct;

import com.jeeplus.core.dto.DragNode;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface TreeWrapper<D, E> extends EntityWrapper <D, E> {

    @Mappings({
            @Mapping(source = "parent.id", target = "parentId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping(source = "updateBy.id", target = "updateById")})
    E toEntity(D dto);


    @Mappings({
            @Mapping(source = "parentId", target = "parent.id"),
            @Mapping(source = "createById", target = "createBy.id"),
            @Mapping(source = "updateById", target = "updateBy.id")})
    D toDTO(E entity);

    E copyEntity(E entity);

    D copyDTO(D dto);


    DragNode <E> toEntity(DragNode <D> dDragDTO);

}
