/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.datav.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.core.excel.EasyExcelUtils;
import com.jeeplus.core.excel.ExcelOptions;
import com.jeeplus.core.excel.annotation.ExportMode;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.datav.domain.DataComponent;
import com.jeeplus.datav.service.dto.DataComponentDTO;
import com.jeeplus.datav.service.mapstruct.DataComponentWrapper;
import com.jeeplus.datav.service.DataComponentService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 组件Controller
 * @author 刘高峰
 * @version 2023-02-12
 */

@Tag(name = "大屏组件")
@RestController
@RequestMapping(value = "/datav/dataComponent")
public class DataComponentController {

	@Autowired
	private DataComponentService dataComponentService;

	@Autowired
	private DataComponentWrapper dataComponentWrapper;

	/**
	 * 组件列表数据
	 */
	@ApiLog("查询组件列表数据")
	@PreAuthorize("hasAuthority('datav:dataComponent:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<DataComponent>> list(DataComponentDTO dataComponentDTO, Page<DataComponent> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (dataComponentDTO, DataComponentDTO.class);
		IPage<DataComponent> result = dataComponentService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取组件数据
	 */
	@ApiLog("根据Id获取组件数据")
	@PreAuthorize("hasAnyAuthority('datav:dataComponent:view','datav:dataComponent:add','datav:dataComponent:edit')")
	@GetMapping("queryById")
	public ResponseEntity<DataComponentDTO> queryById(String id) {
		return ResponseEntity.ok ( dataComponentWrapper.toDTO ( dataComponentService.getById ( id ) ) );
	}

	/**
	 * 保存组件
	 */
	@ApiLog("保存组件")
	@PreAuthorize("hasAnyAuthority('datav:dataComponent:add','datav:dataComponent:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody DataComponentDTO dataComponentDTO) {
		//新增或编辑表单保存
		dataComponentService.saveOrUpdate (dataComponentWrapper.toEntity (dataComponentDTO));
        return ResponseEntity.ok ( "保存组件成功" );
	}


	/**
	 * 删除组件
	 */
	@ApiLog("删除组件")
	@PreAuthorize("hasAuthority('datav:dataComponent:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String[] idArray = ids.split(",");
        dataComponentService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除组件成功" );
	}

	/**
     * 导出组件数据
     *
     * @param dataComponentDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出组件数据")
    @PreAuthorize("hasAnyAuthority('datav:dataComponent:export')")
    @GetMapping("export")
    public void exportFile(DataComponentDTO dataComponentDTO, Page <DataComponent> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (dataComponentDTO, DataComponentDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据

        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List < DataComponent> result = dataComponentService.page ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( dataComponentService, dataComponentWrapper ).exportExcel ( result,  options.getSheetName ( ), DataComponentDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入组件数据
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('datav:dataComponent:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( dataComponentService, dataComponentWrapper ).importExcel ( file, DataComponentDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入组件数据模板
     *
     * @param response
     * @return
     */
    @PreAuthorize ("hasAnyAuthority('datav:dataComponent:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "组件数据导入模板.xlsx";
        List<DataComponentDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( dataComponentService, dataComponentWrapper ).exportExcel ( list,  "组件数据", DataComponentDTO.class, fileName, null, response );
    }

}
