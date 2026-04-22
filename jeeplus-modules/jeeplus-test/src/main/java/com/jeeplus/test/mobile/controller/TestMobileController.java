/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.mobile.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.mobile.service.dto.TestMobileDTO;
import com.jeeplus.test.mobile.service.mapstruct.TestMobileWrapper;
import com.jeeplus.test.mobile.service.TestMobileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 移动表单Controller
 * @author 刘高峰
 * @version 2023-03-11
 */

@Tag(name ="移动表单")
@RestController
@RequestMapping(value = "/test/mobile/testMobile")
public class TestMobileController {

	@Autowired
	private TestMobileService testMobileService;

	@Autowired
	private TestMobileWrapper testMobileWrapper;

	/**
	 * 移动表单列表数据
	 */
	@ApiLog("查询移动表单列表数据")
	@Operation(summary ="查询移动表单列表数据")
	@GetMapping("list")
	public ResponseEntity<IPage<TestMobileDTO>> list(TestMobileDTO testMobileDTO, Page<TestMobileDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testMobileDTO, TestMobileDTO.class);
		IPage<TestMobileDTO> result = testMobileService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取移动表单数据
	 */
	@ApiLog("根据Id获取移动表单数据")
	@Operation(summary ="根据Id获取移动表单数据")
	@GetMapping("queryById")
	public ResponseEntity<TestMobileDTO> queryById(String id) {
		return ResponseEntity.ok ( testMobileService.findById ( id ) );
	}

	/**
	 * 保存移动表单
	 */
	@ApiLog("保存移动表单")
	@Operation(summary ="保存移动表单")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestMobileDTO testMobileDTO) {
		//新增或编辑表单保存
		testMobileService.saveOrUpdate (testMobileWrapper.toEntity (testMobileDTO));
        return ResponseEntity.ok ( "保存移动表单成功" );
	}


	/**
	 * 删除移动表单
	 */
	@ApiLog("删除移动表单")
	@Operation(summary ="删除移动表单")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String[] idArray = ids.split(",");
        testMobileService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除移动表单成功" );
	}

    /**
     * 导出移动表单数据
     *
     * @param testMobileDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出移动表单数据")
    @GetMapping("export")
    public void exportFile(TestMobileDTO testMobileDTO, Page <TestMobileDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testMobileDTO, TestMobileDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据

        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<TestMobileDTO> result = testMobileService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testMobileService, testMobileWrapper ).exportExcel ( result,  options.getSheetName ( ), TestMobileDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入移动表单数据
     *
     * @return
     */
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testMobileService, testMobileWrapper ).importExcel ( file, TestMobileDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入移动表单数据模板
     *
     * @param response
     * @return
     */
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "移动表单数据导入模板.xlsx";
        List<TestMobileDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testMobileService, testMobileWrapper ).exportExcel ( list,  "移动表单数据", TestMobileDTO.class, fileName, null, response );
    }


}
