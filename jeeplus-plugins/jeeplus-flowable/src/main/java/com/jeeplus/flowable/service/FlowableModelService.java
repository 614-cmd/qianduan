package com.jeeplus.flowable.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.jeeplus.extension.service.NodeSettingService;
import com.jeeplus.extension.service.TaskDefExtensionService;
import com.jeeplus.flowable.mapper.FlowModelMapper;
import com.jeeplus.flowable.model.FlowModel;
import com.jeeplus.flowable.model.ModelKeyRepresentation;
import com.jeeplus.flowable.model.ModelRepresentation;
import com.jeeplus.flowable.utils.ModelUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.form.model.SimpleFormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * 流出模型Service
 *
 * @author liuruijun
 * @version 2021-08-02
 */
@Slf4j
@Service
@Transactional
public class FlowableModelService extends ServiceImpl <FlowModelMapper, FlowModel> {

    @Autowired
    FlowProcessService flowProcessService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private NodeSettingService nodeSettingService;
    @Autowired
    private TaskDefExtensionService taskDefExtensionService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 流程模型列表
     */
    public Page <FlowModel> getModels(Page <FlowModel> page, String name, String category) {

        ModelQuery query = repositoryService.createModelQuery ( );
        if ( StrUtil.isNotBlank ( name ) ) {
            query.modelNameLike ( "%" + name + "%" );
        }
        if ( StrUtil.isNotBlank ( category ) ) {
            query.modelCategory ( category );
        }
        query.orderByLastUpdateTime ( ).desc ( );
        // 创建时间降序排列
        query.orderByCreateTime ( ).desc ( );
        page.setTotal ( query.count ( ) );
        // 分页查询
        List <Model> modelList = query.listPage ( (int) ((page.getCurrent ( ) - 1) * page.getSize ( )), (int) page.getSize ( ) );

        List <FlowModel> flowModelList = Lists.newArrayList ( );
        for (Model m : modelList) {
            FlowModel flowModel = new FlowModel ( );
            flowModel.setName ( m.getName ( ) );
            flowModel.setId ( m.getId ( ) );
            flowModel.setComment ( m.getMetaInfo ( ) );
            flowModel.setKey ( m.getKey ( ) );
            flowModel.setUpdateDate ( m.getLastUpdateTime ( ) );
            flowModelList.add ( flowModel );
            ProcessDefinition processDefinition = flowProcessService.getProcessDefinitionByKey ( m.getKey ( ) );
            Map pMap = new HashMap <> ( );
            if ( processDefinition != null ) {
                String deploymentId = processDefinition.getDeploymentId ( );
                Deployment deployment = repositoryService.createDeploymentQuery ( ).deploymentId ( deploymentId ).singleResult ( );
                pMap.put ( "id", processDefinition.getId ( ) );
                pMap.put ( "category", processDefinition.getCategory ( ) );
                pMap.put ( "key", processDefinition.getKey ( ) );
                pMap.put ( "name", processDefinition.getName ( ) );
                pMap.put ( "version", "V:" + processDefinition.getVersion ( ) );
                pMap.put ( "resourceName", processDefinition.getResourceName ( ) );
                pMap.put ( "diagramResourceName", processDefinition.getDiagramResourceName ( ) );
                pMap.put ( "deploymentId", processDefinition.getDeploymentId ( ) );
                pMap.put ( "suspended", processDefinition.isSuspended ( ) );
                pMap.put ( "deploymentTime", deployment.getDeploymentTime ( ) );
            }else {
                pMap.put ( "version", m.getVersion ( ) );
                pMap.put ( "category", m.getCategory ( ) );
            }
            flowModel.setProcDef ( pMap );
        }
        page.setRecords ( flowModelList );
        return page;
    }

    /**
     * 创建模型
     *
     * @param model
     * @param values
     * @param forceNewVersion
     * @return
     */
    public ModelRepresentation createModel(Model model, MultiValueMap <String, String> values, boolean forceNewVersion) {
        String name = values.getFirst ( "name" );
        String key = (values.getFirst ( "key" )).replaceAll ( " ", "" );
        String description = values.getFirst ( "description" );
        ModelKeyRepresentation modelKeyInfo = ModelUtils.validateModelKey ( model, key );
        if ( modelKeyInfo.isKeyAlreadyExists ( ) ) {
            throw new RuntimeException ( "Model with provided key already exists " + key );
        } else {
            boolean newVersion = false;
            if ( forceNewVersion ) {
                newVersion = true;
            }

            String xml = values.getFirst ( "json_xml" );
            model.setKey ( key );
            model.setName ( name );
            model.setMetaInfo ( description );
            if ( newVersion ) {
                model.setVersion ( model.getVersion ( ) + 1 );
            }
            repositoryService.saveModel ( model );
            repositoryService.addModelEditorSource ( model.getId ( ), StrUtil.utf8Bytes ( xml ) );
            return new ModelRepresentation ( model );
        }
    }


    /**
     * 更新模型
     *
     * @param model
     * @param values
     * @param forceNewVersion
     * @return
     */
    public ModelRepresentation updateModel(Model model, MultiValueMap <String, String> values, boolean forceNewVersion) {
        String name = values.getFirst ( "name" );
        String key = (values.getFirst ( "key" )).replaceAll ( " ", "" );
        String description = values.getFirst ( "description" );
        String isNewVersionString = values.getFirst ( "newversion" );
        ModelKeyRepresentation modelKeyInfo = ModelUtils.validateModelKey ( model, key );
        if ( modelKeyInfo.isKeyAlreadyExists ( ) ) {
            throw new RuntimeException ( "Model with provided key already exists " + key );
        } else {
            boolean newVersion = false;
            if ( forceNewVersion ) {
                newVersion = true;
            } else if ( isNewVersionString != null ) {
                newVersion = "true".equals ( isNewVersionString );
            }

            String xml = values.getFirst ( "json_xml" );
            model.setKey ( key );
            model.setName ( name );
            model.setMetaInfo ( description );
            if ( newVersion ) {
                model.setVersion ( model.getVersion ( ) + 1 );
            }
            repositoryService.saveModel ( model );
            repositoryService.addModelEditorSource ( model.getId ( ), StrUtil.utf8Bytes ( xml ) );

            return new ModelRepresentation ( model );
        }
    }


    /**
     * 检查key是否重复
     *
     * @param modelRepresentation
     */
    public void checkForDuplicateKey(ModelRepresentation modelRepresentation) {
        Model checkModel = repositoryService.createModelQuery ( ).modelKey ( modelRepresentation.getKey ( ) ).singleResult ( );
        if ( ObjectUtil.isNotNull ( checkModel ) ) {
            throw new RuntimeException ( "模型key已存在！" );
        }
    }


    /*
     * 根据Model部署流程
     */
    public String deploy(String id, String category) {
        String message = "";
        try {
            Model model = repositoryService.getModel ( id );
            byte[] bpmnBytes = repositoryService.getModelEditorSource ( id );

            String processName = model.getName ( );
            if ( !StringUtils.endsWith ( processName, ".bpmn20.xml" ) ) {
                processName += ".bpmn20.xml";
            }
            Deployment deployment = repositoryService.createDeployment ( )
                    .addBytes ( processName, bpmnBytes )
                    .name ( model.getName ( ) )
                    .key ( model.getKey ( ) )
                    .deploy ( );

            log.debug ( "流程部署--------deploy：" + deployment );
            List <ProcessDefinition> list = repositoryService.createProcessDefinitionQuery ( )
                    .deploymentId ( deployment.getId ( ) ).list ( );

            // 设置流程分类
            for (ProcessDefinition processDefinition : list) {
                if ( StringUtils.isNotBlank ( category ) ) {
                    repositoryService.setProcessDefinitionCategory ( processDefinition.getId ( ), category );
                }
                message += "部署成功，流程ID=" + processDefinition.getId ( );
            }

            if ( list.size ( ) == 0 ) {
                message = "部署失败，没有流程。";
            }

        } catch (Exception e) {
            throw new FlowableException ( "设计模型图不正确，检查模型正确性，模型ID=" + id, e );
        }
        return message;
    }

    /**
     * 导出model的xml文件
     *
     * @throws IOException
     */
    public String getBpmnXml(String modelId, HttpServletResponse response) {
        try {

            byte[] xmlBytes = repositoryService.getModelEditorSource ( modelId );
            return new String ( xmlBytes );
        } catch (Exception e) {
            throw new FlowableException ( "导出model的xml文件失败，模型定义ID=" + modelId, e );
        }

    }

    public void generateBpmn20Xml(HttpServletResponse response, String modelId) {

        Model model = repositoryService.getModel ( modelId );
        String name = model.getName ( ).replaceAll ( " ", "_" ) + ".bpmn20.xml";
        String encodedName = null;

        try {
            encodedName = "UTF-8''" + URLEncoder.encode ( name, StandardCharsets.UTF_8 );
        } catch (Exception var12) {
            log.warn ( "Failed to encode name {}", name );
        }

        String contentDispositionValue = "attachment; filename=" + name;
        if ( encodedName != null ) {
            contentDispositionValue = contentDispositionValue + "; filename*=" + encodedName;
        }

        response.setHeader ( "Content-Disposition", contentDispositionValue );
        ServletOutputStream servletOutputStream = null;
        try {
            servletOutputStream = response.getOutputStream ( );
            response.setContentType ( "application/xml" );
            byte[] xmlBytes = repositoryService.getModelEditorSource ( modelId );
            BufferedInputStream in = new BufferedInputStream ( new ByteArrayInputStream ( xmlBytes ) );
            byte[] buffer = new byte[8096];

            while (true) {
                int count = in.read ( buffer );
                if ( count == -1 ) {
                    servletOutputStream.flush ( );
                    servletOutputStream.close ( );
                    break;
                }

                servletOutputStream.write ( buffer, 0, count );
            }
        } catch (IOException e) {
            throw new RuntimeException ( e );
        }
    }

    /**
     * 更新Model分类
     */
    public void updateCategory(String id, String category) {
        Model modelData = repositoryService.getModel ( id );
        modelData.setCategory ( category );
        repositoryService.saveModel ( modelData );
    }

    /**
     * 删除模型
     *
     * @param id
     * @return
     */
    public void delete(String id) {
        Model model = repositoryService.getModel ( id );
        try {
            this.deleteDeployment ( model.getKey ( ) );
            repositoryService.deleteModel ( id );
            taskDefExtensionService.deleteByProcessDefId ( model.getKey ( ) );
            nodeSettingService.deleteByProcessDefId ( model.getKey ( ) );
        } catch (Exception var4) {
            log.error ( "Error while deleting: ", var4 );
            throw new RuntimeException ( "Model cannot be deleted: " + id );
        }
    }

    /**
     * 级联删除流程定义
     *
     * @param key
     * @return
     */
    public void deleteDeployment(String key) {
        ProcessDefinition processDefinition = flowProcessService.getProcessDefinitionByKey ( key );
        if ( processDefinition == null ) {
        } else {
            try {
                repositoryService.deleteDeployment ( processDefinition.getDeploymentId ( ), true );

            } catch (Exception var4) {
                log.error ( "Error while deleting: ", var4 );
                throw new RuntimeException ( "Process cannot be deleted: " + processDefinition.getDeploymentId ( ) );
            }
            this.deleteDeployment ( key );
        }


    }

    public String createModelJson(ModelRepresentation model) {
        String json = null;
        Exception e;
        if (Integer.valueOf(2).equals(model.getModelType())) {
            try {
                json = this.objectMapper.writeValueAsString(new SimpleFormModel ());
            } catch (Exception var28) {
                e = var28;
                log.error("Error creating form model", e);
                throw new RuntimeException ("Error creating form");
            }
        } else {
            ObjectNode propertiesNode;
            ObjectNode childNode;
            ObjectNode lowerRightNode;
            ObjectNode boundsNode;
            ObjectNode editorNode;
            ObjectNode stencilSetNode;
            if (Integer.valueOf(5).equals(model.getModelType())) {
                editorNode = this.objectMapper.createObjectNode();
                editorNode.put("id", "canvas");
                editorNode.put("resourceId", "canvas");
                stencilSetNode = this.objectMapper.createObjectNode();
                stencilSetNode.put("namespace", "http://b3mn.org/stencilset/cmmn1.1#");
                editorNode.set("stencilset", stencilSetNode);
                propertiesNode = this.objectMapper.createObjectNode();
                propertiesNode.put("case_id", model.getKey());
                propertiesNode.put("name", model.getName());
                if (StringUtils.isNotEmpty(model.getDescription())) {
                    propertiesNode.put("documentation", model.getDescription());
                }

                editorNode.set("properties", propertiesNode);
                ArrayNode childShapeArray = this.objectMapper.createArrayNode();
                editorNode.set("childShapes", childShapeArray);
                childNode = this.objectMapper.createObjectNode();
                childShapeArray.add(childNode);
                propertiesNode = this.objectMapper.createObjectNode();
                childNode.set("bounds", propertiesNode);
                lowerRightNode = this.objectMapper.createObjectNode();
                propertiesNode.set("lowerRight", lowerRightNode);
                lowerRightNode.put("x", 758);
                lowerRightNode.put("y", 754);
                childNode = this.objectMapper.createObjectNode();
                propertiesNode.set("upperLeft", childNode);
                childNode.put("x", 40);
                childNode.put("y", 40);
                childNode.set("childShapes", this.objectMapper.createArrayNode());
                childNode.set("dockers", this.objectMapper.createArrayNode());
                childNode.set("outgoing", this.objectMapper.createArrayNode());
                childNode.put("resourceId", "casePlanModel");
                boundsNode = this.objectMapper.createObjectNode();
                childNode.set("stencil", boundsNode);
                boundsNode.put("id", "CasePlanModel");
                json = editorNode.toString();
            } else if (Integer.valueOf(6).equals(model.getModelType())) {
                editorNode = this.objectMapper.createObjectNode();
                editorNode.put("id", "canvas");
                editorNode.put("resourceId", "canvas");
                stencilSetNode = this.objectMapper.createObjectNode();
                stencilSetNode.put("namespace", "http://b3mn.org/stencilset/dmn1.2#");
                editorNode.set("stencilset", stencilSetNode);
                propertiesNode = this.objectMapper.createObjectNode();
                editorNode.set("bounds", propertiesNode);
                ObjectNode lowerRightCanvasNode = this.objectMapper.createObjectNode();
                propertiesNode.set("lowerRight", lowerRightCanvasNode);
                lowerRightCanvasNode.put("x", 1200);
                lowerRightCanvasNode.put("y", 1050);
                childNode = this.objectMapper.createObjectNode();
                propertiesNode.set("upperLeft", childNode);
                childNode.put("x", 0);
                childNode.put("y", 0);
                propertiesNode = this.objectMapper.createObjectNode();
                propertiesNode.put("drd_id", model.getKey());
                propertiesNode.put("name", model.getName());
                if (StringUtils.isNotEmpty(model.getDescription())) {
                    propertiesNode.put("documentation", model.getDescription());
                }

                editorNode.set("properties", propertiesNode);
                ArrayNode childShapeArray = this.objectMapper.createArrayNode();
                editorNode.set("childShapes", childShapeArray);
                childNode = this.objectMapper.createObjectNode();
                childShapeArray.add(childNode);
                boundsNode = this.objectMapper.createObjectNode();
                childNode.set("bounds", boundsNode);
                lowerRightNode = this.objectMapper.createObjectNode();
                boundsNode.set("lowerRight", lowerRightNode);
                lowerRightNode.put("x", 750);
                lowerRightNode.put("y", 554);
                ObjectNode upperLeftNode = this.objectMapper.createObjectNode();
                boundsNode.set("upperLeft", upperLeftNode);
                upperLeftNode.put("x", 150);
                upperLeftNode.put("y", 74);
                ArrayNode childShapes = this.objectMapper.createArrayNode();
                childNode.set("childShapes", childShapes);
                childNode.set("dockers", this.objectMapper.createArrayNode());
                childNode.set("outgoing", this.objectMapper.createArrayNode());
                childNode.put("resourceId", "expandedDecisionService");
                ObjectNode stencilNode = this.objectMapper.createObjectNode();
                childNode.set("stencil", stencilNode);
                stencilNode.put("id", "ExpandedDecisionService");
                ObjectNode outgoingDecisionsShape = this.objectMapper.createObjectNode();
                childShapes.add(outgoingDecisionsShape);
                ObjectNode outgoingDecisionsStencilNode = this.objectMapper.createObjectNode();
                outgoingDecisionsShape.set("stencil", outgoingDecisionsStencilNode);
                outgoingDecisionsStencilNode.put("id", "OutputDecisionsDecisionServiceSection");
                outgoingDecisionsShape.set("childShapes", this.objectMapper.createArrayNode());
                outgoingDecisionsShape.set("dockers", this.objectMapper.createArrayNode());
                outgoingDecisionsShape.set("outgoing", this.objectMapper.createArrayNode());
                outgoingDecisionsShape.put("resourceId", "outputDecisions");
                ObjectNode outgoingDecisionsBoundsNode = this.objectMapper.createObjectNode();
                outgoingDecisionsShape.set("bounds", outgoingDecisionsBoundsNode);
                ObjectNode outgoingDecisionsLowerRightNode = this.objectMapper.createObjectNode();
                outgoingDecisionsBoundsNode.set("lowerRight", outgoingDecisionsLowerRightNode);
                outgoingDecisionsLowerRightNode.put("x", 600);
                outgoingDecisionsLowerRightNode.put("y", 240);
                ObjectNode outgoingDecisionsUpperLeftNode = this.objectMapper.createObjectNode();
                outgoingDecisionsBoundsNode.set("upperLeft", outgoingDecisionsUpperLeftNode);
                outgoingDecisionsUpperLeftNode.put("x", 0);
                outgoingDecisionsUpperLeftNode.put("y", 0);
                ObjectNode encapsulatedDecisionsShape = this.objectMapper.createObjectNode();
                childShapes.add(encapsulatedDecisionsShape);
                ObjectNode encapsulatedDecisionsStencilNode = this.objectMapper.createObjectNode();
                encapsulatedDecisionsShape.set("stencil", encapsulatedDecisionsStencilNode);
                encapsulatedDecisionsStencilNode.put("id", "EncapsulatedDecisionsDecisionServiceSection");
                encapsulatedDecisionsShape.set("childShapes", this.objectMapper.createArrayNode());
                encapsulatedDecisionsShape.set("dockers", this.objectMapper.createArrayNode());
                encapsulatedDecisionsShape.set("outgoing", this.objectMapper.createArrayNode());
                encapsulatedDecisionsShape.put("resourceId", "encapsulatedDecisions");
                ObjectNode encapsulatedDecisionsBoundsNode = this.objectMapper.createObjectNode();
                encapsulatedDecisionsShape.set("bounds", encapsulatedDecisionsBoundsNode);
                ObjectNode encapsulatedDecisionsLowerRightNode = this.objectMapper.createObjectNode();
                encapsulatedDecisionsBoundsNode.set("lowerRight", encapsulatedDecisionsLowerRightNode);
                encapsulatedDecisionsLowerRightNode.put("x", 600);
                encapsulatedDecisionsLowerRightNode.put("y", 480);
                ObjectNode encapsulatedDecisionsUpperLeftNode = this.objectMapper.createObjectNode();
                encapsulatedDecisionsBoundsNode.set("upperLeft", encapsulatedDecisionsUpperLeftNode);
                encapsulatedDecisionsUpperLeftNode.put("x", 0);
                encapsulatedDecisionsUpperLeftNode.put("y", 240);
                json = editorNode.toString();
            } else {
                editorNode = this.objectMapper.createObjectNode();
                editorNode.put("id", "canvas");
                editorNode.put("resourceId", "canvas");
                stencilSetNode = this.objectMapper.createObjectNode();
                stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
                editorNode.set("stencilset", stencilSetNode);
                propertiesNode = this.objectMapper.createObjectNode();
                propertiesNode.put("process_id", model.getKey());
                propertiesNode.put("name", model.getName());
                if (StringUtils.isNotEmpty(model.getDescription())) {
                    propertiesNode.put("documentation", model.getDescription());
                }

                editorNode.set("properties", propertiesNode);
                ArrayNode childShapeArray = this.objectMapper.createArrayNode();
                editorNode.set("childShapes", childShapeArray);
                childNode = this.objectMapper.createObjectNode();
                childShapeArray.add(childNode);
                propertiesNode = this.objectMapper.createObjectNode();
                childNode.set("bounds", propertiesNode);
                lowerRightNode = this.objectMapper.createObjectNode();
                propertiesNode.set("lowerRight", lowerRightNode);
                lowerRightNode.put("x", 130);
                lowerRightNode.put("y", 193);
                childNode = this.objectMapper.createObjectNode();
                propertiesNode.set("upperLeft", childNode);
                childNode.put("x", 100);
                childNode.put("y", 163);
                childNode.set("childShapes", this.objectMapper.createArrayNode());
                childNode.set("dockers", this.objectMapper.createArrayNode());
                childNode.set("outgoing", this.objectMapper.createArrayNode());
                childNode.put("resourceId", "startEvent1");
                boundsNode = this.objectMapper.createObjectNode();
                childNode.set("stencil", boundsNode);
                boundsNode.put("id", "StartNoneEvent");
                json = editorNode.toString();
            }
        }

        return json;
    }


}
