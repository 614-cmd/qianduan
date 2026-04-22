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
import com.jeeplus.test.manytomany.service.dto.TestStudentCourseDTO;
import com.jeeplus.test.manytomany.service.mapstruct.TestStudentCourseWrapper;
import com.jeeplus.test.manytomany.service.TestStudentCourseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 学生课程记录Controller
 * @author 刘高峰
 * @version 2024-09-01
 */

@Tag(name = "学生课程记录")
@RestController
@RequestMapping(value = "/test/manytomany/testStudentCourse")
public class TestStudentCourseController {

	@Autowired
	private TestStudentCourseService testStudentCourseService;

	@Autowired
	private TestStudentCourseWrapper testStudentCourseWrapper;

	/**
	 * 学生课程记录列表数据
	 */
	@ApiLog("查询学生课程记录列表数据")
	@Operation(summary = "查询学生课程记录列表数据")
	@PreAuthorize("hasAuthority('test:manytomany:testStudentCourse:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestStudentCourseDTO>> list(TestStudentCourseDTO testStudentCourseDTO, Page<TestStudentCourseDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testStudentCourseDTO, TestStudentCourseDTO.class);
		IPage<TestStudentCourseDTO> result = testStudentCourseService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取学生课程记录数据
	 */
	@ApiLog("根据Id获取学生课程记录数据")
	@Operation(summary = "根据Id获取学生课程记录数据")
	@PreAuthorize("hasAnyAuthority('test:manytomany:testStudentCourse:view','test:manytomany:testStudentCourse:add','test:manytomany:testStudentCourse:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestStudentCourseDTO> queryById(String id) {
		return ResponseEntity.ok ( testStudentCourseService.findById ( id ) );
	}

	/**
	 * 保存学生课程记录
	 */
	@ApiLog("保存学生课程记录")
	@Operation(summary = "保存学生课程记录")
	@PreAuthorize("hasAnyAuthority('test:manytomany:testStudentCourse:add','test:manytomany:testStudentCourse:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestStudentCourseDTO testStudentCourseDTO) {
		//新增或编辑表单保存
		testStudentCourseService.saveOrUpdate (testStudentCourseWrapper.toEntity (testStudentCourseDTO));
        return ResponseEntity.ok ( "保存学生课程记录成功" );
	}


	/**
	 * 删除学生课程记录
	 */
	@ApiLog("删除学生课程记录")
	@Operation(summary = "删除学生课程记录")
	@PreAuthorize("hasAuthority('test:manytomany:testStudentCourse:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testStudentCourseService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除学生课程记录成功" );
	}
	
    /**
     * 导出学生课程记录数据
     *
     * @param testStudentCourseDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出学生课程记录数据")
    @Operation(summary = "导出学生课程记录数据")
    @PreAuthorize("hasAnyAuthority('test:manytomany:testStudentCourse:export')")
    @GetMapping("export")
    public void exportFile(TestStudentCourseDTO testStudentCourseDTO, Page <TestStudentCourseDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testStudentCourseDTO, TestStudentCourseDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<TestStudentCourseDTO> result = testStudentCourseService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testStudentCourseService, testStudentCourseWrapper ).exportExcel ( result,  options.getSheetName ( ), TestStudentCourseDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入学生课程记录数据
     *
     * @return
     */
    @ApiLog("导入学生课程记录数据板")
	@Operation(summary = "导入学生课程记录数据")
    @PreAuthorize("hasAnyAuthority('test:manytomany:testStudentCourse:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testStudentCourseService, testStudentCourseWrapper ).importExcel ( file, TestStudentCourseDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入学生课程记录数据模板
     *
     * @param response
     * @return
     */
    @ApiLog("下载导入学生课程记录数据模板")
	@Operation(summary = "下载导入学生课程记录数据模板")
    @PreAuthorize ("hasAnyAuthority('test:manytomany:testStudentCourse:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "学生课程记录数据导入模板.xlsx";
        List<TestStudentCourseDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testStudentCourseService, testStudentCourseWrapper ).exportExcel ( list,  "学生课程记录数据", TestStudentCourseDTO.class, fileName, null, response );
    }


}
