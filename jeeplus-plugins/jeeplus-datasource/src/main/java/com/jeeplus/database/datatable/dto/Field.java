package com.jeeplus.database.datatable.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Field {

    @ExcelProperty("序号")
    private int num;

    @ExcelProperty("字段名称")
    private String name;

    @ExcelProperty("数据类型")
    private String type;

    @ExcelProperty("长(精)度")
    private int scale;

    @ExcelProperty("小数位数")
    private int size;

    @ExcelProperty("PK")
    private boolean pk;

    @ExcelProperty("可空")
    private boolean isEmpty;

    @ExcelProperty("缺省值")
    private String defule;

    @ExcelProperty("唯一")
    private boolean sigle;

    @ExcelProperty("字段描述")
    private String comment;
}
