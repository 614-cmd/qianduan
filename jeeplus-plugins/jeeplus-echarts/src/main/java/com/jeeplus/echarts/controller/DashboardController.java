/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.echarts.domain.Dashboard;
import com.jeeplus.echarts.service.DashboardService;
import com.jeeplus.echarts.service.dto.DashboardDTO;
import com.jeeplus.echarts.service.mapstruct.DashboardWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 图表看板Controller
 *
 * @author 刘高峰
 * @version 2022-07-20
 */
@Tag(name = "图表看板")
@RestController
@RequestMapping(value = "/echarts/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private DashboardWrapper dashboardWrapper;

    /**
     * 图表看板列表数据
     */
    @ApiLog("查询图表看板列表数据")
    @PreAuthorize("hasAuthority('echarts:dashboard:list')")
    @GetMapping("list")
    public ResponseEntity <IPage <Dashboard>> list(DashboardDTO dashboardDTO, Page <Dashboard> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition ( dashboardDTO, DashboardDTO.class );
        IPage <Dashboard> result = dashboardService.page ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }


    /**
     * 根据Id获取图表看板数据
     */
    @ApiLog("根据Id获取图表看板数据")
    @PreAuthorize("hasAnyAuthority('echarts:dashboard:view','echarts:dashboard:add','echarts:dashboard:edit')")
    @GetMapping("queryById")
    public ResponseEntity <DashboardDTO> queryById(String id) {
        return ResponseEntity.ok ( dashboardWrapper.toDTO ( dashboardService.getById ( id ) ) );
    }

    /**
     * 保存图表看板
     */
    @ApiLog("保存图表看板")
    @PreAuthorize("hasAnyAuthority('echarts:dashboard:add','echarts:dashboard:edit')")
    @PostMapping("save")
    public ResponseEntity <Dashboard> save(@Valid @RequestBody DashboardDTO dashboardDTO) {
        //新增或编辑表单保存
        Dashboard dashboard = dashboardWrapper.toEntity ( dashboardDTO );
        dashboardService.saveOrUpdate ( dashboard );
        return ResponseEntity.ok ( dashboard );
    }


    /**
     * 删除图表看板
     */
    @ApiLog("删除图表看板")
    @PreAuthorize("hasAuthority('echarts:dashboard:del')")
    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String[] idArray = ids.split ( "," );
        dashboardService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除图表看板成功" );
    }

}
