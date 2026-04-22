/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.sys.domain.Desk;
import com.jeeplus.sys.service.dto.DeskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * DeskWrapper
 *
 * @author 刘高峰
 * @version 2022-07-19
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DeskWrapper extends EntityWrapper <DeskDTO, Desk> {

    DeskWrapper INSTANCE = Mappers.getMapper ( DeskWrapper.class );
}

