package com.jeeplus.sys.service.dto;

import lombok.Data;

@Data
public class DragMenuDTO {
    /**
     * 移动的菜单节点
     */
    private MenuDTO draggingNode;
    /**
     * 移动到的目标节点
     */
    private MenuDTO dropNode;
    /**
     * 移动的类型 before、after、inner
     */
    private String dropType;


}
