/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.AssignType;
import com.jeeplus.extension.service.AssignTypeService;
import com.jeeplus.extension.service.dto.AssignTypeDTO;
import com.jeeplus.extension.service.mapstruct.AssignTypeWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 代办人类型Controller
 *
 * @author 刘高峰
 * @version 2021-12-26
 */
@Tag(name = "代办人类型")
@RestController
@RequestMapping(value = "/extension/assignType")
public class AssignTypeController {

    @Autowired
    private AssignTypeService assignTypeService;

    @Autowired
    private AssignTypeWrapper assignTypeWrapper;

    /**
     * 代办人类型列表数据
     */
    @ApiLog("查询代办人类型列表数据")
    @Operation(summary = "查询代办人类型列表数据")
    @GetMapping("list")
    public ResponseEntity <IPage <AssignType>> list(AssignTypeDTO assignTypeDTO, Page <AssignType> page) throws Exception {
        QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition ( assignTypeDTO, AssignTypeDTO.class );
        IPage <AssignType> result = assignTypeService.page ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }


    /**
     * 根据Id获取代办人类型数据
     */
    @ApiLog("根据Id获取代办人类型数据")
    @Operation(summary = "根据Id获取代办人类型数据")
    @GetMapping("queryById")
    public ResponseEntity <AssignTypeDTO> queryById(String id) {
        return ResponseEntity.ok ( assignTypeWrapper.toDTO ( assignTypeService.getById ( id ) ) );
    }

    /**
     * 保存代办人类型
     */
    @DemoMode
    @ApiLog("保存代办人类型")
    @Operation(summary = "保存代办人类型")
    @PostMapping("save")
    public ResponseEntity <String> save(@Valid @RequestBody AssignTypeDTO assignTypeDTO) {
        //新增或编辑表单保存
        assignTypeService.saveOrUpdate ( assignTypeWrapper.toEntity ( assignTypeDTO ) );
        return ResponseEntity.ok ( "保存代办人类型成功" );
    }


    /**
     * 删除代办人类型
     */
    @DemoMode
    @ApiLog("删除代办人类型")
    @Operation(summary = "删除代办人类型")
    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String[] idArray = ids.split ( "," );
        assignTypeService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除代办人类型成功" );
    }

}
