/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.extension.domain.FlowCopy;
import com.jeeplus.extension.service.FlowCopyService;
import com.jeeplus.flowable.service.FlowProcessService;
import com.jeeplus.flowable.common.enums.ProcessStatus;
import com.jeeplus.flowable.vo.ProcessVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 流程定义相关Controller
 *
 * @author jeeplus
 * @version 2021-09-03
 */
@Tag(name = "流程定义")
@RestController
@RequestMapping("/flowable/process")
public class FlowableProcessController {

    @Autowired
    private FlowProcessService flowProcessService;

    @Autowired
    private FlowCopyService flowCopyService;

    /**
     * 流程定义列表
     */
    @ApiLog("流程定义列表")
    @Operation(summary = "流程定义列表")
    @GetMapping("list")
    public ResponseEntity processListData(Page <Map> page, String category) {
        /*
         * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
         */
        page = flowProcessService.processList ( page, category );
        return ResponseEntity.ok ( page );
    }

    @ApiLog("流程是否存在")
    @Operation(summary = "流程是否存在")
    @GetMapping("exist")
    public ResponseEntity exist(String key) {
        /*
         * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
         */
        ProcessDefinition processDefinition = flowProcessService.getProcessDefinitionByKey ( key );
        if ( processDefinition == null ) {
            return ResponseEntity.ok ( false );
        } else {
            return ResponseEntity.ok ( true );
        }
    }

    @ApiLog("运行中的流程")
    @Operation(summary = "运行中的流程")
    @GetMapping("runningData")
    public ResponseEntity runningListData(Page <ProcessInstance> page, String title, String procInsId, String procDefKey) throws Exception {
        Page <ProcessVO> result = flowProcessService.runningList ( page, title, procInsId, procDefKey );
        return ResponseEntity.ok ( result );
    }

    /**
     * 已结束的实例
     */
    @ApiLog("已结束的实例")
    @Operation(summary = "已结束的实例")
    @GetMapping("historyListData")
    public ResponseEntity historyListData(Page <ProcessVO> page, String title, String procInsId, String procDefKey) throws Exception {
        page = flowProcessService.historyList ( page, title, procInsId, procDefKey );
        return ResponseEntity.ok ( page );
    }

    /**
     * 读取资源，通过部署ID
     *
     * @param response
     * @throws Exception
     */
    @ApiLog("读取资源")
    @Operation(summary = "读取资源")
    @GetMapping("resource/read")
    public void resourceRead(String procDefId, String proInsId, String resType, HttpServletResponse response) throws Exception {
        InputStream resourceAsStream = flowProcessService.resourceRead ( procDefId, proInsId, resType );
        byte[] b = new byte[1024];
        int len = -1;
        if ( "xml".equals ( resType ) ) {
            response.reset ( );
            response.setContentType ( "application/octet-stream; charset=utf-8" );
            String fileName = flowProcessService.getProcessDefinition ( procDefId ).getResourceName ( );
            response.setHeader ( "Content-Disposition", URLEncoder.encode ( fileName, StandardCharsets.UTF_8 ) );
        }
        while ((len = resourceAsStream.read ( b, 0, 1024 )) != -1) {
            response.getOutputStream ( ).write ( b, 0, len );
        }
    }

    /**
     * 读取资源，通过部署ID
     *
     * @throws Exception
     */
    @ApiLog("获取流程图")
    @Operation(summary = "获取流程图")
    @GetMapping("getFlowChart")
    public Map getFlowChart(String processDefId) {
        return flowProcessService.getImageStream ( processDefId );

    }

    /**
     * 挂起、激活流程实例
     */
    @ApiLog("挂起、激活流程实例")
    @Operation(summary = "流程挂起、激活流程实例")
    @PutMapping("update/{state}")
    public ResponseEntity updateState(@PathVariable("state") String state, String procDefId) {
        String message = flowProcessService.updateState ( state, procDefId );
        return ResponseEntity.ok ( message );
    }


    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentIds 流程部署ID
     */
    @DemoMode
    @ApiLog("删除部署的流程，级联删除流程实例")
    @Operation(summary = "删除部署的流程，级联删除流程实例")
    @DeleteMapping("delete")
    public ResponseEntity delete(String deploymentIds) {
        String[] idArray = deploymentIds.split ( "," );
        for (String id : idArray) {
            flowProcessService.deleteDeployment ( id );
        }
        return ResponseEntity.ok ( "删除成功!" );
    }

    /**
     * 删除流程实例
     *
     * @param ids    流程实例ID
     * @param reason 删除原因
     */
    @ApiLog("删除流程实例")
    @Operation(summary = "删除流程实例")
    @DeleteMapping("deleteProcIns")
    public ResponseEntity deleteProcIns(String ids, String reason) {
        try {
            for (String id : ids.split ( "," )) {
                flowProcessService.deleteProcIns ( id, reason );
            }
            return ResponseEntity.ok ( "流程实例删除成功!" );
        } catch (FlowableObjectNotFoundException e) {
            return ResponseEntity.ok ( "操作失败，流程已经结束!" );
        }

    }

    /**
     * 流程撤回
     *
     * @param id 流程实例ID
     */
    @ApiLog("流程撤回")
    @Operation(summary = "流程撤回")
    @RequestMapping("revokeProcIns")
    public ResponseEntity revokeProcIns(String id) {
        try {
            flowProcessService.callBackProcessInstanceById ( id );

            return ResponseEntity.ok ( "流程撤销成功!" );
        } catch (FlowableObjectNotFoundException e) {
            return ResponseEntity.ok ( "操作失败，流程已经结束!" );
        }
    }

    /**
     * 流程终止
     *
     * @param id 流程实例ID
     */
    @ApiLog("流程终止")
    @Operation(summary = "流程终止")
    @RequestMapping("stop")
    public ResponseEntity stopProcIns(String id, String message) {
        try {
            flowProcessService.stopProcessInstanceById ( id, ProcessStatus.STOP, message );
            return ResponseEntity.ok ( "终止流程成功!" );
        } catch (FlowableObjectNotFoundException e) {
            return ResponseEntity.ok ( "操作失败，流程已经结束!" );
        }

    }

    /**
     * 查询流程状态
     */
    @ApiLog("查询流程状态")
    @Operation(summary = "查询流程状态")
    @GetMapping("queryProcessStatus")
    public ResponseEntity queryProcessStatus(String procDefId, String procInsId) throws Exception {
        ProcessVO processVo = flowProcessService.queryProcessState ( procDefId, procInsId );
        return ResponseEntity.ok ( processVo.getCode ( ) );

    }

    /**
     * 删除历史流程实例
     *
     * @param procInsIds 流程实例ID
     */
    @ApiLog("删除历史流程实例")
    @Operation(summary = "删除历史流程实例")
    @DeleteMapping("deleteAllProcIns")
    public ResponseEntity deleteAllProcIns(String procInsIds) {
        String[] procInsIdArra = procInsIds.split ( "," );
        for (String procInsId : procInsIdArra) {
            flowProcessService.delHistoryProcInsById ( procInsId );
            flowCopyService.lambdaUpdate ( ).eq ( FlowCopy::getProcInsId, procInsId ).remove ( );
        }
        return ResponseEntity.ok ( "删除成功，流程实例ID=" + procInsIds );
    }

}
