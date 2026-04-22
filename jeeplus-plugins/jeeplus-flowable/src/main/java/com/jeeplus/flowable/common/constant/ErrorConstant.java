package com.jeeplus.flowable.common.constant;

public interface ErrorConstant {


    String MESSAGE_CURRENT_TASK_IS_NOT_MULTI = "当前节点不是会签节点！";

    String TASK_SIGN_CREATE_USER_NOT_EXIST = "任务加签：选择的用户不存在";

    String TASK_SIGN_DELETE_USER_NOT_EXIST = "任务减签：选择的用户不存在";

    String TASK_SIGN_CREATE_USER_REPEAT = "任务加签失败，加签人与现有审批人[%s]重复";

    String TASK_SIGN_DELETE_USER_REPEAT = "任务减签失败，减签人不能包含自己[%s]";

    String TASK_OPERATE_FAIL_ASSIGN_NOT_SELF = "操作失败，原因：该任务的审批人不是你";

    String TASK_NOT_EXISTS = "流程任务不存在";

}
