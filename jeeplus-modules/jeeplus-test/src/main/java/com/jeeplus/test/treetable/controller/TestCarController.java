/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.excel.EasyExcelUtils;
import com.jeeplus.core.excel.ExcelOptions;
import com.jeeplus.core.excel.annotation.ExportMode;
import com.jeeplus.core.query.QueryWrapperGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.treetable.service.dto.TestCarDTO;
import com.jeeplus.test.treetable.service.mapstruct.TestCarWrapper;
import com.jeeplus.test.treetable.service.TestCarService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 车辆Controller
 * @author 刘高峰
 * @version 2024-09-01
 */

@Tag(name = "车辆")
@RestController
@RequestMapping(value = "/test/treetable/testCar")
public class TestCarController {

	@Autowired
	private TestCarService testCarService;

	@Autowired
	private TestCarWrapper testCarWrapper;

	/**
	 * 车辆列表数据
	 */
	@ApiLog("查询车辆列表数据")
	@Operation(summary = "查询车辆列表数据")
	@PreAuthorize("hasAuthority('test:treetable:testCar:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestCarDTO>> list(TestCarDTO testCarDTO, Page<TestCarDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testCarDTO, TestCarDTO.class);
		IPage<TestCarDTO> result = testCarService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取车辆数据
	 */
	@ApiLog("根据Id获取车辆数据")
	@Operation(summary = "根据Id获取车辆数据")
	@PreAuthorize("hasAnyAuthority('test:treetable:testCar:view','test:treetable:testCar:add','test:treetable:testCar:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestCarDTO> queryById(String id) {
		return ResponseEntity.ok ( testCarService.findById ( id ) );
	}

	/**
	 * 保存车辆
	 */
	@ApiLog("保存车辆")
	@Operation(summary = "保存车辆")
	@PreAuthorize("hasAnyAuthority('test:treetable:testCar:add','test:treetable:testCar:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestCarDTO testCarDTO) {
		//新增或编辑表单保存
		testCarService.saveOrUpdate (testCarWrapper.toEntity (testCarDTO));
        return ResponseEntity.ok ( "保存车辆成功" );
	}


	/**
	 * 删除车辆
	 */
	@ApiLog("删除车辆")
	@Operation(summary = "删除车辆")
	@PreAuthorize("hasAuthority('test:treetable:testCar:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testCarService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除车辆成功" );
	}
	
    /**
     * 导出车辆数据
     *
     * @param testCarDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出车辆数据")
    @Operation(summary = "导出车辆数据")
    @PreAuthorize("hasAnyAuthority('test:treetable:testCar:export')")
    @GetMapping("export")
    public void exportFile(TestCarDTO testCarDTO, Page <TestCarDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testCarDTO, TestCarDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<TestCarDTO> result = testCarService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testCarService, testCarWrapper ).exportExcel ( result,  options.getSheetName ( ), TestCarDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入车辆数据
     *
     * @return
     */
    @ApiLog("导入车辆数据")
    @Operation(summary = "导入车辆数据") 
    @PreAuthorize("hasAnyAuthority('test:treetable:testCar:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testCarService, testCarWrapper ).importExcel ( file, TestCarDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入车辆数据模板
     *
     * @param response
     * @return
     */
    @ApiLog("下载导入车辆数据模板")
    @Operation(summary = "下载导入车辆数据模板")
    @PreAuthorize ("hasAnyAuthority('test:treetable:testCar:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "车辆数据导入模板.xlsx";
        List<TestCarDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testCarService, testCarWrapper ).exportExcel ( list,  "车辆数据", TestCarDTO.class, fileName, null, response );
    }


}
