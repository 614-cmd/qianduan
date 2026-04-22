/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.core.service.TreeService;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.constant.enums.OfficeTypeEnum;
import com.jeeplus.sys.domain.Office;
import com.jeeplus.sys.mapper.OfficeMapper;
import com.jeeplus.sys.service.dto.OfficeDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 机构Service
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Service
@Transactional
public class OfficeService extends TreeService <OfficeMapper, Office> {


    /**
     * 获取列表
     *
     * @return
     */
    public List <OfficeDTO> findList(String parentId) {
        QueryWrapper queryWrapper = new QueryWrapper ( );
        queryWrapper.like ( StringUtils.isNotBlank ( parentId ), "a.parent_ids", "," + parentId + "," );
        queryWrapper.eq ( "a.del_flag", 0 );
        queryWrapper.orderByAsc ( "a.sort" );
        return baseMapper.findList ( queryWrapper );
    }

    public List <OfficeDTO> getRootTree(String parentId, List <OfficeDTO> list, String extId, String type, String showAll) {
        List <OfficeDTO> offices = Lists.newArrayList ( );
        parentId = StrUtil.isEmpty ( parentId ) ? OfficeDTO.getRootId ( ) : parentId;
        for (OfficeDTO root : list) {
            if ( parentId.equals ( root.getParentId ( ) ) ) {
                if ( this.isUseAble ( extId, type, root, showAll ) ) {
                    // 不是被排除节点的子节点
                    List <OfficeDTO> officeList = formatListToTree ( root, list, extId, type, showAll );
                    offices.addAll ( officeList );
                }
            }
        }
        return offices;
    }


    public List <OfficeDTO> formatListToTree(OfficeDTO root, List <OfficeDTO> allList, String extId, String type, String showAll) {
        String rootId = root.getId ( );

        // type为2时，是选择部门，因此禁用type为1的公司节点
        root.setDisabled ( OfficeTypeEnum.OFFICE.getValue ( ).equals ( type ) && root.getType ( ).equals ( OfficeTypeEnum.COMPANY.getValue ( ) ) );
        // 最终的树形态
        List <OfficeDTO> trees = Lists.newArrayList ( );

        // 把需要构造树的所有列表, 根据以父id作为key, 整理为列表
        Map <String, List <OfficeDTO>> treeMap = Maps.newHashMap ( );
        for (OfficeDTO entity : allList) {
            List <OfficeDTO> offices = treeMap.get ( entity.getParent ( ).getId ( ) );
            if ( offices == null ) {
                offices = Lists.newLinkedList ( );
            }

            if ( this.isUseAble ( extId, type, root, showAll ) ) {

                // 如果是查找公司，只返回公司数据，如果是查询部门，则一起返回公司部门数据
                if ( OfficeTypeEnum.COMPANY.getValue ( ).equals ( type ) && entity.getType ( ).equals ( type )
                        || OfficeTypeEnum.OFFICE.getValue ( ).equals ( type )
                        || StrUtil.isBlank ( type )
                ) {

                    // type为2时，是选择部门，因此禁用type为1的公司节点
                    entity.setDisabled ( OfficeTypeEnum.OFFICE.getValue ( ).equals ( type ) && entity.getType ( ).equals ( OfficeTypeEnum.COMPANY.getValue ( ) ) );
                    offices.add ( entity );
                }

                treeMap.put ( entity.getParent ( ).getId ( ), offices );
            }
        }

        // 开始递归格式化
        List <OfficeDTO> children = treeMap.get ( rootId );
        if ( children != null ) {
            for (OfficeDTO parent : children) {
                formatFillChildren ( parent, treeMap );
                trees.add ( parent );
            }
        }

        root.setChildren ( trees );
        return Lists.newArrayList ( root );
    }

    /**
     * 从treeMap中取出子节点填入parent, 并递归此操作
     **/
    private void formatFillChildren(OfficeDTO parent, Map <String, List <OfficeDTO>> treeMap) {

        List <OfficeDTO> children = treeMap.get ( parent.getId ( ) );
        parent.setChildren ( children );
        if ( children != null && !children.isEmpty ( ) ) {
            for (OfficeDTO child : children) {
                formatFillChildren ( child, treeMap );
            }
        }
    }

    private boolean isUseAble(String extId, String type, OfficeDTO dto, String showAll) {
        return (StringUtils.isBlank ( extId ) || (extId != null && !extId.equals ( dto.getId ( ) ) && dto.getParentIds ( ).indexOf ( "," + extId + "," ) == -1))
                && (type == null || (type != null && (!type.equals ( OfficeTypeEnum.COMPANY.getValue ( ) ) || type.equals ( dto.getType ( ) ))))
                && (CommonConstants.YES.equals ( showAll ) || CommonConstants.YES.equals ( dto.getUseable ( ) ));
    }


}
