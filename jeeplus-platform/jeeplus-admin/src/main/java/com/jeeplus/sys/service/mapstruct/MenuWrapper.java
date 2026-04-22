package com.jeeplus.sys.service.mapstruct;

import com.jeeplus.core.mapstruct.TreeWrapper;
import com.jeeplus.sys.domain.Menu;
import com.jeeplus.sys.domain.vo.MenuVO;
import com.jeeplus.sys.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface MenuWrapper extends TreeWrapper <MenuDTO, Menu> {

    MenuWrapper INSTANCE = Mappers.getMapper ( MenuWrapper.class );

    /**
     * entity对象转换成VO对象
     */
    @Mappings({
            @Mapping(source = "href", target = "path"),
            @Mapping(source = "id", target = "meta.id"),
            @Mapping(source = "target", target = "meta.target"),
            @Mapping(source = "icon", target = "meta.icon"),
            @Mapping(source = "type", target = "meta.type"),
            @Mapping(source = "isShow", target = "meta.isShow"),
            @Mapping(source = "hiddenBreadcrumb", target = "meta.hiddenBreadcrumb"),
            @Mapping(source = "affix", target = "meta.affix"),
            @Mapping(source = "name", target = "meta.title")})
    MenuVO toVO(MenuDTO menuDTO);
}
