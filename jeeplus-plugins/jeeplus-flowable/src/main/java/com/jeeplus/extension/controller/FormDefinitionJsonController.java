/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.extension.domain.FormDefinitionJson;
import com.jeeplus.extension.service.FormDefinitionJsonService;
import com.jeeplus.extension.service.FormDefinitionService;
import com.jeeplus.extension.service.dto.FormDefinitionDTO;
import com.jeeplus.form.service.dto.ColumnDTO;
import com.jeeplus.form.utils.DDLBuilder;
import com.jeeplus.form.utils.FormJsonUtils;
import com.jeeplus.sys.constant.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.apache.ddlutils.platform.oracle.Oracle8Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

/**
 * 流程表单Controller
 *
 * @author 刘高峰
 * @version 2021-02-02
 */
@Tag(name = "流程表单定义json")
@RestController
@RequestMapping(value = "/extension/formDefinitionJson")
public class FormDefinitionJsonController {

    @Autowired
    private FormDefinitionJsonService formDefinitionJsonService;
    @Autowired
    private FormDefinitionService formDefinitionService;

    /**
     * 流程表单列表数据
     */
    @ApiLog("流程表单列表数据")
    @Operation(summary = "流程表单列表数据")
    @GetMapping("list")
    public ResponseEntity list(FormDefinitionJson formDefinitionJson, Page <FormDefinitionJson> page) throws Exception {
        QueryWrapper <FormDefinitionJson> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( formDefinitionJson, FormDefinitionJson.class );
        IPage <FormDefinitionJson> result = formDefinitionJsonService.page ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }


    /**
     * 根据Id获取流程表单数据
     */
    @ApiLog("根据Id获取流程表单数据")
    @Operation(summary = "根据Id获取流程表单数据")
    @GetMapping("queryById")
    public ResponseEntity queryById(FormDefinitionJson formDefinitionJson) {
        formDefinitionJson = formDefinitionJsonService.getById ( formDefinitionJson.getId ( ) );

        return ResponseEntity.ok ( formDefinitionJson );
    }

    /**
     * 更新主版本
     */
    @ApiLog("更新主版本")
    @Operation(summary = "更新主版本")
    @PutMapping("updatePrimary")
    public ResponseEntity updatePrimary(String id) {
        //设置主版本
        formDefinitionJsonService.updatePrimary ( id );
        return ResponseEntity.ok ( "设置主表单成功!" );
    }

    /**
     * 保存流程表单
     */
    @ApiLog("保存流程表单")
    @Operation(summary = "保存流程表单")
    @PostMapping("save")
    public ResponseEntity save(@RequestBody FormDefinitionJson formDefinitionJson) {

        FormDefinitionDTO formDefinitionDTO = formDefinitionService.getById ( formDefinitionJson.getFormDefinitionId () );

        /**
         * 更新版本信息
         */
        if ( StrUtil.isBlank ( formDefinitionJson.getId ( ) ) ) {//新增
            formDefinitionJson.setVersion ( 1 );
        } else {//更新
            FormDefinitionJson old = formDefinitionJsonService.getById ( formDefinitionJson.getId ( ) );
            if ( CommonConstants.YES.equals ( old.getStatus ( ) ) ) {//已发布, 如果表单已经发布，不可修改，只能发布为新版本
                formDefinitionJson.setId ( "" );//发布新版本
                formDefinitionJson.setVersion ( formDefinitionJsonService.getMaxVersion ( formDefinitionJson ) + 1 );
            }

        }

        /**
         * 如果是自动建表，同步表结构， 表结构就一个版本跟着表单主版本走，不再提供多个表关联不同版本的表单版本
         */
        if (formDefinitionDTO.getIsRelatedTable ().equals ( CommonConstants.YES )
                && formDefinitionDTO.getAutoCreate ( ).equals ( CommonConstants.YES ) ) {
            String dataSourceId = formDefinitionDTO.getDataSource ( ).getId ( );
            DataSource dataSource = DBPool.getInstance ( ).getDsById ( dataSourceId );
            DDLBuilder ddlBuilder = new DDLBuilder ( dataSource, formDefinitionDTO.getTableName ( ) );
            List <ColumnDTO> fieldArra = FormJsonUtils.newInstance ( ).getFields ( formDefinitionJson.getJson () );
            ddlBuilder.syncTable ( fieldArra );
        }
        formDefinitionJsonService.saveOrUpdate ( formDefinitionJson );//保存
        formDefinitionJsonService.updatePrimary ( formDefinitionJson.getId ( ) );// 设置为新版本
        return ResponseEntity.ok ( "保存流程表单成功" );
    }


    /**
     * 批量删除流程表单
     */
    @ApiLog("批量删除流程表单")
    @Operation(summary = "批量删除流程表单")
    @DeleteMapping("delete")
    public ResponseEntity delete(String ids) {
        String[] idArray = ids.split ( "," );
        formDefinitionJsonService.removeByIds ( Lists.newArrayList ( idArray ) );
        return ResponseEntity.ok ( "删除流程表单成功" );
    }

}
