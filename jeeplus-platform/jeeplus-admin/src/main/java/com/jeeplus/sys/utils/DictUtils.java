/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.sys.service.DictTypeService;
import com.jeeplus.sys.service.dto.DictTypeDTO;
import com.jeeplus.sys.service.dto.DictValueDTO;

import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 *
 * @author jeeplus
 * @version 2021-5-29
 */
public class DictUtils {


    public static String getDictLabel(String value, String type, String defaultLabel) {
        if ( StrUtil.isNotBlank ( type ) && StrUtil.isNotBlank ( value ) ) {
            for (DictValueDTO dictValueDTO : getDictDTOList ( type )) {
                if ( value.equals ( dictValueDTO.getValue ( ) ) ) {
                    return dictValueDTO.getLabel ( );
                }
            }
        }
        return defaultLabel;
    }


    public static String getDictValue(String label, String type, String defaultLabel) {
        if ( StrUtil.isNotBlank ( type ) && StrUtil.isNotBlank ( label ) ) {
            for (DictValueDTO dictValue : getDictDTOList ( type )) {
                if ( label.equals ( dictValue.getLabel ( ) ) ) {
                    return dictValue.getValue ( );
                }
            }
        }
        return defaultLabel;
    }

    public static List <DictValueDTO> getDictDTOList(String type) {
        @SuppressWarnings("unchecked")
        Map <String, List <DictValueDTO>> dictMap = getDictMap ( );
        List <DictValueDTO> dictList = dictMap.get ( type );
        if ( dictList == null ) {
            dictList = Lists.newArrayList ( );
        }
        return dictList;
    }

    public static Map <String, List <DictValueDTO>> getDictMap() {
        @SuppressWarnings("unchecked")
        Map <String, List <DictValueDTO>> dictMap = Maps.newHashMap ( );
        List <DictTypeDTO> dict = SpringUtil.getBean ( DictTypeService.class ).getDict ( );
        for (DictTypeDTO dictTypeDTO : dict) {
            dictMap.put ( dictTypeDTO.getType ( ), dictTypeDTO.getDictValueDTOList ( ) );
        }
        return dictMap;
    }

}
