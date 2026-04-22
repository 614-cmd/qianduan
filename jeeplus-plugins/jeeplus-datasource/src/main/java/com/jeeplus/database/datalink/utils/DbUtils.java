/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datalink.utils;

import com.jeeplus.sys.utils.DictUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * 数据库连接Service
 *
 * @author 刘高峰
 * @version 2021-08-06
 */
public class DbUtils {

    /**
     * 转换为JDBC连接
     *
     * @param type
     * @param host
     * @param port
     * @param dbName
     * @return
     */
    public static String toUrl(String type, String host, String port, String dbName) {
        String template = DictUtils.getDictValue ( type, "db_jdbc_url", "mysql" );
        if ( template != null ) {
            template = HtmlUtils.htmlUnescape ( template );
            return template.replace ( "${host}", host ).replace ( "${port}", port ).replace ( "${db}", dbName );
        }
        return null;
    }

    /**
     * 获取JDBC驱动
     *
     * @param type
     * @return
     */
    public static String getDriver(String type) {

        return DictUtils.getDictValue ( type, "db_driver", "mysql" );

    }


}
