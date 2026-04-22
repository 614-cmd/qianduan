/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.echarts.domain.Dashboard;
import com.jeeplus.echarts.service.dto.DashboardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * DashboardWrapper
 *
 * @author 刘高峰
 * @version 2022-07-20
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface DashboardWrapper extends EntityWrapper <DashboardDTO, Dashboard> {

    DashboardWrapper INSTANCE = Mappers.getMapper ( DashboardWrapper.class );
}

