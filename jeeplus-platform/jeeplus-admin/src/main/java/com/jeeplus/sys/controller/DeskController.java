/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.sys.domain.Desk;
import com.jeeplus.sys.service.DeskService;
import com.jeeplus.sys.service.dto.DeskDTO;
import com.jeeplus.sys.service.mapstruct.DeskWrapper;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 控制台Controller
 *
 * @author 刘高峰
 * @version 2022-07-19
 */
@Tag(name = "控制台")
@RestController
@RequestMapping(value = "/sys/desk")
public class DeskController {

    @Autowired
    private DeskService deskService;

    @Autowired
    private DeskWrapper deskWrapper;


    /**
     * 根据获取控制台数据
     */
    @ApiLog("根据获取控制台数据")
    @Operation(summary = "根据获取控制台数据")
    @GetMapping("getGridInfo")
    public ResponseEntity <DeskDTO> getGridInfo() {
        Desk desk = deskService.lambdaQuery ( ).eq ( Desk::getCreateById, UserUtils.getCurrentUserDTO ( ).getId ( ) ).one ( );
        if ( desk == null ) {
            desk = new Desk ( );
        }
        return ResponseEntity.ok ( deskWrapper.toDTO ( desk ) );
    }

    /**
     * 保存控制台
     */
    @ApiLog("保存控制台")
    @Operation(summary = "保存控制台")
    @PostMapping("save")
    public ResponseEntity <String> save(@Valid @RequestBody DeskDTO deskDTO) {
        //新增或编辑表单保存
        Desk desk = deskService.lambdaQuery ( ).eq ( Desk::getCreateById, UserUtils.getCurrentUserDTO ( ).getId ( ) ).one ( );
        if ( desk == null ) {
            desk = new Desk ( );
        }
        desk.setGrid ( deskDTO.getGrid ( ) );
        deskService.saveOrUpdate ( desk );
        return ResponseEntity.ok ( "保存控制台成功" );
    }

}
