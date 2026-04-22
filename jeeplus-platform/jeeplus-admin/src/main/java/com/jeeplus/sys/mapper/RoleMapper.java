/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.sys.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色MAPPER接口
 *
 * @author jeeplus
 * @version 2021-09-05
 */
public interface RoleMapper extends BaseMapper <Role> {

    /**
     * 查询关联的菜单Id
     */
    List <String> getMenuIdListByRoleId(@Param("roleId") String roleId);

    /**
     * 查询关联的权限Id
     */
    List <String> getDataRuleIdListByRoleId(@Param("roleId") String roleId);

    /**
     * 查询角色的所有无下属菜单ID
     *
     * @param id
     * @return
     */
    List <String> queryAllNotChildrenMenuId(@Param("id") String id);

    /**
     * 维护角色与菜单权限关系
     *
     * @param id
     * @return
     */
    int deleteRoleMenu(@Param("id") String id);

    int insertRoleMenu(@Param("roleId") String roleId, @Param("menuId") String menuId);

    /**
     * 维护角色与数据权限关系
     *
     * @param id
     * @return
     */
    int deleteRoleDataRule(@Param("id") String id);

    int insertRoleDataRule(@Param("roleId") String roleId, @Param("dataRuleId") String dataRuleId);

}
