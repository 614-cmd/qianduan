/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.database.datalink.jdbc.DBPool;
import com.jeeplus.extension.domain.FormDefinition;
import com.jeeplus.extension.domain.FormDefinitionJson;
import com.jeeplus.extension.service.FormDefinitionJsonService;
import com.jeeplus.extension.service.FormDefinitionService;
import com.jeeplus.extension.service.dto.FormDefinitionDTO;
import com.jeeplus.extension.service.mapstruct.FormDefinitionJsonWrapper;
import com.jeeplus.extension.service.mapstruct.FormDefinitionWrapper;
import com.jeeplus.form.utils.DDLBuilder;
import com.jeeplus.sys.constant.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.apache.ddlutils.platform.oracle.Oracle8Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

/**
 * 流程表单Controller
 *
 * @author 刘高峰
 * @version 2021-02-02
 */
@Tag(name = "流程表单")
@RestController
@RequestMapping(value = "/extension/formDefinition")
public class FormDefinitionController {

    @Autowired
    private FormDefinitionService formDefinitionService;
    @Autowired
    private FormDefinitionJsonService formDefinitionJsonService;
    @Autowired
    private FormDefinitionJsonWrapper formDefinitionJsonWrapper;
    @Autowired
    private FormDefinitionWrapper formDefinitionWrapper;

    /**
     * 流程表单列表数据
     */
    @ApiLog("流程表单列表数据")
    @Operation(summary = "流程表单列表数据")
    @GetMapping("list")
    public ResponseEntity list(FormDefinitionDTO formDefinitionDTO, Page <FormDefinitionDTO> page) throws Exception {
        QueryWrapper <FormDefinitionDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition ( formDefinitionDTO, FormDefinitionDTO.class );
        IPage <FormDefinitionDTO> result = formDefinitionService.findPage ( page, queryWrapper );
        return ResponseEntity.ok ( result );
    }


    /**
     * 根据Id获取流程表单数据
     */
    @ApiLog("根据Id获取流程表单数据")
    @Operation(summary = "根据Id获取流程表单数据")
    @GetMapping("queryById")
    public ResponseEntity queryById(String id) {
        return ResponseEntity.ok ( formDefinitionService.getById ( id ) );
    }

    /**
     * 根据Id获取流程表单数据
     */
    @ApiLog("根据Id获取流程表单数据")
    @Operation(summary = "根据Id获取流程表单数据")
    @GetMapping("queryByJsonId")
    public ResponseEntity queryByJsonId(String jsonId) {
        FormDefinitionJson formDefinitionJson = formDefinitionJsonService.getById ( jsonId );
        if ( formDefinitionJson != null ) {
            FormDefinitionDTO formDefinitionDTO = formDefinitionService.getById ( formDefinitionJson.getFormDefinitionId ( ) );
            formDefinitionDTO.setFormDefinitionJson ( formDefinitionJsonWrapper.toDTO ( formDefinitionJson ) );
            return ResponseEntity.ok ( formDefinitionDTO );
        } else {
            return ResponseEntity.badRequest ( ).body ( "流程关联的动态表单版本已经被删除!" );
        }

    }

    /**
     * 保存流程表单
     */
    @ApiLog("保存流程表单")
    @Operation(summary = "保存流程表单")
    @PostMapping("save")
    public ResponseEntity save(@RequestBody FormDefinitionDTO formDefinitionDTO) {
        /**
         * 如果是关联了物理表，
         *  新建 自动建表
         *  修改表名 自动建表
         */
        if (formDefinitionDTO.getIsRelatedTable ().equals ( CommonConstants.YES )
                && formDefinitionDTO.getAutoCreate ( ).equals ( CommonConstants.YES )
                && !formDefinitionDTO.getTableName ().equals ( formDefinitionDTO.getOldTableName () )) {
            String dataSourceId = formDefinitionDTO.getDataSource ( ).getId ( );
            DataSource dataSource = DBPool.getInstance ( ).getDsById ( dataSourceId );
            DDLBuilder ddlBuilder = new DDLBuilder ( dataSource );
            if ( ddlBuilder.getDataBaseType ( ).equals ( Oracle8Platform.DATABASENAME ) ) {
                formDefinitionDTO.setTableName ( StringUtils.upperCase ( formDefinitionDTO.getTableName ( ) ) );
            }
            ddlBuilder.setTableName ( formDefinitionDTO.getTableName ( ) );
            ddlBuilder.createTable ( );
        }

        /**
         * 如果修改表名，将表单设置为草稿
         */
        if(!formDefinitionDTO.getTableName ().equals ( formDefinitionDTO.getOldTableName () )){
            formDefinitionJsonService.lambdaUpdate ( )
                    .eq( FormDefinitionJson::getFormDefinitionId, formDefinitionDTO.getId () )
                    .eq ( FormDefinitionJson::getIsPrimary, CommonConstants.YES )
                    .set ( FormDefinitionJson::getStatus, CommonConstants.NO )
                    .update ( );
        }

        FormDefinition formDefinition = formDefinitionWrapper.toEntity ( formDefinitionDTO );
        formDefinitionService.saveOrUpdate ( formDefinition );//保存
        return ResponseUtil.newInstance ( ).add ( "id", formDefinition.getId ( ) ).ok ( "保存成功" );
    }


    /**
     * 批量删除流程表单
     */
    @ApiLog("批量删除流程表单")
    @Operation(summary = "批量删除流程表单")
    @DeleteMapping("delete")
    public ResponseEntity delete(String ids) {
        String[] idArray = ids.split ( "," );
        for (String id : idArray) {
            FormDefinitionDTO form = formDefinitionService.getById ( id );
            //需要删除表
            if ( StrUtil.isNotBlank ( form.getTableName ( ) ) && form.getAutoCreate ( ).equals ( "1" ) ) {
                String dataSourceId = form.getDataSource ( ).getId ( );
                DataSource dataSource = DBPool.getInstance ( ).getDsById ( dataSourceId );
                DDLBuilder formTableBuilder = new DDLBuilder ( dataSource, form.getTableName ( ) );
                formTableBuilder.dropTable ( );
            }
            formDefinitionService.removeById ( form.getId ( ) );

        }
        return ResponseEntity.ok ( "删除流程表单成功" );
    }


}
