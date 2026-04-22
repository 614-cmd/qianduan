/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.service.dto;

import com.jeeplus.core.query.Query;
import com.jeeplus.core.query.QueryType;
import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图表看板DTO
 *
 * @author 刘高峰
 * @version 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DashboardDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @Query(type = QueryType.LIKE)
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
