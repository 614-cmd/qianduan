/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.pic.controller;

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
import com.jeeplus.test.pic.domain.TestPic;
import com.jeeplus.test.pic.service.dto.TestPicDTO;
import com.jeeplus.test.pic.service.mapstruct.TestPicWrapper;
import com.jeeplus.test.pic.service.TestPicService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 图片Controller
 * @author 刘高峰
 * @version 2024-09-01
 */

@Tag(name = "图片")
@RestController
@RequestMapping(value = "/test/pic/testPic")
public class TestPicController {

	@Autowired
	private TestPicService testPicService;

	@Autowired
	private TestPicWrapper testPicWrapper;

	/**
	 * 图片列表数据
	 */
	@ApiLog("查询图片列表数据")
	@Operation(summary = "查询图片列表数据")
	@PreAuthorize("hasAuthority('test:pic:testPic:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestPicDTO>> list(TestPicDTO testPicDTO, Page<TestPic> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testPicDTO, TestPicDTO.class);
		IPage<TestPicDTO> result = testPicWrapper.toDTO ( testPicService.page (page, queryWrapper) );
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取图片数据
	 */
	@ApiLog("根据Id获取图片数据")
	@Operation(summary = "根据Id获取图片数据")
	@PreAuthorize("hasAnyAuthority('test:pic:testPic:view','test:pic:testPic:add','test:pic:testPic:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestPicDTO> queryById(String id) {
		return ResponseEntity.ok ( testPicWrapper.toDTO ( testPicService.getById ( id ) ) );
	}

	/**
	 * 保存图片
	 */
	@ApiLog("保存图片")
	@Operation(summary = "保存图片")
	@PreAuthorize("hasAnyAuthority('test:pic:testPic:add','test:pic:testPic:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestPicDTO testPicDTO) {
		//新增或编辑表单保存
		testPicService.saveOrUpdate (testPicWrapper.toEntity (testPicDTO));
        return ResponseEntity.ok ( "保存图片成功" );
	}


	/**
	 * 删除图片
	 */
	@ApiLog("删除图片")
	@Operation(summary = "删除图片")
	@PreAuthorize("hasAuthority('test:pic:testPic:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testPicService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除图片成功" );
	}
	
    /**
     * 导出图片数据
     *
     * @param testPicDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出图片数据")
    @Operation(summary = "导出图片数据")
    @PreAuthorize("hasAnyAuthority('test:pic:testPic:export')")
    @GetMapping("export")
    public void exportFile(TestPicDTO testPicDTO, Page <TestPic> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testPicDTO, TestPicDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List < TestPic> result = testPicService.page ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testPicService, testPicWrapper ).exportExcel ( result,  options.getSheetName ( ), TestPicDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入图片数据
     *
     * @return
     */
    @ApiLog("导入图片数据板")
	@Operation(summary = "导入图片数据")
    @PreAuthorize("hasAnyAuthority('test:pic:testPic:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testPicService, testPicWrapper ).importExcel ( file, TestPicDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入图片数据模板
     *
     * @param response
     * @return
     */
    @ApiLog("下载导入图片数据模板")
	@Operation(summary = "下载导入图片数据模板")
    @PreAuthorize ("hasAnyAuthority('test:pic:testPic:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "图片数据导入模板.xlsx";
        List<TestPicDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testPicService, testPicWrapper ).exportExcel ( list,  "图片数据", TestPicDTO.class, fileName, null, response );
    }


}
