/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.validation.controller;

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
import com.jeeplus.test.validation.domain.TestValidation;
import com.jeeplus.test.validation.service.dto.TestValidationDTO;
import com.jeeplus.test.validation.service.mapstruct.TestValidationWrapper;
import com.jeeplus.test.validation.service.TestValidationService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 测试校验功能Controller
 * @author 刘高峰
 * @version 2024-09-01
 */

@Tag(name = "测试校验功能")
@RestController
@RequestMapping(value = "/test/validation/testValidation")
public class TestValidationController {

	@Autowired
	private TestValidationService testValidationService;

	@Autowired
	private TestValidationWrapper testValidationWrapper;

	/**
	 * 测试校验功能列表数据
	 */
	@ApiLog("查询测试校验功能列表数据")
	@Operation(summary = "查询测试校验功能列表数据")
	@PreAuthorize("hasAuthority('test:validation:testValidation:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestValidationDTO>> list(TestValidationDTO testValidationDTO, Page<TestValidation> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testValidationDTO, TestValidationDTO.class);
		IPage<TestValidationDTO> result = testValidationWrapper.toDTO ( testValidationService.page (page, queryWrapper) );
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取测试校验功能数据
	 */
	@ApiLog("根据Id获取测试校验功能数据")
	@Operation(summary = "根据Id获取测试校验功能数据")
	@PreAuthorize("hasAnyAuthority('test:validation:testValidation:view','test:validation:testValidation:add','test:validation:testValidation:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestValidationDTO> queryById(String id) {
		return ResponseEntity.ok ( testValidationWrapper.toDTO ( testValidationService.getById ( id ) ) );
	}

	/**
	 * 保存测试校验功能
	 */
	@ApiLog("保存测试校验功能")
	@Operation(summary = "保存测试校验功能")
	@PreAuthorize("hasAnyAuthority('test:validation:testValidation:add','test:validation:testValidation:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestValidationDTO testValidationDTO) {
		//新增或编辑表单保存
		testValidationService.saveOrUpdate (testValidationWrapper.toEntity (testValidationDTO));
        return ResponseEntity.ok ( "保存测试校验功能成功" );
	}


	/**
	 * 删除测试校验功能
	 */
	@ApiLog("删除测试校验功能")
	@Operation(summary = "删除测试校验功能")
	@PreAuthorize("hasAuthority('test:validation:testValidation:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testValidationService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除测试校验功能成功" );
	}
	
    /**
     * 导出测试校验功能数据
     *
     * @param testValidationDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出测试校验功能数据")
    @Operation(summary = "导出测试校验功能数据")
    @PreAuthorize("hasAnyAuthority('test:validation:testValidation:export')")
    @GetMapping("export")
    public void exportFile(TestValidationDTO testValidationDTO, Page <TestValidation> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testValidationDTO, TestValidationDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List < TestValidation> result = testValidationService.page ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testValidationService, testValidationWrapper ).exportExcel ( result,  options.getSheetName ( ), TestValidationDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入测试校验功能数据
     *
     * @return
     */
    @ApiLog("导入测试校验功能数据板")
	@Operation(summary = "导入测试校验功能数据")
    @PreAuthorize("hasAnyAuthority('test:validation:testValidation:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testValidationService, testValidationWrapper ).importExcel ( file, TestValidationDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入测试校验功能数据模板
     *
     * @param response
     * @return
     */
    @ApiLog("下载导入测试校验功能数据模板")
	@Operation(summary = "下载导入测试校验功能数据模板")
    @PreAuthorize ("hasAnyAuthority('test:validation:testValidation:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "测试校验功能数据导入模板.xlsx";
        List<TestValidationDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testValidationService, testValidationWrapper ).exportExcel ( list,  "测试校验功能数据", TestValidationDTO.class, fileName, null, response );
    }


}
