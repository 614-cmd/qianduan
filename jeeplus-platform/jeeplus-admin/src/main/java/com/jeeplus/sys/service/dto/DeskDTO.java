/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;

import com.jeeplus.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 控制台DTO
 *
 * @author 刘高峰
 * @version 2022-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeskDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 控制台内容
     */
    private String grid;

}
