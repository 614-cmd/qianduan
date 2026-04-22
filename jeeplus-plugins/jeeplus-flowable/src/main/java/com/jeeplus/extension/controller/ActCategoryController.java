/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.extension.domain.ActCategory;
import com.jeeplus.extension.service.ActCategoryService;
import com.jeeplus.extension.service.dto.ActCategoryDTO;
import com.jeeplus.extension.service.mapstruct.ActCategoryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 流程分类Controller
 *
 * @author 刘高峰
 * @version 2021-10-09
 */
@Tag(name = "流程分类")
@RestController
@RequestMapping("/extension/actCategory")
public class ActCategoryController {

    @Autowired
    private ActCategoryService actCategoryService;
    @Autowired
    private ActCategoryWrapper actCategoryWrapper;

    /**
     * 流程分类树表数据
     */
    @ApiLog("流程分类树表数据")
    @Operation(summary = "流程分类树表数据")
    @GetMapping("list")
    public ResponseEntity list() {
        return ResponseEntity.ok ( actCategoryService.list ( ) );
    }

    /**
     * 根据id查询实例
     *
     * @param id
     * @return
     */
    @ApiLog("根据id查询实例")
    @Operation(summary = "根据id查询实例")
    @GetMapping("queryById")
    public ResponseEntity queryById(String id) {
        return ResponseEntity.ok ( actCategoryWrapper.toDTO ( actCategoryService.getById ( id ) ) );
    }

    /**
     * 保存流程分类
     */
    @ApiLog("保存流程分类")
    @Operation(summary = "保存流程分类")
    @PostMapping("save")
    public ResponseEntity save(@RequestBody @Valid ActCategoryDTO actCategoryDTO) {
        //新增或编辑表单保存
        ActCategory actCategory = actCategoryWrapper.toEntity ( actCategoryDTO );
        actCategoryService.saveOrUpdate ( actCategory );//保存
        return ResponseEntity.ok ( actCategory );
    }

    /**
     * 移动流程节点
     *
     * @param dragDTO
     * @return
     */
    @ApiLog("移动流程节点")
    @Operation(summary = "移动流程节点")
    @PostMapping("drag")
    public ResponseEntity drag(@Valid @RequestBody DragNode <ActCategory> dragDTO) {
        actCategoryService.sortList ( dragDTO );
        return ResponseEntity.ok ( "移动节点成功!" );
    }

    /**
     * 删除流程分类
     */
    @ApiLog("删除流程分类")
    @Operation(summary = "删除流程分类")
    @DeleteMapping("delete")
    public ResponseEntity delete(String ids) {
        String[] idArray = ids.split ( "," );
        actCategoryService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除流程分类成功" );
    }

    /**
     * 获取JSON树形数据。
     *
     * @param extId 排除的ID
     * @return
     */
    @ApiLog("获取JSON树形数据")
    @Operation(summary = "获取JSON树形数据")
    @GetMapping("treeData")
    public ResponseEntity treeData(@RequestParam(required = false) String extId) {
        List <ActCategory> list = actCategoryService.treeData ( extId );
        return ResponseEntity.ok ( list );
    }


}
