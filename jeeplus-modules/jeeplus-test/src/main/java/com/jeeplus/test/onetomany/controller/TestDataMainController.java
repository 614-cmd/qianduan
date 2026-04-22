/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.onetomany.controller;

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
import com.jeeplus.test.onetomany.service.dto.TestDataMainDTO;
import com.jeeplus.test.onetomany.service.mapstruct.TestDataMainWrapper;
import com.jeeplus.test.onetomany.service.TestDataMainService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 票务代理Controller
 * @author 刘高峰
 * @version 2024-09-03
 */

@Tag(name = "票务代理")
@RestController
@RequestMapping(value = "/test/onetomany/testDataMain")
public class TestDataMainController {

	@Autowired
	private TestDataMainService testDataMainService;

	@Autowired
	private TestDataMainWrapper testDataMainWrapper;

	/**
	 * 票务代理列表数据
	 */
	@ApiLog("查询票务代理列表数据")
	@Operation(summary = "查询票务代理列表数据")
	@PreAuthorize("hasAuthority('test:onetomany:testDataMain:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestDataMainDTO>> list(TestDataMainDTO testDataMainDTO, Page<TestDataMainDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testDataMainDTO, TestDataMainDTO.class);
		IPage<TestDataMainDTO> result = testDataMainService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取票务代理数据
	 */
	@ApiLog("根据Id获取票务代理数据")
	@Operation(summary = "根据Id获取票务代理数据")
	@PreAuthorize("hasAnyAuthority('test:onetomany:testDataMain:view','test:onetomany:testDataMain:add','test:onetomany:testDataMain:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestDataMainDTO> queryById(String id) {
		return ResponseEntity.ok ( testDataMainService.findById ( id ) );
	}

	/**
	 * 保存票务代理
	 */
	@ApiLog("保存票务代理")
	@Operation(summary = "保存票务代理")
	@PreAuthorize("hasAnyAuthority('test:onetomany:testDataMain:add','test:onetomany:testDataMain:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestDataMainDTO testDataMainDTO) {
		//新增或编辑表单保存
		testDataMainService.saveOrUpdate (testDataMainDTO);
        return ResponseEntity.ok ( "保存票务代理成功" );
	}


	/**
	 * 删除票务代理
	 */
	@ApiLog("删除票务代理")
	@Operation(summary = "删除票务代理")
	@PreAuthorize("hasAuthority('test:onetomany:testDataMain:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			testDataMainService.removeById ( id );
		}
		return ResponseEntity.ok( "删除票务代理成功" );
	}
    /**
     * 导出票务代理数据
     *
     * @param testDataMainDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出票务代理数据")
    @Operation(summary = "导出票务代理数据")
    @PreAuthorize("hasAnyAuthority('test:onetomany:testDataMain:export')")
    @GetMapping("export")
    public void exportFile(TestDataMainDTO testDataMainDTO, Page <TestDataMainDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testDataMainDTO, TestDataMainDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<TestDataMainDTO> result = testDataMainService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testDataMainService, testDataMainWrapper ).exportExcel ( result,  options.getSheetName ( ), TestDataMainDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入票务代理数据
     *
     * @return
     */
    @ApiLog("导入票务代理数据")
    @Operation(summary = "导入票务代理数据") 
    @PreAuthorize("hasAnyAuthority('test:onetomany:testDataMain:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testDataMainService, testDataMainWrapper ).importExcel ( file, TestDataMainDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入票务代理数据模板
     *
     * @param response
     * @return
     */
    @ApiLog("下载导入票务代理数据模板")
    @Operation(summary = "下载导入票务代理数据模板")  
    @PreAuthorize ("hasAnyAuthority('test:onetomany:testDataMain:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "票务代理数据导入模板.xlsx";
        List<TestDataMainDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testDataMainService, testDataMainWrapper ).exportExcel ( list,  "票务代理数据", TestDataMainDTO.class, fileName, null, response );
    }


}
