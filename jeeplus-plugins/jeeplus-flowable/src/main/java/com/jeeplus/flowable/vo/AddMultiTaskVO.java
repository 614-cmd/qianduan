package com.jeeplus.flowable.vo;

import lombok.Data;

import java.util.List;

/**
 * 加签参数请求
 */
@Data
public class AddMultiTaskVO {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 加签人员id
     */
    private List<String> assigneeIds;

    /**
     * 加签人员名称
     */
    private List<String> assigneeNames;

    /**
     * 加签原因
     */
    private String reason;

}
