package com.jeeplus.flowable.vo;

import lombok.Data;

/**
 * 减签参数请求
 */
@Data
public class DeleteMultiTaskVO {

    /**
     * 任务ID
     */
    private String id;

    /**
     * 执行id
     */
    private String executionId;

    /**
     * 人员id
     */
    private String assigneeName;

    /**
     * 人员名称
     */
    private String assignee;
}
