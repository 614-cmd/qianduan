/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.treetable.controller;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.google.common.collect.Lists;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jeeplus.test.treetable.service.dto.TestCarKindDTO;
import com.jeeplus.test.treetable.service.mapstruct.TestCarKindWrapper;
import com.jeeplus.test.treetable.service.TestCarKindService;

/**
 * 车系Controller
 * @author 刘高峰
 * @version 2024-09-01
 */
@Tag(name = "车系")		
@RestController
@RequestMapping(value = "/test/treetable/testCarKind")
public class TestCarKindController {

	@Autowired
	private TestCarKindService testCarKindService;

	@Autowired
	private TestCarKindWrapper testCarKindWrapper;

	/**
	 * 根据Id获取车系数据
	 */
	@ApiLog("根据Id获取车系数据")
	@Operation(summary = "根据Id获取车系数据")
	@GetMapping("queryById")
	public ResponseEntity<TestCarKindDTO> queryById(String id) {
		return ResponseEntity.ok ( testCarKindWrapper.toDTO ( testCarKindService.getById ( id ) ) );
	}

	/**
	 * 保存车系
	 */
	@ApiLog("保存车系")
	@Operation(summary = "保存车系")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody TestCarKindDTO testCarKindDTO) {
		//新增或编辑表单保存
		testCarKindService.saveOrUpdate (testCarKindWrapper.toEntity (testCarKindDTO));
        return ResponseEntity.ok ( "保存车辆成功" );
	}

	/**
	 * 删除车系
	 */
	@ApiLog("删除车系")
	@Operation(summary = "删除车系")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testCarKindService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除车辆成功" );
	}
	
    /**
     * 移动车系节点
     * @param testCarKindDTO
     * @return
     */
    @ApiLog("移动车系节点")
	@Operation(summary = "移动车系节点") 
    @PostMapping("drag")
    public ResponseEntity drag(@Valid @RequestBody DragNode <TestCarKindDTO> testCarKindDTO) {
        testCarKindService.sortList ( testCarKindWrapper.toEntity (testCarKindDTO) );
        return ResponseEntity.ok ("移动节点成功!");
    }


	/**
     * 获取JSON树形数据。
     * @param extId 排除的ID
     * @return
	*/
	@ApiLog("查询车系树表数据")
	@Operation(summary = "查询车系树表数据")
	@GetMapping("treeData")
	public ResponseEntity <List <TestCarKindDTO> > treeData(String extId) {
		List <TestCarKindDTO> rootTree = testCarKindService.treeData ( extId ).stream ().map ( testCarKindWrapper::toDTO ).collect( Collectors.toList());
		return ResponseEntity.ok ( rootTree );
	}

}
