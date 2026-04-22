package com.jeeplus.sys.domain.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class MenuVO {

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单路径
     */
    private String path;
    /**
     * meta
     */
    private MetaVO meta;
    /**
     * 子菜单
     */
    private List <MenuVO> children = Lists.newArrayList ( );
}
