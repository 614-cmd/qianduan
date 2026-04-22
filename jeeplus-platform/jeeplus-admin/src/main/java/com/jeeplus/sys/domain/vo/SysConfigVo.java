package com.jeeplus.sys.domain.vo;

import lombok.Data;

@Data
public class SysConfigVo {
    /*
     * 默认主题模式
     */
    private String defaultTheme;
    /*
     * 默认主题色
     */
    private String defaultColor;
    /**
     * 默认布局
     */
    private String defaultLayout;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品logo
     */
    private String logo;

    /**
     * 首页配置
     */
    private String homeUrl;

}
