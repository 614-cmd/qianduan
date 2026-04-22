/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图表看板Entity
 *
 * @author 刘高峰
 * @version 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("plugin_echarts_dashboard")
public class Dashboard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
    /**
     * 控制台内容
     */
    private String grid;
    /**
     * 备注
     */
    private String remarks;

}
