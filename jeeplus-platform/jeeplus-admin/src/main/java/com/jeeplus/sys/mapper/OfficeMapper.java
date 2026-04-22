/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jeeplus.core.domain.TreeMapper;
import com.jeeplus.sys.domain.Office;
import com.jeeplus.sys.service.dto.OfficeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构MAPPER接口
 *
 * @author jeeplus
 * @version 2021-05-16
 */
public interface OfficeMapper extends TreeMapper <Office> {

    /**
     * 机构列表
     *
     * @return
     */
    List <OfficeDTO> findList(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
