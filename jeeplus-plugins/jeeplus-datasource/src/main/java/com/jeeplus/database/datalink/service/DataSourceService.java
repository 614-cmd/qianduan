/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datalink.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.database.datalink.domain.DataSource;
import com.jeeplus.database.datalink.mapper.DataSourceMapper;
import com.jeeplus.sys.constant.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据库连接Service
 *
 * @author 刘高峰
 * @version 2021-08-06
 */
@Service
@Transactional
public class DataSourceService extends ServiceImpl <DataSourceMapper, DataSource> {

    /**
     * 主数据源
     */
    @Value("${spring.datasource.dynamic.primary}")
    public String primary;

    /**
     * 根据id获取数据源
     *
     * @param id
     * @return
     */
    public DataSource getById(String id) {
        if ( StringUtils.isBlank ( id ) || primary.equals ( id ) ) {
            return createPrimaryDataSource ( );
        } else {
            return super.getById ( id );
        }

    }


    /**
     * 根据英文名获取数据源
     *
     * @param enName
     * @return
     */
    public DataSource getByEnName(String enName) {

        if ( StrUtil.isBlank ( enName ) || primary.equals ( enName ) ) {
            return createPrimaryDataSource ( );
        } else {
            return super.lambdaQuery ( ).eq ( DataSource::isEnable, CommonConstants.TRUE ).eq ( DataSource::getEnName, enName ).one ( );
        }

    }

    private DataSource createPrimaryDataSource() {
        DataSource dataSource = new DataSource ( );
        dataSource.setId ( primary );
        dataSource.setEnName ( primary );
        dataSource.setName ( "本地数据库" );
        return dataSource;
    }

}
