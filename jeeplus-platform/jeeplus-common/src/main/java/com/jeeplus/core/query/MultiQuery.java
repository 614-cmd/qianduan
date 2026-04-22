package com.jeeplus.core.query;


import java.lang.annotation.*;

/**
 * 查询字段注解
 */
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiQuery {
    /**
     * 查询注解
     *
     * @return
     */
    Query[] value() ;


}
