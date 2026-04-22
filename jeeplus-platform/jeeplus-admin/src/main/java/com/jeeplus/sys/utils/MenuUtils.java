package com.jeeplus.sys.utils;

import com.google.common.collect.Lists;
import com.jeeplus.sys.domain.vo.MenuVO;
import com.jeeplus.sys.service.dto.MenuDTO;
import com.jeeplus.sys.service.mapstruct.MenuWrapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取用户可见的菜单
 */
public class MenuUtils {


    public static List <MenuVO> getMenuVOs() {
        List <MenuDTO> menuDTOS = getMenus ( );
        List <MenuVO> menuVOS = menuDTOS.stream ( ).map ( MenuWrapper.INSTANCE::toVO ).collect ( Collectors.toList ( ) );
        return menuVOS;
    }

    private static List <MenuDTO> getMenus() {
        MenuDTO menuDTO = UserUtils.getTopMenuDTO ( );
        getChildOfTree ( menuDTO, 0, UserUtils.getMenuDTOList ( ) );
        return menuDTO.getChildren ( );
    }

    private static MenuDTO getChildOfTree(MenuDTO menuItem, int level, List <MenuDTO> menuList) {

        menuItem.setChildren ( Lists.newArrayList ( ) );
        for (MenuDTO child : menuList) {
            if ( child.getParentId ( ).equals ( menuItem.getId ( ) ) ) {
                menuItem.getChildren ( ).add ( getChildOfTree ( child, level + 1, menuList ) );
            }
        }

        return menuItem;
    }


}
