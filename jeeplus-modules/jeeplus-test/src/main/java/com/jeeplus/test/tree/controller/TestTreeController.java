/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.tree.controller;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jeeplus.test.tree.domain.TestTree;
import com.jeeplus.test.tree.service.dto.TestTreeDTO;
import com.jeeplus.test.tree.service.mapstruct.TestTreeWrapper;
import com.jeeplus.test.tree.service.TestTreeService;

/**
 * 组织机构Controller
 * @author 刘高峰
 * @version 2024-09-01
 */
@Tag(name = "组织机构") 
@RestController
@RequestMapping(value = "/test/tree/testTree")
public class TestTreeController {

	@Autowired
	private TestTreeService testTreeService;

	@Autowired
	private TestTreeWrapper testTreeWrapper;

	/**
	 * 根据Id获取组织机构数据
	 */
	@ApiLog("根据Id获取组织机构数据")
	@Operation(summary = "根据Id获取组织机构数据")
	@PreAuthorize("hasAnyAuthority('test:tree:testTree:view','test:tree:testTree:add','test:tree:testTree:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestTreeDTO> queryById(String id) {
		return ResponseEntity.ok ( testTreeWrapper.toDTO ( testTreeService.getById ( id ) ) );
	}

	/**
	 * 保存组织机构
	 */
	@ApiLog("保存组织机构")
	@Operation(summary = "保存组织机构")
	@PreAuthorize("hasAnyAuthority('test:tree:testTree:add','test:tree:testTree:edit')")
	@PostMapping("save")
	public  ResponseEntity <TestTree> save(@Valid @RequestBody TestTreeDTO testTreeDTO) {
		//新增或编辑表单保存
		TestTree testTree = testTreeWrapper.toEntity (testTreeDTO);
		testTreeService.saveOrUpdate (testTree);
        return ResponseEntity.ok ( testTree );
	}
	
    /**
     * 移动组织机构节点
     * @param testTreeDTO
     * @return
     */
    @PostMapping("drag")
    public ResponseEntity drag(@Valid @RequestBody DragNode <TestTreeDTO> testTreeDTO) {
        testTreeService.sortList ( testTreeWrapper.toEntity (testTreeDTO) );
        return ResponseEntity.ok ("移动节点成功!");
    }


	/**
	 * 删除组织机构
	 */
	@ApiLog("删除组织机构")
	@Operation(summary = "删除组织机构")
	@PreAuthorize("hasAuthority('test:tree:testTree:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testTreeService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除组织机构成功" );
	}

	/**
     * 获取JSON树形数据。
     * @param extId 排除的ID
     * @return
	*/
	@ApiLog("查询组织机构树表数据")
	@Operation(summary = "查询组织机构树表数据")
	@GetMapping("treeData")
	public ResponseEntity <List <TestTreeDTO> > treeData(String extId) {
		List <TestTreeDTO> rootTree = testTreeService.treeData ( extId ).stream ().map ( testTreeWrapper::toDTO ).collect( Collectors.toList());
		return ResponseEntity.ok ( rootTree );
	}

}
