/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeeplus.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 控制台Entity
 *
 * @author 刘高峰
 * @version 2022-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_desk")
public class Desk extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 控制台内容
     */
    private String grid;

}
