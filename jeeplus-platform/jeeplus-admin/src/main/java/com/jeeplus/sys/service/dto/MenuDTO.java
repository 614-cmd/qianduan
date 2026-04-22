/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * 菜单Entity
 *
 * @author jeeplus
 * @version 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDTO extends TreeDTO <MenuDTO> {

    private static final long serialVersionUID = 1L;

    /**
     * 链接
     */
    @Size(min = 0, max = 2000)
    private String href;

    /**
     * 目标（ iframe 1）
     */
    @Size(min = 0, max = 20)
    private String target;

    /**
     * 图标
     */
    @Size(min = 0, max = 100)
    private String icon;

    /**
     * 是否在菜单中显示（1：显示；0：不显示）
     */
    @Size(min = 1, max = 1)
    private String isShow;

    /**
     * 按钮类型
     */
    private String type;

    /**
     * 权限标识
     */
    @Size(min = 0, max = 200)
    private String permission;

    /**
     * 数据权限
     */
    private List <DataRuleDTO> dataRuleDTOList;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 是否固定在标签页
     */
    private String affix;

    /**
     * 隐藏面包屑
     */
    private String hiddenBreadcrumb;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 排序
     *
     * @param list
     * @param sourceList
     * @param parentId
     */
    @JsonIgnore
    public static void sortList(List <MenuDTO> list, List <MenuDTO> sourceList, String parentId) {
        for (int i = 0; i < sourceList.size ( ); i++) {
            MenuDTO e = sourceList.get ( i );
            if ( e.getParent ( ) != null && parentId.equals ( e.getParent ( ).getId ( ) ) ) {
                list.add ( e );
            }
        }
    }


}
