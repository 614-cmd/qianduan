/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.shop.controller;

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
import com.jeeplus.test.shop.domain.TestCategory;
import com.jeeplus.test.shop.service.dto.TestCategoryDTO;
import com.jeeplus.test.shop.service.mapstruct.TestCategoryWrapper;
import com.jeeplus.test.shop.service.TestCategoryService;

/**
 * 商品类型Controller
 * @author 刘高峰
 * @version 2024-09-01
 */
@Tag(name = "商品类型") 
@RestController
@RequestMapping(value = "/test/shop/testCategory")
public class TestCategoryController {

	@Autowired
	private TestCategoryService testCategoryService;

	@Autowired
	private TestCategoryWrapper testCategoryWrapper;

	/**
	 * 根据Id获取商品类型数据
	 */
	@ApiLog("根据Id获取商品类型数据")
	@Operation(summary = "根据Id获取商品类型数据")
	@PreAuthorize("hasAnyAuthority('test:shop:testCategory:view','test:shop:testCategory:add','test:shop:testCategory:edit')")
	@GetMapping("queryById")
	public ResponseEntity<TestCategoryDTO> queryById(String id) {
		return ResponseEntity.ok ( testCategoryWrapper.toDTO ( testCategoryService.getById ( id ) ) );
	}

	/**
	 * 保存商品类型
	 */
	@ApiLog("保存商品类型")
	@Operation(summary = "保存商品类型")
	@PreAuthorize("hasAnyAuthority('test:shop:testCategory:add','test:shop:testCategory:edit')")
	@PostMapping("save")
	public  ResponseEntity <TestCategory> save(@Valid @RequestBody TestCategoryDTO testCategoryDTO) {
		//新增或编辑表单保存
		TestCategory testCategory = testCategoryWrapper.toEntity (testCategoryDTO);
		testCategoryService.saveOrUpdate (testCategory);
        return ResponseEntity.ok ( testCategory );
	}
	
    /**
     * 移动商品类型节点
     * @param testCategoryDTO
     * @return
     */
    @PostMapping("drag")
    public ResponseEntity drag(@Valid @RequestBody DragNode <TestCategoryDTO> testCategoryDTO) {
        testCategoryService.sortList ( testCategoryWrapper.toEntity (testCategoryDTO) );
        return ResponseEntity.ok ("移动节点成功!");
    }


	/**
	 * 删除商品类型
	 */
	@ApiLog("删除商品类型")
	@Operation(summary = "删除商品类型")
	@PreAuthorize("hasAuthority('test:shop:testCategory:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        testCategoryService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除商品类型成功" );
	}

	/**
     * 获取JSON树形数据。
     * @param extId 排除的ID
     * @return
	*/
	@ApiLog("查询商品类型树表数据")
	@Operation(summary = "查询商品类型树表数据")
	@GetMapping("treeData")
	public ResponseEntity <List <TestCategoryDTO> > treeData(String extId) {
		List <TestCategoryDTO> rootTree = testCategoryService.treeData ( extId ).stream ().map ( testCategoryWrapper::toDTO ).collect( Collectors.toList());
		return ResponseEntity.ok ( rootTree );
	}

}
