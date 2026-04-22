package com.jeeplus.core.dto;

import lombok.Data;

@Data
public class DragNode<T> {

    /**
     * 移动的菜单节点
     */
    private T draggingNode;
    /**
     * 移动到的目标节点
     */
    private T dropNode;
    /**
     * 移动的类型 before、after、inner
     */
    private String dropType;

}
