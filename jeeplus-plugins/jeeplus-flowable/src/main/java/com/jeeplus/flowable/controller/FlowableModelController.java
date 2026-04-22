package com.jeeplus.flowable.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.flowable.model.FlowModel;
import com.jeeplus.flowable.model.ModelRepresentation;
import com.jeeplus.flowable.service.FlowableModelService;
import com.jeeplus.flowable.utils.ModelUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

/**
 * 流程模型相关Controller
 *
 * @author liugaofeng
 * @version 2021-07-29
 */
@Tag(name = "流程模型")
@RestController
@RequestMapping("/flowable/model")
public class FlowableModelController {

    @Autowired
    private FlowableModelService flowableModelService;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private RepositoryService repositoryService;


    /**
     * 流程模型列表
     */
    @ApiLog("流程模型列表")
    @Operation(summary = "流程模型列表")
    @GetMapping("list")
    public ResponseEntity data(Page <FlowModel> page, String name, String category, HttpServletRequest request) {
        page = flowableModelService.getModels ( page, name, category );
        return ResponseEntity.ok ( page );
    }


    /**
     * 获取model的xml文件
     */
    @ApiLog("获取model的xml文件")
    @Operation(summary = "获取model的xml文件")
    @GetMapping("getBpmnXml")
    public String getBpmnXml(String id, HttpServletResponse response) {
        return flowableModelService.getBpmnXml ( id, response );
    }

    /**
     * 导出model的xml文件
     */
    @ApiLog("导出model的xml文件")
    @Operation(summary = "导出model的xml文件")
    @GetMapping("exportBpmnXml")
    public void exportBpmnXml(String id, HttpServletResponse response) throws Exception {
        flowableModelService.generateBpmn20Xml ( response, id );

    }

    /**
     * 更新Model分类
     */
    @ApiLog("更新Model分类")
    @Operation(summary = "更新Model分类")
    @PutMapping(value = "updateCategory")
    public ResponseEntity updateCategory(String key, String category) {
        Model model = repositoryService.createModelQuery ( ).modelKey ( key ).singleResult ( );

        if (model != null) {
            model.setCategory ( category );
            repositoryService.saveModel( model );
            repositoryService.saveModel ( model );
        }
        List <ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery ( ).processDefinitionKey ( key ).list ( );
        processDefinitionList.forEach ( processDefinition -> {
            repositoryService.setProcessDefinitionCategory ( processDefinition.getId (), category );
        } );
        return ResponseEntity.ok ( "设置成功，流程KEY=" + key );
    }

    /**
     * 删除Model
     *
     * @param ids
     * @return
     */
    @DemoMode
    @ApiLog("删除Model")
    @Operation(summary = "删除Model")
    @DeleteMapping("delete")
    public ResponseEntity deleteAll(String ids) {
        String[] idArray = ids.split ( "," );
        for (String id : idArray) {
            flowableModelService.delete ( id );
        }
        return ResponseEntity.ok ( "删除成功!" );
    }

    /**
     * 根据Model复制流程
     */
    @ApiLog("根据Model复制流程")
    @Operation(summary = "根据Model复制流程")
    @GetMapping("copy")
    public ResponseEntity copy(String id) throws Exception {

        Model sourceModel = repositoryService.getModel ( id );
        ModelRepresentation modelRepresentation = new ModelRepresentation ( );
        modelRepresentation.setKey ( "Process_" + new Date ().getTime () );
        modelRepresentation.setName ( sourceModel.getName ( ) + "_copy" );
        modelRepresentation.setModelType ( 0 );
        modelRepresentation.setDescription ( sourceModel.getMetaInfo () );
        flowableModelService.checkForDuplicateKey ( modelRepresentation );

        Model newModel = repositoryService.newModel ( );
        newModel.setMetaInfo ( sourceModel.getMetaInfo () );
        newModel.setKey (modelRepresentation.getKey());
        newModel.setName(modelRepresentation.getName());
        newModel.setCategory ( sourceModel.getCategory () );
        newModel.setTenantId ( sourceModel.getTenantId () );
        String xml = StrUtil.utf8Str(repositoryService.getModelEditorSource ( id ));
        BpmnModel bpmnModel = ModelUtils.xmlToBpmnModel(xml);
        bpmnModel.getMainProcess ().setId ( modelRepresentation.getKey () );
        bpmnModel.getMainProcess ().setName ( modelRepresentation.getName() );
        byte[] xmlBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        repositoryService.saveModel(newModel);
        repositoryService.addModelEditorSource(newModel.getId(), xmlBytes);
        return ResponseEntity.ok ( "拷贝成功!" );
    }

    /**
     * 根据Model部署流程
     */
    @ApiLog("根据Model部署流程")
    @Operation(summary = "根据Model部署流程")
    @PutMapping("deploy")
    public ResponseEntity deploy(String id, String category) {
        String result = flowableModelService.deploy ( id, category );
        return ResponseEntity.ok ( result );
    }


    @ApiLog("创建模型")
    @Operation(summary = "创建模型")
    @PostMapping(
            value = {"/rest/models"},
            produces = {"application/json"}
    )
    public ModelRepresentation createModel(@RequestBody ModelRepresentation modelRepresentation) {
        modelRepresentation.setKey ( modelRepresentation.getKey ( ).replaceAll ( " ", "" ) );
        flowableModelService.checkForDuplicateKey ( modelRepresentation );

        //初始空的模型
        Model model = repositoryService.newModel ( );
        model.setKey ( modelRepresentation.getKey ( ) );
        model.setName ( modelRepresentation.getName ( ) );
        model.setVersion ( 1 );
        model.setMetaInfo ( modelRepresentation.getDescription ( ) );
        //保存初始化的模型基本信息数据
        repositoryService.saveModel ( model );
        String json = flowableModelService.createModelJson (modelRepresentation  );
        repositoryService.addModelEditorSource(model.getId(), StrUtil.utf8Bytes(json));


        return new ModelRepresentation ( model );
    }

    @ApiLog("保存模型")
    @Operation(summary = "保存模型")
    @PostMapping("saveModel/{modelId}")
    public ModelRepresentation saveModel(@PathVariable String modelId, @RequestBody MultiValueMap <String, String> values) {
        long lastUpdated = -1L;
        String lastUpdatedString = values.getFirst ( "lastUpdated" );
        if ( lastUpdatedString == null ) {
            throw new RuntimeException ( "Missing lastUpdated date" );
        } else {
            lastUpdated = DateUtil.parse ( lastUpdatedString ).getTime ( );
            Model model = repositoryService.getModel ( modelId );
            if ( model.getLastUpdateTime ( ).getTime ( ) / 1000 != lastUpdated / 1000 ) {
                    return flowableModelService.updateModel ( model, values, true );
            } else {
                return flowableModelService.updateModel ( model, values, false );
            }
        }
    }


}

