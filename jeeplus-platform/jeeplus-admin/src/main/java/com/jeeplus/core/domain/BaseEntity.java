package com.jeeplus.core.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*
 * 数据Entity类
 * @author jeeplus
 * @version 2021-05-16
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体主键
     */
    @ExcelIgnore
    @TableId
    private String id;

    /**
     * 创建日期
     */
    @ExcelIgnore
    @Hidden
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @ExcelIgnore
    @Hidden
    @TableField(fill = FieldFill.INSERT)
    private String createById;

    /**
     * 更新日期
     */
    @ExcelIgnore
    @Hidden
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @ExcelIgnore
    @Hidden
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateById;

    /**
     * 逻辑删除标记
     */
    @ExcelIgnore
    @Hidden
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;

    /**
     * 默认构造函数
     */

    public BaseEntity() {

    }

    /**
     * 构造函数
     */
    public BaseEntity(String id) {
        this.id = id;
    }


}
