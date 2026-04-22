/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.controller;

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
import com.jeeplus.test.shop.service.dto.TestGoodsDTO;
import com.jeeplus.test.shop.service.mapstruct.TestGoodsWrapper;
import com.jeeplus.test.shop.service.TestGoodsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 商品Controller
 * @author 刘高峰
 * @version 2024-09-03
 */

@Tag(name = "商品")
@RestController
@RequestMapping(value = "/test/shop/testGoods")
public class TestGoodsController {

	@Autowired
	private TestGoodsService testGoodsService;

	@Autowired
	private TestGoodsWrapper testGoodsWrapper;

	/**
	 * 商品列表数据
	 */
	@ApiLog("查询商品列表数据")
	@Operation(summary = "查询商品列表数据")
	@PreAuthorize("hasAuthority('test:shop:testGoods:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<TestGoodsDTO>> list(TestGoodsDTO testGoodsDTO, Page<TestGoodsDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testGoodsDTO, TestGoodsDTO.class);
		IPage<TestGoodsDTO> result = testGoodsService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取商品数据
	 */
	@ApiLog("根据Id获取商品数据")
	@Operation(summary = "根据Id获取商品数据")
	@PreAuthorize("hasAnyAuthority('test:shop:testGoods:view','test:shop:testGoods:add','test:shop:testGoods:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestGoodsDTO> queryById(String id) {
		return ResponseEntity.ok ( testGoodsService.findById ( id ) );
	}

	/**
	 * 保存商品
	 */
	@ApiLog("保存商品")
	@Operation(summary = "保存商品")
	@PreAuthorize("hasAnyAuthority('test:shop:testGoods:add','test:shop:testGoods:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestGoodsDTO testGoodsDTO) {
		//新增或编辑表单保存
		testGoodsService.saveOrUpdate (testGoodsWrapper.toEntity (testGoodsDTO));
        return ResponseEntity.ok ( "保存商品成功" );
	}


	/**
	 * 删除商品
	 */
	@ApiLog("删除商品")
	@Operation(summary = "删除商品")
	@PreAuthorize("hasAuthority('test:shop:testGoods:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testGoodsService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除商品成功" );
	}
	
    /**
     * 导出商品数据
     *
     * @param testGoodsDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出商品数据")
    @Operation(summary = "导出商品数据")
    @PreAuthorize("hasAnyAuthority('test:shop:testGoods:export')")
    @GetMapping("export")
    public void exportFile(TestGoodsDTO testGoodsDTO, Page <TestGoodsDTO> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testGoodsDTO, TestGoodsDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据
            
        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "a.id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List<TestGoodsDTO> result = testGoodsService.findPage ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( testGoodsService, testGoodsWrapper ).exportExcel ( result,  options.getSheetName ( ), TestGoodsDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入商品数据
     *
     * @return
     */
    @ApiLog("导入商品数据板")
	@Operation(summary = "导入商品数据")
    @PreAuthorize("hasAnyAuthority('test:shop:testGoods:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( testGoodsService, testGoodsWrapper ).importExcel ( file, TestGoodsDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入商品数据模板
     *
     * @param response
     * @return
     */
    @ApiLog("下载导入商品数据模板")
	@Operation(summary = "下载导入商品数据模板")
    @PreAuthorize ("hasAnyAuthority('test:shop:testGoods:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "商品数据导入模板.xlsx";
        List<TestGoodsDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( testGoodsService, testGoodsWrapper ).exportExcel ( list,  "商品数据", TestGoodsDTO.class, fileName, null, response );
    }


}
