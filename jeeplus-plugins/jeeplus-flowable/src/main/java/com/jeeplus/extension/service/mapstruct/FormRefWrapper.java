/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.extension.domain.FormRef;
import com.jeeplus.extension.service.dto.FormDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  FormDataWrapper
 * @author 刘高峰
 * @version 2024-06-25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface FormRefWrapper extends EntityWrapper<FormDataDTO, FormRef> {

    FormRefWrapper INSTANCE = Mappers.getMapper( FormRefWrapper.class);
}

