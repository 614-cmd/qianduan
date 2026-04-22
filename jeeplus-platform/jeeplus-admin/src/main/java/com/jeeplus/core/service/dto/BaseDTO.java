package com.jeeplus.core.service.dto;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.jeeplus.sys.service.dto.UserDTO;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体主键
     */
    @ExcelIgnore
    protected String id;

    /**
     * 创建日期
     */
    @ExcelIgnore
    @Hidden
    protected Date createTime;

    /**
     * 创建人
     */
    @ExcelIgnore
    @Hidden
    protected UserDTO createBy;

    /**
     * 更新日期
     */
    @ExcelIgnore
    @Hidden
    protected Date updateTime;

    /**
     * 更新人
     */
    @ExcelIgnore
    @Hidden
    protected UserDTO updateBy;

    /**
     * 逻辑删除标记
     */
    @ExcelIgnore
    @Hidden
    protected Integer delFlag;

    /**
     * 构造函数
     */
    public BaseDTO() {

    }

    /**
     * 构造函数
     *
     * @param id
     */
    public BaseDTO(String id) {
        this.id = id;
    }


}

