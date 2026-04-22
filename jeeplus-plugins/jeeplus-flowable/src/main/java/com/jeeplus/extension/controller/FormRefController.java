/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import com.jeeplus.extension.domain.FormRef;
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
import com.jeeplus.extension.service.dto.FormDataDTO;
import com.jeeplus.extension.service.mapstruct.FormRefWrapper;
import com.jeeplus.extension.service.FormRefService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 工作流数据Controller
 * @author 刘高峰
 * @version 2024-06-25
 */

@Tag(name = "工作流数据")
@RestController
@RequestMapping(value = "/extension/formData")
public class FormRefController {

	@Autowired
	private FormRefService formRefService;

	@Autowired
	private FormRefWrapper formRefWrapper;

	/**
	 * 工作流数据列表数据
	 */
	@ApiLog("查询工作流数据列表数据")
	@Operation(summary = "查询工作流数据列表数据")
	@PreAuthorize("hasAuthority('extension:formData:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<FormDataDTO>> list(FormDataDTO formDataDTO, Page<FormRef> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (formDataDTO, FormDataDTO.class);
		IPage<FormDataDTO> result = formRefWrapper.toDTO ( formRefService.page (page, queryWrapper) );
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取工作流数据数据
	 */
	@ApiLog("根据Id获取工作流数据数据")
	@Operation(summary = "根据Id获取工作流数据数据")
	@PreAuthorize("hasAnyAuthority('extension:formData:view','extension:formData:add','extension:formData:edit')")
	@GetMapping("queryById")
	public ResponseEntity<FormDataDTO> queryById(String id) {
		return ResponseEntity.ok ( formRefWrapper.toDTO ( formRefService.getById ( id ) ) );
	}

	/**
	 * 保存工作流数据
	 */
	@ApiLog("保存工作流数据")
	@Operation(summary = "保存工作流数据")
	@PreAuthorize("hasAnyAuthority('extension:formData:add','extension:formData:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody FormDataDTO formDataDTO) {
		//新增或编辑表单保存
		formRefService.saveOrUpdate ( formRefWrapper.toEntity (formDataDTO));
        return ResponseEntity.ok ( "保存工作流数据成功" );
	}


	/**
	 * 删除工作流数据
	 */
	@ApiLog("删除工作流数据")
	@Operation(summary = "删除工作流数据")
	@PreAuthorize("hasAuthority('extension:formData:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        formRefService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除工作流数据成功" );
	}

    /**
     * 导出工作流数据数据
     *
     * @param formDataDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出工作流数据数据")
	@Operation(summary = "导出工作流数据数据")
    @PreAuthorize("hasAnyAuthority('extension:formData:export')")
    @GetMapping("export")
    public void exportFile(FormDataDTO formDataDTO, Page <FormRef> page, ExcelOptions options, HttpServletResponse response) throws Exception {
        String fileName = options.getFilename ( );
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (formDataDTO, FormDataDTO.class);
        if ( ExportMode.current.equals ( options.getMode ( ) ) ) { // 导出当前页数据

        } else if ( ExportMode.selected.equals ( options.getMode ( ) ) ) { // 导出选中数据
            queryWrapper.in ( "id", options.getSelectIds () );
        } else { // 导出全部数据
            page.setSize ( -1 );
            page.setCurrent ( 0 );
        }
        List <FormRef> result = formRefService.page ( page, queryWrapper ).getRecords ( );
        EasyExcelUtils.newInstance ( formRefService, formRefWrapper ).exportExcel ( result,  options.getSheetName ( ), FormDataDTO.class, fileName,options.getExportFields (), response );
    }

    /**
     * 导入工作流数据数据
     *
     * @return
     */
	@ApiLog("导入工作流数据数据")
	@Operation(summary = "导入工作流数据数据")
    @PreAuthorize("hasAnyAuthority('extension:formData:import')")
    @PostMapping("import")
    public ResponseEntity importFile(MultipartFile file) throws IOException {
        String result = EasyExcelUtils.newInstance ( formRefService, formRefWrapper ).importExcel ( file, FormDataDTO.class );
        return ResponseEntity.ok ( result );
    }

    /**
     * 下载导入工作流数据数据模板
     *
     * @param response
     * @return
     */
	@ApiLog("下载导入工作流数据数据模板")
	@Operation(summary = "下载导入工作流数据数据模板")
    @PreAuthorize ("hasAnyAuthority('extension:formData:import')")
    @GetMapping("import/template")
    public void importFileTemplate(HttpServletResponse response) throws IOException {
        String fileName = "工作流数据数据导入模板.xlsx";
        List<FormDataDTO> list = Lists.newArrayList();
        EasyExcelUtils.newInstance ( formRefService, formRefWrapper ).exportExcel ( list,  "工作流数据数据", FormDataDTO.class, fileName, null, response );
    }


}
