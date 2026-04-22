/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.extension.domain.FormCategory;
import com.jeeplus.extension.service.FormCategoryService;
import com.jeeplus.extension.service.dto.FormCategoryDTO;
import com.jeeplus.extension.service.mapstruct.FormCategoryWrapper;
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
 * @version 2021-02-02
 */
@Tag(name = "流程分类")
@RestController
@RequestMapping(value = "/extension/formCategory")
public class FormCategoryController {

    @Autowired
    private FormCategoryService formCategoryService;
    @Autowired
    private FormCategoryWrapper formCategoryWrapper;

    /**
     * 流程分类树表数据
     */
    @ApiLog("流程分类树表数据")
    @Operation(summary = "流程分类树表数据")
    @GetMapping("list")
    public ResponseEntity list(FormCategory formCategory, Page <FormCategory> page) throws Exception {
        QueryWrapper <FormCategory> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( formCategory, FormCategory.class );
        IPage <FormCategory> result = formCategoryService.page ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }


    /**
     * 根据Id获取流程分类数据
     */
    @ApiLog("根据Id获取流程分类数据")
    @Operation(summary = "根据Id获取流程分类数据")
    @GetMapping("queryById")
    public ResponseEntity <FormCategoryDTO> queryById(String id) {
        return ResponseEntity.ok ( formCategoryWrapper.toDTO ( formCategoryService.getById ( id ) ) );
    }

    /**
     * 保存流程分类
     */
    @ApiLog("保存流程分类")
    @Operation(summary = "保存流程分类")
    @PostMapping("save")
    public ResponseEntity save(@RequestBody FormCategoryDTO formCategoryDTO) {
        //新增或编辑表单保存
        formCategoryService.saveOrUpdate ( formCategoryWrapper.toEntity ( formCategoryDTO ) );//保存
        return ResponseEntity.ok ( "保存流程分类成功" );
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
    public ResponseEntity drag(@Valid @RequestBody DragNode <FormCategory> dragDTO) {
        formCategoryService.sortList ( dragDTO );
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
        formCategoryService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
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
        List rootTree = formCategoryService.treeData ( extId );
        return ResponseEntity.ok ( rootTree );
    }

}
