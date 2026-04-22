/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.controller;

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
import com.jeeplus.test.manytomany.domain.TestStudent;
import com.jeeplus.test.manytomany.service.dto.TestStudentDTO;
import com.jeeplus.test.manytomany.service.mapstruct.TestStudentWrapper;
import com.jeeplus.test.manytomany.service.TestStudentService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 学生Controller
 * @author 刘高峰
 * @version 2024-09-01
 */

@Tag(name = "学生")
@RestController
@RequestMapping(value = "/test/manytomany/testStudent")
public class TestStudentController {

	@Autowired
	private TestStudentService testStudentService;

	@Autowired
	private TestStudentWrapper testStudentWrapper;

	/**
	 * 学生列表数据
	 */
	@ApiLog("查询学生列表数据")
	@Operation(summary = "查询学生列表数据")
	@PreAuthorize("hasAuthority('test:manytomany:testStudent:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestStudentDTO>> list(TestStudentDTO testStudentDTO, Page<TestStudent> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testStudentDTO, TestStudentDTO.class);
		IPage<TestStudentDTO> result = testStudentWrapper.toDTO ( testStudentService.page (page, queryWrapper) );
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取学生数据
	 */
	@ApiLog("根据Id获取学生数据")
	@Operation(summary = "根据Id获取学生数据")
	@PreAuthorize("hasAnyAuthority('test:manytomany:testStudent:view','test:manytomany:testStudent:add','test:manytomany:testStudent:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestStudentDTO> queryById(String id) {
		return ResponseEntity.ok ( testStudentWrapper.toDTO ( testStudentService.getById ( id ) ) );
	}

	/**
	 * 保存学生
	 */
	@ApiLog("保存学生")
	@Operation(summary = "保存学生")
	@PreAuthorize("hasAnyAuthority('test:manytomany:testStudent:add','test:manytomany:testStudent:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestStudentDTO testStudentDTO) {
		//新增或编辑表单保存
		testStudentService.saveOrUpdate (testStudentWrapper.toEntity (testStudentDTO));
        return ResponseEntity.ok ( "保存学生成功" );
	}


	/**
	 * 删除学生
	 */
	@ApiLog("删除学生")
	@Operation(summary = "删除学生")
	@PreAuthorize("hasAuthority('test:manytomany:testStudent:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testStudentService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除学生成功" );
	}
	
    /**
     * 导出学生数据
     *
     * @param testStudentDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出学生数据")
    @Operation(summary = "导出学生数据")
    @PreAuthorize("hasAnyAuthority('test:manytomany:testStudent:export')")
    @GetMapping("export")
    public void exportFile(TestStudentDTO testStudentDTO, Page <TestStudent> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testStudentDTO, TestStudentDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List < TestStudent> result = testStudentService.page ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testStudentService, testStudentWrapper ).exportExcel ( result,  options.getSheetName ( ), TestStudentDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入学生数据
     *
     * @return
     */
    @ApiLog("导入学生数据板")
	@Operation(summary = "导入学生数据")
    @PreAuthorize("hasAnyAuthority('test:manytomany:testStudent:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testStudentService, testStudentWrapper ).importExcel ( file, TestStudentDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入学生数据模板
     *
     * @param response
     * @return
     */
    @ApiLog("下载导入学生数据模板")
	@Operation(summary = "下载导入学生数据模板")
    @PreAuthorize ("hasAnyAuthority('test:manytomany:testStudent:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "学生数据导入模板.xlsx";
        List<TestStudentDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testStudentService, testStudentWrapper ).exportExcel ( list,  "学生数据", TestStudentDTO.class, fileName, null, response );
    }


}
