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
import com.jeeplus.test.manytomany.domain.TestCourse;
import com.jeeplus.test.manytomany.service.dto.TestCourseDTO;
import com.jeeplus.test.manytomany.service.mapstruct.TestCourseWrapper;
import com.jeeplus.test.manytomany.service.TestCourseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 课程Controller
 * @author 刘高峰
 * @version 2024-09-03
 */

@Tag(name = "课程")
@RestController
@RequestMapping(value = "/test/manytomany/testCourse")
public class TestCourseController {

	@Autowired
	private TestCourseService testCourseService;

	@Autowired
	private TestCourseWrapper testCourseWrapper;

	/**
	 * 课程列表数据
	 */
	@ApiLog("查询课程列表数据")
	@Operation(summary = "查询课程列表数据")
	@PreAuthorize("hasAuthority('test:manytomany:testCourse:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestCourseDTO>> list(TestCourseDTO testCourseDTO, Page<TestCourse> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testCourseDTO, TestCourseDTO.class);
		IPage<TestCourseDTO> result = testCourseWrapper.toDTO ( testCourseService.page (page, queryWrapper) );
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取课程数据
	 */
	@ApiLog("根据Id获取课程数据")
	@Operation(summary = "根据Id获取课程数据")
	@PreAuthorize("hasAnyAuthority('test:manytomany:testCourse:view','test:manytomany:testCourse:add','test:manytomany:testCourse:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestCourseDTO> queryById(String id) {
		return ResponseEntity.ok ( testCourseWrapper.toDTO ( testCourseService.getById ( id ) ) );
	}

	/**
	 * 保存课程
	 */
	@ApiLog("保存课程")
	@Operation(summary = "保存课程")
	@PreAuthorize("hasAnyAuthority('test:manytomany:testCourse:add','test:manytomany:testCourse:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestCourseDTO testCourseDTO) {
		//新增或编辑表单保存
		testCourseService.saveOrUpdate (testCourseWrapper.toEntity (testCourseDTO));
        return ResponseEntity.ok ( "保存课程成功" );
	}


	/**
	 * 删除课程
	 */
	@ApiLog("删除课程")
	@Operation(summary = "删除课程")
	@PreAuthorize("hasAuthority('test:manytomany:testCourse:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testCourseService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除课程成功" );
	}
	
    /**
     * 导出课程数据
     *
     * @param testCourseDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出课程数据")
    @Operation(summary = "导出课程数据")
    @PreAuthorize("hasAnyAuthority('test:manytomany:testCourse:export')")
    @GetMapping("export")
    public void exportFile(TestCourseDTO testCourseDTO, Page <TestCourse> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testCourseDTO, TestCourseDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List < TestCourse> result = testCourseService.page ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testCourseService, testCourseWrapper ).exportExcel ( result,  options.getSheetName ( ), TestCourseDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入课程数据
     *
     * @return
     */
    @ApiLog("导入课程数据板")
	@Operation(summary = "导入课程数据")
    @PreAuthorize("hasAnyAuthority('test:manytomany:testCourse:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testCourseService, testCourseWrapper ).importExcel ( file, TestCourseDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入课程数据模板
     *
     * @param response
     * @return
     */
    @ApiLog("下载导入课程数据模板")
	@Operation(summary = "下载导入课程数据模板")
    @PreAuthorize ("hasAnyAuthority('test:manytomany:testCourse:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "课程数据导入模板.xlsx";
        List<TestCourseDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testCourseService, testCourseWrapper ).exportExcel ( list,  "课程数据", TestCourseDTO.class, fileName, null, response );
    }


}
