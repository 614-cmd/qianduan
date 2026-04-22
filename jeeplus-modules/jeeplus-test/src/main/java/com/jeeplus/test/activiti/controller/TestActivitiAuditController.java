/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.activiti.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.aop.logging.annotation.ApiLog;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.activiti.service.dto.TestActivitiAuditDTO;
import com.jeeplus.test.activiti.service.TestActivitiAuditService;

/**
 * 加薪申请Controller
 * @author 刘高峰
 * @version 2024-09-01
 */
@Tag(name = "加薪申请")
@RestController
@RequestMapping(value = "/test/activiti/testActivitiAudit")
public class TestActivitiAuditController {

	@Autowired
	private TestActivitiAuditService testActivitiAuditService;

	/**
	 * 加薪申请列表数据
	 */
	@ApiLog("查询加薪申请列表数据")
	@Operation(summary = "查询加薪申请列表数据")
	@GetMapping("list")
	public ResponseEntity<IPage<TestActivitiAuditDTO>> list(TestActivitiAuditDTO testActivitiAuditDTO, Page<TestActivitiAuditDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (testActivitiAuditDTO, TestActivitiAuditDTO.class);
		IPage<TestActivitiAuditDTO> result = testActivitiAuditService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取加薪申请数据
	 */
	@ApiLog("根据Id获取加薪申请数据")
	@Operation(summary = "根据Id获取加薪申请数据")
	@GetMapping("queryById")
	public ResponseEntity<TestActivitiAuditDTO> queryById(String id) {
		return ResponseEntity.ok ( testActivitiAuditService.findById ( id ) );
	}

	/**
	 * 保存加薪申请
	 */
	@ApiLog("保存加薪申请")
	@Operation(summary = "保存加薪申请")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestActivitiAuditDTO testActivitiAuditDTO) {
		//新增或编辑表单保存
		testActivitiAuditService.saveOrUpdate (testActivitiAuditDTO);
		return ResponseUtil.newInstance ( ).add("businessTable", "test_activiti_audit").add("businessId", testActivitiAuditDTO.getId()).ok ("保存加薪申请成功");
	}


	/**
	 * 删除加薪申请
	 */
	@ApiLog("删除加薪申请")
	@Operation(summary = "删除加薪申请")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			testActivitiAuditService.removeById ( id );
		}
		return ResponseEntity.ok( "删除加薪申请成功" );
	}

}
