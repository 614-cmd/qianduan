/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.controller;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.extension.domain.FormRef;
import com.jeeplus.extension.domain.FormDefinitionJson;
import com.jeeplus.extension.service.FormRefService;
import com.jeeplus.extension.service.FormDefinitionJsonService;
import com.jeeplus.extension.service.FormDefinitionService;
import com.jeeplus.extension.service.dto.FormDefinitionDTO;
import com.jeeplus.flowable.model.Flow;
import com.jeeplus.flowable.service.FlowTaskService;
import com.jeeplus.form.constant.TableColumn;
import com.jeeplus.form.service.GenerateFormService;
import com.jeeplus.form.service.dto.ColumnDTO;
import com.jeeplus.form.utils.FormJsonUtils;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.json.JSONObject;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.StartEvent;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.*;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 流程个人任务相关Controller
 *
 * @author jeeplus
 * @version 2021-09-03
 */
@Tag(name = "流程表单")
@RestController
@RequestMapping("/flowable/form")
public class FlowableFormController {

    @Autowired
    private FlowTaskService flowTaskService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private FormRefService formRefService;
    @Autowired
    private FormDefinitionService formDefinitionService;
    @Autowired
    private FormDefinitionJsonService formDefinitionJsonService;
    @Autowired
    private GenerateFormService generateFormService;


    /**
     * 启动流程动态表单
     * @param processDefinitionId 流程定义ID
     */
    @ApiLog("启动流程动态表单")
    @Operation(summary = "启动流程动态表单")
    @PostMapping("submitStartFormData")
    public ResponseEntity submitStartFormData(String assignee, String processDefinitionId, String title, @RequestParam("data") String data) throws Exception {
        StartFormData formData = formService.getStartFormData ( processDefinitionId );//拿取流程启动前的表单字段。
        List <FormProperty> formProperties = formData.getFormProperties ( );//获取表单字段值
        JSONObject jData = JSONObject.fromObject ( data );

        // 设置流程变量
        Map <String, String> formValues = new HashMap <String, String> ( );
        String userId = UserUtils.getCurrentUserDTO ( ).getId ( );
        String userName = UserUtils.get ( userId ).getName ( );
        formValues.put ( "userName", userName );

        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId ( userId );

        // 设置流程标题
        if ( StrUtil.isNotBlank ( title ) ) {
            formValues.put ( "title", title );
        }

        /**
         * 同步流程变量表
         */
        for (FormProperty formProperty : formProperties) {
            if ( formProperty.isWritable ( ) ) {
                if ( jData.has ( formProperty.getId ( ) ) ) {
                    String value = jData.getString ( formProperty.getId ( ) );//拿取具体参数值
                    formValues.put ( formProperty.getId ( ), value );
                }
            }
        }

        String procInsId = formService.submitStartFormData ( processDefinitionId, formValues ).getId ( );//启动流程，提交表单

        /**
         * 同步物理表数据
         */
        FormDefinitionJson formDefinitionJson = formDefinitionJsonService.getById ( formData.getFormKey ( ) );
        FormDefinitionDTO formDefinitionDTO = formDefinitionService.getById ( formDefinitionJson.getFormDefinitionId ( ) );
        if ( formDefinitionDTO != null && CommonConstants.YES.equals ( formDefinitionDTO.getIsRelatedTable ( ) ) ) {
            List <ColumnDTO> fieldArra = FormJsonUtils.newInstance ( ).getFields ( formDefinitionJson.getJson ( ) );
            HashMap <String, String> map = new HashMap ( );
            for (ColumnDTO column : fieldArra) {
                map.put ( column.getModel ( ), column.getType ( ) );
            }
            String businessId = generateFormService.executeInsert ( formDefinitionDTO.getDataSource ( ).getId ( ), formDefinitionDTO.getTableName ( ), formDefinitionJson.getJson ( ), jData, map );
            FormRef fd = new FormRef ( );
            fd.setProcDefId ( processDefinitionId );
            fd.setProcInsId ( procInsId );
            fd.setBusinessId ( businessId );
            fd.setBusinessTable ( formDefinitionDTO.getTableName ( ) );
            formRefService.save ( fd );
        }

        //指定下一步处理人
        if ( StrUtil.isNotBlank ( assignee ) ) {
            Task task = taskService.createTaskQuery ( ).processInstanceId ( procInsId ).active ( ).singleResult ( );
            if ( task != null ) {
                taskService.setAssignee ( task.getId ( ), assignee );
            }
        }
        return ResponseEntity.ok ( procInsId );
    }

    /**
     * 获取表单数据
     *
     * @param taskId 任务ID
     */
    @ApiLog("获取表单数据")
    @Operation(summary = "获取表单数据")
    @GetMapping("getTaskFormData")
    public ResponseEntity getTaskFormData(String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData ( taskId );//根据任务ID拿取表单数据
        return ResponseEntity.ok ( taskFormData.getFormProperties ( ) );
    }

    /**
     * 获取表单启动弄数据
     *
     * @param processDefinitionId 流程定义id
     */
    @ApiLog("获取表单启动弄数据")
    @Operation(summary = "获取表单启动弄数据")
    @GetMapping("getStartFormData")
    public ResponseEntity getStartFormData(String processDefinitionId) {
        StartFormData startFormData = formService.getStartFormData ( processDefinitionId );//根据流程定义ID拿取表单数据
        return ResponseEntity.ok ( startFormData.getFormProperties ( ) );
    }

    /**
     * 获取历史表单数据
     *
     * @param processInstanceId 流程实例Id
     */
    @ApiLog("获取历史表单数据")
    @Operation(summary = "获取历史表单数据")
    @GetMapping("getHistoryTaskFormData")
    public ResponseEntity getHistroyTaskFormData(String processInstanceId, String procDefId, String taskDefKey) {
        List <HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery ( ).processInstanceId ( processInstanceId )
                .list ( );
        HashMap <String, Object> historicVariableMap = new HashMap <> ( );
        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            historicVariableMap.put ( historicVariableInstance.getVariableName ( ), historicVariableInstance.getValue ( ) );
        }

        List <Map> list = Lists.newArrayList ( );
        FlowElement node = repositoryService.getBpmnModel ( procDefId ).getFlowElement ( taskDefKey );
        if ( node != null ) {
            List <org.flowable.bpmn.model.FormProperty> formPropertyList = Lists.newArrayList ( );
            if ( node instanceof UserTask ) {
                formPropertyList = ((UserTask) node).getFormProperties ( );
            } else if ( node instanceof StartEvent ) {
                formPropertyList = ((StartEvent) node).getFormProperties ( );
            }
            for (org.flowable.bpmn.model.FormProperty formProperty : formPropertyList) {
                try {
                    HashMap <String, Object> formPropertyMap = new HashMap <> ( );

                    Field field = formProperty.getClass ( ).getDeclaredField ( "readable" );
                    field.setAccessible ( true );
                    boolean readable = (boolean) field.get ( formProperty );
                    if ( readable ) {
                        formPropertyMap.put ( "id", formProperty.getId ( ) );
                        formPropertyMap.put ( "name", formProperty.getName ( ) );
                        formPropertyMap.put ( "value", historicVariableMap.get ( formProperty.getId ( ) ) );
                        formPropertyMap.put ( "readable", readable );
                        list.add ( formPropertyMap );
                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                }

            }
        } else {
            List <FormProperty> formPropertyList = formService.getStartFormData ( procDefId ).getFormProperties ( );
            for (FormProperty formProperty : formPropertyList) {
                try {
                    HashMap <String, Object> formPropertyMap = new HashMap <> ( );
                    boolean readable = formProperty.isReadable ( );
                    if ( readable ) {
                        formPropertyMap.put ( "id", formProperty.getId ( ) );
                        formPropertyMap.put ( "name", formProperty.getName ( ) );
                        formPropertyMap.put ( "value", historicVariableMap.get ( formProperty.getId ( ) ) );
                        formPropertyMap.put ( "readable", readable );
                        list.add ( formPropertyMap );
                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        }
        return ResponseEntity.ok ( list );
    }

    /**
     * 提交表单
     */
    @ApiLog("提交表单")
    @Operation(summary = "提交表单")
    @PostMapping("submitTaskFormData")
    public ResponseEntity submitTaskFormData(Flow flow, @RequestParam("data") String data) throws Exception {
        TaskFormData taskFormData = formService.getTaskFormData ( flow.getTaskId ( ) );//根据任务ID拿取表单数据
        List <FormProperty> formProperties = taskFormData.getFormProperties ( );//获取表单字段值
        JSONObject jData = JSONObject.fromObject ( data );
        Map <String, Object> formValues = new HashMap <String, Object> ( );
        HashSet noCommitValues = new HashSet ( );

        for (FormProperty formProperty : formProperties) {
            if ( !formProperty.isWritable ( ) ) {
                noCommitValues.add ( formProperty.getId ( ) );
            }
        }

        for (Object str : jData.keySet ( )) {
            if ( !noCommitValues.contains ( str.toString ( ) ) ) {
                Object value = jData.get ( str.toString ( ) );//拿取具体参数值
                formValues.put ( str.toString ( ), value );    //将ID和value存入map中
            }
        }
        formValues.put ( "assignee", "" );// 避免jackson序列化错误
        flowTaskService.complete ( flow, formValues );  //提交用户任务表单并且完成任务。

        /**
         * 同步物理表数据
         */
        FormDefinitionJson formDefinitionJson = formDefinitionJsonService.getById ( taskFormData.getFormKey ( ) );
        FormDefinitionDTO formDefinitionDTO = formDefinitionService.getById ( formDefinitionJson.getFormDefinitionId ( ) );
        if ( formDefinitionDTO != null && CommonConstants.YES.equals ( formDefinitionDTO.getIsRelatedTable ( ) ) ) {
            List <ColumnDTO> fieldArr = FormJsonUtils.newInstance ( ).getFields ( formDefinitionJson.getJson ( ) );
            HashMap <String, String> map = new HashMap ( );
            for (ColumnDTO column : fieldArr) {
                map.put ( column.getModel ( ), column.getType ( ) );
            }
            FormRef formRef = formRefService.lambdaQuery ( ).eq ( FormRef::getProcInsId, flow.getProcInsId ( ) ).eq ( FormRef::getBusinessTable, formDefinitionDTO.getTableName ( ) ).one ( );
            jData.put ( TableColumn.ID, formRef.getBusinessId ( ) );
            generateFormService.executeUpdate ( formDefinitionDTO.getDataSource ( ).getId ( ), formDefinitionDTO.getTableName ( ), formDefinitionJson.getJson ( ), jData, map );
        }


        //指定下一步处理人
        if ( StrUtil.isNotBlank ( flow.getAssignee ( ) ) ) {
            Task task = taskService.createTaskQuery ( ).processInstanceId ( flow.getProcInsId ( ) ).active ( ).singleResult ( );
            if ( task != null ) {
                taskService.setAssignee ( task.getId ( ), flow.getAssignee ( ) );
            }
        }
        return ResponseEntity.ok ( flow.getProcInsId ( ) );
    }


}
