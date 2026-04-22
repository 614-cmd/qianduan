/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.config.properties;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 全局配置类
 *
 * @author jeeplus
 * @version 2021-06-25
 */
@Data
@Configuration
public class JeePlusProperties implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 超时时间
     */
    @Value("${jwt.accessToken.expireTime}")
    public long EXPIRE_TIME;

    public static JeePlusProperties newInstance() {

        return SpringUtil.getBean ( JeePlusProperties.class );
    }





}
