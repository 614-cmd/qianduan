package com.jeeplus.sys.domain.vo;

import lombok.Data;

@Data
public class MetaVO {

    /**
     * 主键id
     */
    private String id;
    /**
     * 图标
     */
    private String icon;
    /**
     * 类型
     */
    private String type;
    /**
     * 目标 是否是iframe
     */
    private String target;
    /**
     * 标题
     */
    private String title;
    /**
     * 是否固定
     */
    private String affix;
    /**
     * 是否显示 0/1
     */
    private String isShow;

    /**
     * 隐藏面包屑
     */
    private String hiddenBreadcrumb;

}
