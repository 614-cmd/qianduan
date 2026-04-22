/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.flowable.common.cmd.AddSequenceMultiInstanceCmd;
import com.jeeplus.flowable.common.cmd.BackUserTaskCmd;
import com.jeeplus.flowable.common.cmd.DeleteSequenceMultiInstanceCmd;
import com.jeeplus.flowable.common.constant.ErrorConstant;
import com.jeeplus.flowable.common.constant.FlowableConstant;
import com.jeeplus.flowable.common.enums.ActionType;
import com.jeeplus.flowable.common.enums.ProcessStatus;
import com.jeeplus.flowable.mapper.FlowMapper;
import com.jeeplus.flowable.vo.AddMultiTaskVO;
import com.jeeplus.flowable.vo.DeleteMultiTaskVO;
import com.jeeplus.flowable.model.Flow;
import com.jeeplus.flowable.model.TaskComment;
import com.jeeplus.flowable.utils.FlowableUtils;
import com.jeeplus.flowable.utils.ProcessDefCache;
import com.jeeplus.flowable.vo.*;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.flowable.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.flowable.variable.api.history.HistoricVariableInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流程定义相关Service
 *
 * @author jeeplus
 * @version 2021-09-03
 */
@Slf4j
@Service
@Transactional
public class FlowTaskService {

    @Autowired
    private FlowMapper flowMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private FlowProcessService flowProcessService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取待办任务列表
     *
     * @return
     */
    public Page <ProcessVO> todoList(Page <ProcessVO> page, Flow flow) {
        String userId = UserUtils.getCurrentUserDTO ( ).getId ( );//ObjectUtils.toString(UserUtils.getUser().getId());
        // =============== 已经签收或者等待签收的任务  ===============
        TaskQuery todoTaskQuery = taskService.createTaskQuery ( ).taskCandidateOrAssigned ( userId ).active ( )
                .includeProcessVariables ( ).orderByTaskCreateTime ( ).desc ( );

        // 设置查询条件
        if ( StrUtil.isNotBlank ( flow.getProcDefKey ( ) ) ) {
            todoTaskQuery.processDefinitionKey ( flow.getProcDefKey ( ) );
        }
        if ( flow.getBeginDate ( ) != null ) {
            todoTaskQuery.taskCreatedAfter ( flow.getBeginDate ( ) );
        }
        if ( flow.getEndDate ( ) != null ) {
            todoTaskQuery.taskCreatedBefore ( flow.getEndDate ( ) );
        }
        if ( StrUtil.isNotBlank ( flow.getTitle ( ) ) ) {
            todoTaskQuery.processVariableValueLike ( FlowableConstant.TITLE, "%" + flow.getTitle ( ) + "%" );
        }


        long total = todoTaskQuery.count ( );
        page.setTotal ( total );

        List <Task> todoList;
        // 查询列表
        if ( page.getSize ( ) == -1 ) {//不分页
            todoList = todoTaskQuery.list ( );
        } else {
            int start = (int) ((page.getCurrent ( ) - 1) * page.getSize ( ));
            int size = (int) (page.getSize ( ));
            todoList = todoTaskQuery.listPage ( start, size );
        }
        List records = Lists.newArrayList ( );
        for (Task task : todoList) {
            Process process = SpringUtil.getBean ( RepositoryService.class ).getBpmnModel ( task.getProcessDefinitionId ( ) ).getMainProcess ( );
            ProcessVO processVo = new ProcessVO ( );
            TaskVO taskVo = new TaskVO ( task );
            taskVo.setProcessDefKey ( process.getId ( ) );
            processVo.setTask ( taskVo );
            processVo.setVars ( task.getProcessVariables ( ) );
            processVo.setProcessDefinitionName ( ProcessDefCache.get ( task.getProcessDefinitionId ( ) ).getName ( ) );
            processVo.setVersion ( ProcessDefCache.get ( task.getProcessDefinitionId ( ) ).getVersion ( ) );
            processVo.setStatus ( "todo" );
            records.add ( processVo );
        }
        page.setRecords ( records );
        return page;
    }

    /**
     * 获取已办任务列表
     *
     * @param page
     * @return
     */
    public Page <HisTaskVO> historicList(Page <HisTaskVO> page, Flow act) {
        String userId = UserUtils.getCurrentUserDTO ( ).getId ( );

        HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery ( ).taskAssignee ( userId ).finished ( )
                .includeProcessVariables ( ).orderByHistoricTaskInstanceEndTime ( ).desc ( );

        // 设置查询条件
        if ( StrUtil.isNotBlank ( act.getProcDefKey ( ) ) ) {
            histTaskQuery.processDefinitionKey ( act.getProcDefKey ( ) );
        }
        if ( act.getBeginDate ( ) != null ) {
            histTaskQuery.taskCompletedAfter ( act.getBeginDate ( ) );
        }
        if ( act.getEndDate ( ) != null ) {
            histTaskQuery.taskCompletedBefore ( act.getEndDate ( ) );
        }
        if ( act.getTitle ( ) != null ) {
            histTaskQuery.processVariableValueLike ( FlowableConstant.TITLE, "%" + act.getTitle ( ) + "%" );
        }

        // 查询总数
        page.setTotal ( histTaskQuery.count ( ) );
        // 查询列表
        List <HistoricTaskInstance> histList;
        if ( page.getSize ( ) == -1 ) {
            histList = histTaskQuery.list ( );
        } else {
            int start = (int) ((page.getCurrent ( ) - 1) * page.getSize ( ));
            int size = (int) (page.getSize ( ));
            histList = histTaskQuery.listPage ( start, size );
        }

        List records = Lists.newArrayList ( );
        for (HistoricTaskInstance histTask : histList) {
            HisTaskVO hisTaskVo = new HisTaskVO ( histTask );
            hisTaskVo.setProcessDefinitionName ( ProcessDefCache.get ( histTask.getProcessDefinitionId ( ) ).getName ( ) );
            hisTaskVo.setBack ( isBack ( histTask ) );
            List <Task> currentTaskList = taskService.createTaskQuery ( ).processInstanceId ( histTask.getProcessInstanceId ( ) ).list ( );
            if ( currentTaskList.size ( ) > 0 ) {
                TaskVO currentTaskVo = new TaskVO ( currentTaskList.get ( 0 ) );
                hisTaskVo.setCurrentTask ( currentTaskVo );
            }

            // 获取意见评论内容

            List <TaskComment> commentList = this.getTaskComments ( histTask.getId ( ) );
            if ( commentList.size ( ) > 0 ) {
                TaskComment comment = commentList.get ( commentList.size ( ) - 1 );
                hisTaskVo.setComment ( comment.getMessage ( ) );
                hisTaskVo.setLevel ( comment.getLevel ( ) );
                hisTaskVo.setType ( comment.getType ( ) );
                hisTaskVo.setStatus ( comment.getStatus ( ) );

            }
            records.add ( hisTaskVo );
        }
        page.setRecords ( records );
        return page;
    }

    /**
     * 获取流转历史任务列表
     *
     * @param procInsId 流程实例
     */
    public List <Flow> historicTaskList(String procInsId) throws Exception {
        List <Flow> actList = Lists.newArrayList ( );
        List <HistoricActivityInstance> list = Lists.newArrayList ( );
        List <HistoricActivityInstance> historicActivityInstances2 = historyService.createHistoricActivityInstanceQuery ( ).processInstanceId ( procInsId )
                .orderByHistoricActivityInstanceStartTime ( ).asc ( ).orderByHistoricActivityInstanceEndTime ( ).asc ( ).list ( );
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances2) {
            if ( historicActivityInstance.getEndTime ( ) != null ) {
                list.add ( historicActivityInstance );
            }
        }

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances2) {
            if ( historicActivityInstance.getEndTime ( ) == null ) {
                list.add ( historicActivityInstance );
            }
        }

        for (int i = 0; i < list.size ( ); i++) {
            HistoricActivityInstance histIns = list.get ( i );
            // 只显示开始节点和结束节点，并且执行人不为空的任务
            if ( StrUtil.isNotBlank ( histIns.getAssignee ( ) )
                    && historyService.createHistoricTaskInstanceQuery ( ).taskId ( histIns.getTaskId ( ) ).count ( ) != 0
                    || BpmnXMLConstants.ELEMENT_TASK_USER.equals ( histIns.getActivityType ( ) ) && histIns.getEndTime ( ) == null
                    || BpmnXMLConstants.ELEMENT_EVENT_START.equals ( histIns.getActivityType ( ) )
                    || BpmnXMLConstants.ELEMENT_EVENT_END.equals ( histIns.getActivityType ( ) ) ) {
                // 获取流程发起人名称
                Flow e = queryTaskState ( histIns );

                actList.add ( e );
            }
        }
        return actList;
    }

    /**
     * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
     *
     * @return
     */
    public String getFormKey(String procDefId, String taskDefKey) {
        String formKey = "";
        if ( StrUtil.isNotBlank ( procDefId ) ) {
            if ( StrUtil.isNotBlank ( taskDefKey ) ) {
                try {
                    formKey = formService.getTaskFormKey ( procDefId, taskDefKey );
                } catch (Exception e) {
                    formKey = "";
                }
            }
            if ( StrUtil.isBlank ( formKey ) ) {
                formKey = formService.getStartFormKey ( procDefId );
            }
            if ( StrUtil.isBlank ( formKey ) ) {
                formKey = "/404";
            }
        }
        log.debug ( "getFormKey: {}", formKey );
        return formKey;
    }

    /**
     * 获取正在运行的流程实例对象
     *
     * @param procInsId
     * @return
     */
    public ProcessInstance getProcIns(String procInsId) {
        return runtimeService.createProcessInstanceQuery ( ).includeProcessVariables ( ).processInstanceId ( procInsId ).singleResult ( );
    }

    /**
     * 获取已经结束流程实例对象
     *
     * @param procInsId
     * @return
     */
    public HistoricProcessInstance getFinishedProcIns(String procInsId) {
        return historyService.createHistoricProcessInstanceQuery ( ).includeProcessVariables ( ).processInstanceId ( procInsId ).singleResult ( );
    }


    /**
     * 获取我发起的流程申请列表
     *
     * @param user
     * @return
     */
    public Page <ProcessVO> getMyStartedProcIns(UserDTO user, Page <ProcessVO> page, Flow flow) throws Exception {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery ( ).startedBy ( user.getId ( ) ).includeProcessVariables ( ).orderByProcessInstanceStartTime ( ).desc ( );
        if ( flow.getBeginDate ( ) != null ) {
            query.startedAfter ( flow.getBeginDate ( ) );
        }
        if ( flow.getEndDate ( ) != null ) {
            query.startedBefore ( flow.getEndDate ( ) );
        }
        if ( StrUtil.isNotBlank ( flow.getTitle ( ) ) ) {
            query.variableValueLike ( FlowableConstant.TITLE, "%" + flow.getTitle ( ) + "%" );
        }

        page.setTotal ( query.count ( ) );
        List <HistoricProcessInstance> histList;
        if ( page.getSize ( ) == -1 ) {//不分页
            histList = query.list ( );
        } else {
            int start = (int) ((page.getCurrent ( ) - 1) * page.getSize ( ));
            int size = (int) (page.getSize ( ));
            histList = query.involvedUser ( user.getId ( ) ).listPage ( start, size );
        }
        List records = Lists.newArrayList ( );
        for (HistoricProcessInstance historicProcessInstance : histList) {
            ProcessVO processVo = flowProcessService.queryProcessState ( historicProcessInstance.getProcessDefinitionId ( ), historicProcessInstance.getId ( ) );
            processVo.setEndTime ( historicProcessInstance.getEndTime ( ) );
            processVo.setStartTime ( historicProcessInstance.getStartTime ( ) );
            processVo.setProcessDefinitionId ( historicProcessInstance.getProcessDefinitionId ( ) );
            processVo.setProcessInstanceId ( historicProcessInstance.getId ( ) );
            processVo.setVars ( historicProcessInstance.getProcessVariables ( ) );
            processVo.setProcessDefinitionName ( historicProcessInstance.getProcessDefinitionName ( ) );
            processVo.setVersion ( historicProcessInstance.getProcessDefinitionVersion ( ) );
            records.add ( processVo );
        }
        page.setRecords ( records );

        return page;
    }


    /**
     * 启动流程
     *
     * @param procDefKey    流程定义KEY
     * @param businessTable 业务表表名
     * @param businessId    业务表编号
     * @param title         流程标题，显示在待办任务标题
     * @return 流程实例ID
     */
    public String startProcess(String procDefKey, String businessTable, String businessId, String title) {
        Map <String, Object> vars = Maps.newHashMap ( );
        return startProcess ( procDefKey, businessTable, businessId, title, vars );
    }

    /**
     * 启动流程
     *
     * @param procDefKey    流程定义KEY
     * @param businessTable 业务表表名
     * @param businessId    业务表编号
     * @param title         流程标题，显示在待办任务标题
     * @param vars          流程变量
     * @return 流程实例ID
     */
    @SuppressWarnings("unused")
    public String startProcess(String procDefKey, String businessTable, String businessId, String title, Map <String, Object> vars) {
        // 设置流程变量
        if ( vars == null ) {
            vars = Maps.newHashMap ( );
        }

        String userId = (String) vars.get ( FlowableConstant.INITIATOR );
        if ( userId == null ) {
            userId = UserUtils.getCurrentUserDTO ( ).getId ( );
        }
        String userName = UserUtils.get ( userId ).getName ( );
        vars.put ( FlowableConstant.USERNAME, userName );

        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId ( userId );

        // 设置流程标题
        if ( StrUtil.isNotBlank ( title ) ) {
            vars.put ( FlowableConstant.TITLE, title );
        }

        // 启动流程
        ProcessInstance procIns = runtimeService.startProcessInstanceByKey ( procDefKey, businessTable + ":" + businessId, vars );

        // 更新业务表流程实例ID
        Flow flow = new Flow ( );
        flow.setBusinessTable ( businessTable );// 业务表名
        flow.setBusinessId ( businessId );  // 业务表ID
        flow.setProcInsId ( procIns.getId ( ) );
        flow.setAssignee ( userId );
        flow.setVars ( vars );
        flowMapper.updateProcInsIdByBusinessId ( flow );
        return flow.getProcInsId ( );
    }


    /**
     * 删除任务
     *
     * @param taskId       任务ID
     * @param deleteReason 删除原因
     */
    public void deleteTask(String taskId, String deleteReason) {
        taskService.deleteTask ( taskId, deleteReason );
    }

    /**
     * 签收任务
     *
     * @param taskId 任务ID
     * @param userId 签收用户ID（用户登录名）
     */
    public void claim(String taskId, String userId) {
        taskService.claim ( taskId, userId );
    }


    /**
     * 提交任务, 并保存意见
     *
     * @param vars 任务变量
     */
    public void complete(Flow flow, Map <String, Object> vars) {
        // 添加意见
        if ( StrUtil.isNotBlank ( flow.getProcInsId ( ) ) ) {
            taskService.addComment ( flow.getTaskId ( ), flow.getProcInsId ( ), flow.getComment ( ).getCommentType ( ), flow.getComment ( ).getFullMessage ( ) );
        }

        // 设置流程变量
        if ( vars == null ) {
            vars = Maps.newHashMap ( );
        }

        // 设置流程标题
        if ( StrUtil.isNotBlank ( flow.getTitle ( ) ) ) {
            vars.put ( FlowableConstant.TITLE, flow.getTitle ( ) );
        }


        Task task = taskService.createTaskQuery ( ).taskId ( flow.getTaskId ( ) ).singleResult ( );
        // owner不为空说明可能存在委托任务
        if ( StrUtil.isNotBlank ( task.getOwner ( ) ) ) {
            DelegationState delegationState = task.getDelegationState ( );
            switch (delegationState) {
                case PENDING:
                    taskService.resolveTask ( flow.getTaskId ( ) );
                    taskService.complete ( flow.getTaskId ( ), vars );
                    break;

                case RESOLVED:
                    // 委托任务已经完成
                    break;

                default:
                    // 不是委托任务
                    taskService.complete ( flow.getTaskId ( ), vars );
                    break;
            }
        } else if ( StrUtil.isBlank ( task.getAssignee ( ) ) ) { // 未签收任务
            // 签收任务
            taskService.claim ( flow.getTaskId ( ), UserUtils.getCurrentUserDTO ( ).getId ( ) );
            // 提交任务
            taskService.complete ( flow.getTaskId ( ), vars );
        } else {
            // 提交任务
            taskService.complete ( flow.getTaskId ( ), vars );
        }
    }


    /**
     * 查询任务节点的状态
     */
    public Flow queryTaskState(HistoricActivityInstance histIns) {
        Flow e = new Flow ( );
        e.setHistIns ( histIns );
        // 获取流程发起人名称
        if ( BpmnXMLConstants.ELEMENT_EVENT_START.equals ( histIns.getActivityType ( ) ) ) {
            e.setAssigneeName ( FlowableConstant.SYSTEM_EVENT_COMMENT );
            List <HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery ( ).processInstanceId ( histIns.getProcessInstanceId ( ) ).orderByProcessInstanceStartTime ( ).asc ( ).list ( );
            if ( il.size ( ) > 0 ) {
                if ( il.get ( 0 ).getStartActivityId ( ).equals ( histIns.getActivityId ( ) ) && StrUtil.isNotBlank ( il.get ( 0 ).getStartUserId ( ) ) ) {
                    UserDTO user = UserUtils.get ( il.get ( 0 ).getStartUserId ( ) );
                    if ( user != null ) {
                        e.setAssignee ( histIns.getAssignee ( ) );
                        e.setAssigneeName ( user.getName ( ) );
                    }
                }
            }
            TaskComment taskComment = new TaskComment ( );
            taskComment.setStatus ( FlowableConstant.START_EVENT_LABEL );
            taskComment.setMessage ( FlowableConstant.START_EVENT_COMMENT );
            e.setComment ( taskComment );
            return e;
        }
        if ( BpmnXMLConstants.ELEMENT_EVENT_END.equals ( histIns.getActivityType ( ) ) ) {
            TaskComment taskComment = new TaskComment ( );
            taskComment.setStatus ( FlowableConstant.END_EVENT_LABEL );
            taskComment.setMessage ( FlowableConstant.END_EVENT_COMMENT );
            e.setAssigneeName ( FlowableConstant.SYSTEM_EVENT_COMMENT );
            e.setComment ( taskComment );
            return e;
        }
        // 获取任务执行人名称
        if ( StrUtil.isNotEmpty ( histIns.getAssignee ( ) ) ) {
            UserDTO user = UserUtils.get ( histIns.getAssignee ( ) );
            if ( user != null ) {
                e.setAssignee ( histIns.getAssignee ( ) );
                e.setAssigneeName ( user.getName ( ) );
            }
        }
        // 获取意见评论内容
        if ( StrUtil.isNotBlank ( histIns.getTaskId ( ) ) ) {
            List <TaskComment> commentList = this.getTaskComments ( histIns.getTaskId ( ) );
            HistoricVariableInstanceQuery action = historyService.createHistoricVariableInstanceQuery ( ).processInstanceId ( histIns.getProcessInstanceId ( ) ).taskId ( histIns.getTaskId ( ) ).variableName ( "_flow_button_name" );
            if ( commentList.size ( ) > 0 ) {
                TaskComment comment = commentList.get ( commentList.size ( ) - 1 );
                e.setComment ( comment );
            } else {
                e.setComment ( new TaskComment ( ) );
            }
        }
        //等待执行的任务
        if ( histIns.getEndTime ( ) == null ) {
            List assignList = Lists.newArrayList ( );
            taskService.getIdentityLinksForTask ( histIns.getTaskId ( ) ).forEach ( identityLink -> {
                assignList.add ( UserUtils.get ( identityLink.getUserId ( ) ) );
            } );
            String assignName = Collections3.extractToString ( assignList, "name", "," );
            TaskComment taskComment = new TaskComment ( );
            taskComment.setStatus ( ActionType.WAITING.getStatus ( ) );
//            taskComment.setMessage (FlowableConstant.WAITING_EVENT_COMMENT);
            e.setComment ( taskComment );
            e.setAssigneeName ( assignName );
        }
        return e;
    }


    public List <TaskComment> getTaskComments(String taskId) {
        return jdbcTemplate.query ( "select * from ACT_HI_COMMENT where TYPE_ like '" + TaskComment.prefix + "%' and TASK_ID_ = '" + taskId + "' order by TIME_ desc", new RowMapper <TaskComment> ( ) {
            @Override
            public TaskComment mapRow(ResultSet rs, int rowNum) throws SQLException {
                TaskComment taskComment = new TaskComment ( );
                taskComment.setCommentType ( rs.getString ( "TYPE_" ) );
                taskComment.setFullMessage ( new String ( rs.getBytes ( "FULL_MSG_" ) ) );
                return taskComment;
            }
        } );
    }


    public Map getDiagram(String processId) {
        Map m = new HashMap ( );
        try {
            String processDefId = "";
            ProcessInstance pi = runtimeService.createProcessInstanceQuery ( ).processInstanceId ( processId ).singleResult ( );
            //流程走完的不显示图
            if ( pi == null ) {
                processDefId = historyService.createHistoricProcessInstanceQuery ( ).processInstanceId ( processId ).singleResult ( ).getProcessDefinitionId ( );
            } else {
                processDefId = pi.getProcessDefinitionId ( );
            }
            BpmnModel bpmnModel = repositoryService.getBpmnModel ( processDefId );
            List <HistoricActivityInstance> historyProcess = getHistoryProcess ( processId );
            Set <String> activityIds = new LinkedHashSet <> ( );
            List <String> flows = new ArrayList <> ( );
            List <Task> currentTasks = taskService.createTaskQuery ( ).processInstanceId ( processId ).active ( ).list ( );
            for (HistoricActivityInstance hi : historyProcess) {
                String activityType = hi.getActivityType ( );
                if ( currentTasks.size ( ) > 0 && currentTasks.get ( 0 ).getTaskDefinitionKey ( ).equals ( hi.getActivityId ( ) ) ) {
                    break;
                } else if ( activityType.equals ( BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW ) || activityType.equals ( BpmnXMLConstants.ELEMENT_GATEWAY_EXCLUSIVE ) ) {
                    flows.add ( hi.getActivityId ( ) );
                } else if ( StrUtil.isNotBlank ( hi.getAssignee ( ) )
                        && historyService.createHistoricTaskInstanceQuery ( ).taskId ( hi.getTaskId ( ) ).count ( ) != 0
                        || BpmnXMLConstants.ELEMENT_TASK_USER.equals ( hi.getActivityType ( ) ) && hi.getEndTime ( ) == null
                        || BpmnXMLConstants.ELEMENT_EVENT_START.equals ( hi.getActivityType ( ) )
                        || BpmnXMLConstants.ELEMENT_EVENT_END.equals ( hi.getActivityType ( ) ) ) {
                    activityIds.add ( hi.getActivityId ( ) );
                }
            }
            if ( currentTasks.size ( ) > 0 ) {
                activityIds.add ( currentTasks.get ( 0 ).getTaskDefinitionKey ( ) );
            }
            byte[] bpmnBytes = new BpmnXMLConverter ( ).convertToXML ( bpmnModel );
            m.put ( "bpmnXml", new String ( bpmnBytes ) );
            m.put ( "flows", flows );
            m.put ( "activityIds", activityIds );
            return m;
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return null;
    }


    /**
     * 任务历史
     *
     * @param processId 部署id
     */
    public List <HistoricActivityInstance> getHistoryProcess(String processId) {
        List <HistoricActivityInstance> list = historyService // 历史相关Service
                .createHistoricActivityInstanceQuery ( ) // 创建历史活动实例查询
                .processInstanceId ( processId ) // 执行流程实例id
                .finished ( ).orderByHistoricActivityInstanceEndTime ( ).asc ( )
                .list ( );
        return list;
    }


    /**
     * 保存审核意见
     *
     * @param flow
     */
    public void auditSave(Flow flow, Map vars) {
        complete ( flow, vars );

    }


    /**
     * 是否可以取回任务
     */
    public boolean isBack(HistoricTaskInstance hisTask) {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery ( )
                .processInstanceId ( hisTask.getProcessInstanceId ( ) ).singleResult ( );
        if ( pi != null ) {
            if ( pi.isSuspended ( ) ) {
                return false;
            } else {
                List <Task> currentTasks = taskService.createTaskQuery ( ).processInstanceId ( hisTask.getProcessInstanceId ( ) ).list ( );
                HistoricTaskInstance lastHisTask = historyService.createHistoricTaskInstanceQuery ( ).processInstanceId ( hisTask.getProcessInstanceId ( ) ).finished ( )
                        .includeProcessVariables ( ).orderByHistoricTaskInstanceEndTime ( ).desc ( ).list ( ).get ( 0 );

                if ( currentTasks.size ( ) != 0 && currentTasks.get ( 0 ).getClaimTime ( ) != null ) {//用户已签收
                    return false;
                }
                return hisTask.getId ( ).equals ( lastHisTask.getId ( ) );
            }

        } else {
            return false;
        }
    }

    /*
     * 驳回任务
     */
    public String backTask(String backTaskDefKey, String taskId, TaskComment comment) {
        Task task = taskService.createTaskQuery ( ).taskId ( taskId ).singleResult ( );
        if ( StrUtil.isBlank ( task.getAssignee ( ) ) ) {
            taskService.claim ( taskId, UserUtils.getCurrentUserDTO ( ).getId ( ) );
        }

        // 退回发起者处理,退回到发起者,默认设置任务执行人为发起者
        ActivityInstance targetRealActivityInstance = runtimeService.createActivityInstanceQuery ( ).processInstanceId ( task.getProcessInstanceId ( ) ).activityId ( backTaskDefKey ).list ( ).get ( 0 );
        if ( targetRealActivityInstance.getActivityType ( ).equals ( BpmnXMLConstants.ELEMENT_EVENT_START ) ) {
            flowProcessService.stopProcessInstanceById ( task.getProcessInstanceId ( ), ProcessStatus.REJECT, comment.getMessage ( ) );
        } else {
            taskService.addComment ( taskId, task.getProcessInstanceId ( ), comment.getCommentType ( ), comment.getFullMessage ( ) );
            managementService.executeCommand ( new BackUserTaskCmd ( runtimeService,
                    taskId, backTaskDefKey ) );
        }

        return task.getProcessInstanceId ( );
    }

    /**
     * 获取会签节点待执行任务
     */
    public List<Task> getMultiInstanceList (String taskId) {
        Task taskEntity = taskService.createTaskQuery ( ).taskId ( taskId ).singleResult ( );
        String processInstanceId = taskEntity.getProcessInstanceId ( );
        List<Task> tasks = taskService.createTaskQuery ().processInstanceId ( processInstanceId ).taskDefinitionKey ( taskEntity.getTaskDefinitionKey () ).active ().list ();
        return tasks;
    }

    /**
     * 获取可驳回节点
     *
     * @param taskId
     * @return
     */
    public List <Flow> getBackNodes(String taskId) {
        Task taskEntity = taskService.createTaskQuery ( ).taskId ( taskId ).singleResult ( );
        String processInstanceId = taskEntity.getProcessInstanceId ( );
        String currActId = taskEntity.getTaskDefinitionKey ( );
        String processDefinitionId = taskEntity.getProcessDefinitionId ( );
        Process process = repositoryService.getBpmnModel ( processDefinitionId ).getMainProcess ( );
        FlowNode currentFlowElement = (FlowNode) process.getFlowElement ( currActId, true );
        List <ActivityInstance> activityInstances =
                runtimeService.createActivityInstanceQuery ( ).processInstanceId ( processInstanceId ).finished ( ).orderByActivityInstanceStartTime ( ).asc ( ).list ( );
        List <String> activityIds =
                activityInstances.stream ( ).filter ( activity -> activity.getActivityType ( ).equals ( BpmnXMLConstants.ELEMENT_TASK_USER ) || activity.getActivityType ( ).equals ( BpmnXMLConstants.ELEMENT_EVENT_START ) ).filter ( activity -> !activity.getActivityId ( ).equals ( currActId ) ).map ( ActivityInstance::getActivityId ).distinct ( ).collect ( Collectors.toList ( ) );
        List <Flow> result = new ArrayList <> ( );
        for (String activityId : activityIds) {
            FlowNode toBackFlowElement = (FlowNode) process.getFlowElement ( activityId, true );
            if ( FlowableUtils.isReachable ( process, toBackFlowElement, currentFlowElement ) ) {
                Flow vo = new Flow ( );
                vo.setTaskDefKey ( activityId );
                vo.setTaskName ( toBackFlowElement.getName ( ) );
                vo.setTaskId ( activityId );
                result.add ( vo );
            }
        }
        return result;
    }

    /**
     * 校验任务是否可以加签
     * @param userId 当前用户 ID
     * @return 当前任务
     */
    private TaskEntityImpl validateTaskCanCreateSign(String userId, AddMultiTaskVO addMultiTask) {
        TaskEntityImpl taskEntity = (TaskEntityImpl) validateTask ( userId, addMultiTask.getTaskId ( ) );

        // 同一个 key 的任务，审批人不重复
        List <Task> taskList = taskService.createTaskQuery ( ).processInstanceId ( taskEntity.getProcessInstanceId ( ) )
                .taskDefinitionKey ( taskEntity.getTaskDefinitionKey ( ) ).list ( );
        List <String> currentAssigneeList =// 需要考虑 owner 的情况，因为向后加签时，它暂时没 assignee 而是 owner
                taskList.stream ( ).filter ( Objects::nonNull ).flatMap ( task ->
                        Stream.of ( task.getAssignee ( ), task.getOwner ( ) ) ).filter ( Objects::nonNull ).collect ( Collectors.toList ( ) );
        if ( CollUtil.containsAny ( currentAssigneeList, addMultiTask.getAssigneeIds ( ) ) ) {
            throw new RuntimeException ( String.format ( ErrorConstant.TASK_SIGN_CREATE_USER_REPEAT, String.join ( ",", addMultiTask.getAssigneeNames ( ) ) ) );
        }
        return taskEntity;
    }

    /**
     * 校验任务是否存在，并且是否是分配给自己的任务
     *
     * @param userId 用户 id
     * @param taskId task id
     */
    private Task validateTask(String userId, String taskId) {
        Task task = validateTaskExist ( taskId );
        if ( !Objects.equals ( userId, task.getAssignee ( ) ) ) {
            throw new RuntimeException ( ErrorConstant.TASK_OPERATE_FAIL_ASSIGN_NOT_SELF );
        }
        return task;
    }

    private Task validateTaskExist(String id) {
        Task task = taskService.createTaskQuery ( ).taskId ( id ).includeTaskLocalVariables ( ).singleResult ( );
        if ( task == null ) {
            throw new RuntimeException ( ErrorConstant.TASK_NOT_EXISTS );
        }
        return task;
    }


    /**
     * 会签任务加签
     *
     * @param addMultiTask 参数
     */
    public boolean addMultiInstanceExecution(AddMultiTaskVO addMultiTask) {

        String userId = UserUtils.getCurrentUserDTO ( ).getId ( );
        validateTaskCanCreateSign ( userId, addMultiTask );
        if ( CollUtil.isEmpty ( addMultiTask.getAssigneeIds () ) ) {
            throw new RuntimeException ( ErrorConstant.TASK_SIGN_CREATE_USER_NOT_EXIST );
        }
        Task task = taskService.createTaskQuery ( ).taskId ( addMultiTask.getTaskId ( ) ).singleResult ( );
        String taskDefinitionKey = task.getTaskDefinitionKey ( );
        String processInstanceId = task.getProcessInstanceId ( );
        String processDefinitionId = task.getProcessDefinitionId ( );

        try {
            MultiInstanceVO multiInstanceVo = FlowableUtils.isMultiInstance ( processDefinitionId, taskDefinitionKey );
            if ( multiInstanceVo == null ) {
                throw new RuntimeException ( ErrorConstant.MESSAGE_CURRENT_TASK_IS_NOT_MULTI );
            }
            if ( multiInstanceVo.getType ( ) instanceof ParallelMultiInstanceBehavior ) {
                for (String assignee : addMultiTask.getAssigneeIds ( )) {
                    runtimeService.addMultiInstanceExecution ( taskDefinitionKey, processInstanceId, Collections.singletonMap ( multiInstanceVo.getAssignee ( ), assignee ) );
                }
            } else if ( multiInstanceVo.getType ( ) instanceof SequentialMultiInstanceBehavior ) {
                AddSequenceMultiInstanceCmd addSequenceMultiInstanceCmd = new AddSequenceMultiInstanceCmd ( task.getExecutionId ( ), multiInstanceVo.getAssigneeList ( ), addMultiTask.getAssigneeIds ( ) );
                managementService.executeCommand ( addSequenceMultiInstanceCmd );
            }
            List <String> assigneeNames = addMultiTask.getAssigneeIds ( ).stream( ).map ( uId ->{
                UserDTO user = UserUtils.get ( uId );
                return user.getName ( );
            }).collect ( Collectors.toList ( ) );
            String username = UserUtils.getCurrentUserDTO ( ).getName ( );
            TaskEntity newTask = FlowableUtils.createNewTask ( task );
            taskService.addComment ( newTask.getId ( ), processInstanceId, ActionType.ADD_MULTI_INSTANCE.getType ( ), username + "加签【" + String.join ( "|", assigneeNames ) + "】" );
            taskService.complete ( newTask.getId ( ) );
            return true;
        } catch (Exception e) {
            e.printStackTrace ( );
            throw new RuntimeException ( e.getMessage ( ) );
        }
    }

    /**
     * 会签任务减签
     *
     * @param deleteMultiTasks 参数
     */
    public boolean deleteMultiInstanceExecution(List<DeleteMultiTaskVO> deleteMultiTasks) {
        Task task = taskService.createTaskQuery ( ).taskId ( deleteMultiTasks.get ( 0 ).getId ( ) ).singleResult ( );
        String taskDefinitionKey = task.getTaskDefinitionKey ( );
        String processInstanceId = task.getProcessInstanceId ( );
        String processDefinitionId = task.getProcessDefinitionId ( );
        List<String> assignees = deleteMultiTasks.stream ().map ( deleteMultiTask -> deleteMultiTask.getAssignee () ).toList ();
        if ( CollUtil.isEmpty ( assignees ) ) {
            throw new RuntimeException ( ErrorConstant.TASK_SIGN_DELETE_USER_NOT_EXIST );
        }

        if ( CollUtil.contains (assignees, UserUtils.getCurrentUserDTO ().getId () ) ) {
            throw new RuntimeException ( String.format ( ErrorConstant.TASK_SIGN_DELETE_USER_REPEAT, String.join ( ",", UserUtils.getCurrentUserDTO ().getName () ) ) );
        }
        try {
            MultiInstanceVO multiInstanceVo = FlowableUtils.isMultiInstance ( processDefinitionId, taskDefinitionKey );
            if ( multiInstanceVo == null ) {
                throw new RuntimeException ( ErrorConstant.MESSAGE_CURRENT_TASK_IS_NOT_MULTI );
            }
            if ( multiInstanceVo.getType ( ) instanceof ParallelMultiInstanceBehavior ) {
                for (DeleteMultiTaskVO deleteMultiTask : deleteMultiTasks) {
                    runtimeService.deleteMultiInstanceExecution ( deleteMultiTask.getExecutionId (), false );
                }
                for (DeleteMultiTaskVO deleteMultiTask : deleteMultiTasks) {
                    historyService.deleteHistoricTaskInstance ( deleteMultiTask.getId () );
                }
            } else if ( multiInstanceVo.getType ( ) instanceof SequentialMultiInstanceBehavior ) {
                List<String> assigneeIds = deleteMultiTasks.stream ().map ( deleteMultiTask -> deleteMultiTask.getAssignee () ).toList ();
                DeleteSequenceMultiInstanceCmd deleteSequenceMultiInstanceCmd = new DeleteSequenceMultiInstanceCmd ( task.getAssignee ( ), task.getExecutionId ( ), multiInstanceVo.getAssigneeList ( ), assigneeIds );
                managementService.executeCommand ( deleteSequenceMultiInstanceCmd );
            }
            List <String> assigneeNames = deleteMultiTasks.stream ().map ( deleteMultiTask ->
                 deleteMultiTask.getAssigneeName ()
             ).toList ();
            String username = UserUtils.getCurrentUserDTO ( ).getName ( );
            TaskEntity newTask = FlowableUtils.createNewTask ( task );
            taskService.addComment ( newTask.getId ( ), processInstanceId, ActionType.DEL_MULTI_INSTANCE.getType ( ), username + "减签【" + String.join ( "|", assigneeNames ) + "】" );
            taskService.complete ( newTask.getId ( ) );
            return true;
        } catch (Exception e) {
            e.printStackTrace ( );
            throw new RuntimeException ( e.getMessage ( ) );
        }
    }

}
