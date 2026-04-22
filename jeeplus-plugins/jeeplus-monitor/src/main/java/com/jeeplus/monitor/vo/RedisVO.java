package com.jeeplus.monitor.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class RedisVO {
    private String id;
    /**
     * 父级编号
     */
    private String parentId;

    /**
     * 所有父级编号
     */
    private String parentIds;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 类型
     */

    private String type;

    /**
     * 子元素集合
     */

    private List <RedisVO> children = Lists.newArrayList ( );
}
