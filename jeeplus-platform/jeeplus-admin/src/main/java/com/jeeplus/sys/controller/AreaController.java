/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import com.google.common.collect.Lists;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.sys.domain.Area;
import com.jeeplus.sys.service.AreaService;
import com.jeeplus.sys.service.dto.AreaDTO;
import com.jeeplus.sys.service.mapstruct.AreaWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 区域Controller
 *
 * @author jeeplus
 * @version 2021-5-15
 */
@Tag(name = "区域管理")
@RestController
@RequestMapping("/sys/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private AreaWrapper areaWrapper;

    /**
     * 查询区域列表
     *
     * @return
     */
    @ApiLog("查询区域列表")
    @Operation(summary = "查询区域列表")
    @PreAuthorize("hasAuthority('sys:area:list')")
    @GetMapping("list")
    public ResponseEntity <List <Area>> list() {
        return ResponseEntity.ok ( ).body ( areaService.findAll ( ) );
    }

    /**
     * 根据id查询区域
     *
     * @param id
     * @return
     */
    @ApiLog("查询区域")
    @Operation(summary = "查询区域")
    @PreAuthorize("hasAnyAuthority('sys:area:view', 'sys:area:add', 'sys:area:edit')")
    @GetMapping("queryById")
    public ResponseEntity <AreaDTO> queryById(String id) {

        return ResponseEntity.ok ( areaWrapper.toDTO ( areaService.getById ( id ) ) );
    }

    /**
     * 保存区域
     *
     * @param areaDTO
     * @return
     */
    @DemoMode
    @ApiLog("保存区域")
    @Operation(summary = "保存区域")
    @PreAuthorize("hasAnyAuthority('sys:area:add', 'sys:area:edit')")
    @PostMapping("save")
    public ResponseEntity <Area> save(@RequestBody AreaDTO areaDTO) {
        Area area = areaWrapper.toEntity ( areaDTO );
        areaService.saveOrUpdate ( area );
        return ResponseEntity.ok ( area );
    }

    /**
     * 移动区域节点
     *
     * @param dragDTO
     * @return
     */
    @ApiLog("移动区域节点")
    @Operation(summary = "移动区域节点")
    @PostMapping("drag")
    public ResponseEntity drag(@Valid @RequestBody DragNode <Area> dragDTO) {
        areaService.sortList ( dragDTO );
        return ResponseEntity.ok ( "移动节点成功!" );
    }


    /**
     * 删除区域
     *
     * @param ids
     * @return
     */
    @DemoMode
    @ApiLog("删除区域")
    @Operation(summary = "删除区域")
    @PreAuthorize("hasAuthority('sys:area:del')")
    @DeleteMapping("delete")
    public ResponseEntity <String> delete(String ids) {
        String[] idArray = ids.split ( "," );
        areaService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除区域成功！" );
    }

    /**
     * 获取区域JSON数据。
     *
     * @param extId 排除的ID
     * @return
     */
    @ApiLog("获取区域数据")
    @Operation(summary = "获取区域数据")
    @GetMapping("treeData")
    public ResponseEntity <List <Area>> treeData(@RequestParam(required = false) String extId) {
        List <Area> rootTree = areaService.treeData ( extId );
        return ResponseEntity.ok ( rootTree );
    }

}
