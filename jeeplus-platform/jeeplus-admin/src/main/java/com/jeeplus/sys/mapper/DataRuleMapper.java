/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeeplus.sys.domain.DataRule;
import com.jeeplus.sys.service.dto.DataRuleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据权限MAPPER接口
 *
 * @author lgf
 * @version 2021-04-02
 */
public interface DataRuleMapper extends BaseMapper <DataRule> {

    void deleteRoleDataRule(@Param("dataRuleId") String dataRuleId);

    @InterceptorIgnore
    List <DataRuleDTO> findByUserId(@Param("userId") String userId);
}
