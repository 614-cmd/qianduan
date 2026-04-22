//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baomidou.dynamic.datasource;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.destroyer.DataSourceDestroyer;
import com.baomidou.dynamic.datasource.destroyer.DefaultDataSourceDestroyer;
import com.baomidou.dynamic.datasource.ds.AbstractRoutingDataSource;
import com.baomidou.dynamic.datasource.ds.GroupDataSource;
import com.baomidou.dynamic.datasource.ds.ItemDataSource;
import com.baomidou.dynamic.datasource.exception.CannotFindDataSourceException;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.strategy.DynamicDataSourceStrategy;
import com.baomidou.dynamic.datasource.strategy.LoadBalanceDynamicDataSourceStrategy;
import com.baomidou.dynamic.datasource.toolkit.DsStrUtils;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.p6spy.engine.spy.P6DataSource;
import io.seata.rm.datasource.DataSourceProxy;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(DynamicRoutingDataSource.class);
    private static final String UNDERLINE = "_";
    private final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap();
    private final Map<String, GroupDataSource> groupDataSources = new ConcurrentHashMap();
    private final List<DynamicDataSourceProvider> providers;
    private Class<? extends DynamicDataSourceStrategy> strategy = LoadBalanceDynamicDataSourceStrategy.class;
    private String primary = "master";
    private Boolean strict = false;
    private Boolean p6spy = false;
    private Boolean seata = false;
    private Boolean graceDestroy = false;


    public DynamicRoutingDataSource(List<DynamicDataSourceProvider> providers) {
        this.providers = providers;
    }

    protected String getPrimary() {
        return this.primary;
    }

    public DataSource determineDataSource() {
        String dsKey = DynamicDataSourceContextHolder.peek();

        DataSource dataSource = getDataSource ( dsKey );
        // jeeplus 修改 start ----------------------------------
        try {
            DatabaseIdProvider databaseIdProvider = SpringUtil.getBean ( DatabaseIdProvider.class );
            if ( databaseIdProvider != null ) {
                String databaseId = databaseIdProvider.getDatabaseId ( dataSource );
                try {
                    SqlSession sqlSession = SpringUtil.getBean ( SqlSession.class );
                    if ( sqlSession != null ) {
                        sqlSession.getConfiguration ( ).setDatabaseId ( databaseId );
                    }
                } catch (Exception e) {
                }

            }

        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        // jeeplus 修改 end -------------------------------------
        return dataSource;
    }

    private DataSource determinePrimaryDataSource() {
        log.debug("dynamic-datasource switch to the primary datasource");
        DataSource dataSource = this.dataSourceMap.get(this.primary);
        if (dataSource != null) {
            return dataSource;
        } else {
            GroupDataSource groupDataSource = this.groupDataSources.get(this.primary);
            if (groupDataSource != null) {
                return groupDataSource.determineDataSource();
            } else {
                throw new CannotFindDataSourceException("dynamic-datasource can not find primary datasource");
            }
        }
    }

    public Map<String, DataSource> getDataSources() {
        return this.dataSourceMap;
    }

    public Map<String, GroupDataSource> getGroupDataSources() {
        return this.groupDataSources;
    }

    public DataSource getDataSource(String ds) {
        if (DsStrUtils.isEmpty(ds)) {
            return this.determinePrimaryDataSource();
        } else if (!this.groupDataSources.isEmpty() && this.groupDataSources.containsKey(ds)) {
            log.debug("dynamic-datasource switch to the datasource named [{}]", ds);
            return this.groupDataSources.get(ds).determineDataSource();
        } else if (this.dataSourceMap.containsKey(ds)) {
            log.debug("dynamic-datasource switch to the datasource named [{}]", ds);
            return this.dataSourceMap.get(ds);
        } else if (this.strict) {
            throw new CannotFindDataSourceException("dynamic-datasource could not find a datasource named " + ds);
        } else {
            return this.determinePrimaryDataSource();
        }
    }

    public synchronized void addDataSource(String ds, DataSource dataSource) {
        DataSource oldDataSource = this.dataSourceMap.put(ds, dataSource);
        this.addGroupDataSource(ds, dataSource);
        if (oldDataSource != null) {
            this.closeDataSource(ds, oldDataSource, this.graceDestroy);
        }

        log.info("dynamic-datasource - add a datasource named [{}] success", ds);
    }

    private void addGroupDataSource(String ds, DataSource dataSource) {
        if (ds.contains("_")) {
            String group = ds.split("_")[0];
            GroupDataSource groupDataSource = this.groupDataSources.get(group);
            if (groupDataSource == null) {
                try {
                    groupDataSource = new GroupDataSource(group, this.strategy.getDeclaredConstructor().newInstance() );
                    this.groupDataSources.put(group, groupDataSource);
                } catch (Exception var6) {
                    Exception e = var6;
                    throw new RuntimeException("dynamic-datasource - add the datasource named " + ds + " error", e);
                }
            }

            groupDataSource.addDatasource(ds, dataSource);
        }

    }

    public synchronized void removeDataSource(String ds) {
        if (!DsStrUtils.hasText(ds)) {
            throw new RuntimeException("remove parameter could not be empty");
        } else if (this.primary.equals(ds)) {
            throw new RuntimeException("could not remove primary datasource");
        } else {
            if (this.dataSourceMap.containsKey(ds)) {
                DataSource dataSource = this.dataSourceMap.remove(ds);
                this.closeDataSource(ds, dataSource, this.graceDestroy);
                if (ds.contains("_")) {
                    String group = ds.split("_")[0];
                    if (this.groupDataSources.containsKey(group)) {
                        DataSource oldDataSource = this.groupDataSources.get(group).removeDatasource(ds);
                        if (oldDataSource == null) {
                            log.warn("fail for remove datasource from group. dataSource: {} ,group: {}", ds, group);
                        }
                    }
                }

                log.info("dynamic-datasource - remove the database named [{}] success", ds);
            } else {
                log.warn("dynamic-datasource - could not find a database named [{}]", ds);
            }

        }
    }

    public void destroy() {
        log.info("dynamic-datasource start closing ....");
        Iterator var1 = this.dataSourceMap.entrySet().iterator();

        while(var1.hasNext()) {
            Map.Entry<String, DataSource> item = (Map.Entry)var1.next();
            this.closeDataSource( item.getKey(), item.getValue(), false);
        }

        log.info("dynamic-datasource all closed success,bye");
    }

    public void afterPropertiesSet() {
        this.checkEnv();
        Map<String, DataSource> dataSources = new HashMap(16);
        Iterator var2 = this.providers.iterator();

        while(var2.hasNext()) {
            DynamicDataSourceProvider provider = (DynamicDataSourceProvider)var2.next();
            Map<String, DataSource> dsMap = provider.loadDataSources();
            if (dsMap != null) {
                dataSources.putAll(dsMap);
            }
        }

        var2 = dataSources.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, DataSource> dsItem = (Map.Entry)var2.next();
            this.addDataSource( dsItem.getKey(), dsItem.getValue() );
        }

        if (this.groupDataSources.containsKey(this.primary)) {
            log.info("dynamic-datasource initial loaded [{}] datasource,primary group datasource named [{}]", dataSources.size(), this.primary);
        } else if (this.dataSourceMap.containsKey(this.primary)) {
            log.info("dynamic-datasource initial loaded [{}] datasource,primary datasource named [{}]", dataSources.size(), this.primary);
        } else {
            log.warn("dynamic-datasource initial loaded [{}] datasource,Please add your primary datasource or check your configuration", dataSources.size());
        }

    }

    private void checkEnv() {
        Exception e;
        if (this.p6spy) {
            try {
                Class.forName("com.p6spy.engine.spy.P6DataSource");
                log.info("dynamic-datasource detect P6SPY plugin and enabled it");
            } catch (Exception var3) {
                e = var3;
                throw new RuntimeException("dynamic-datasource enabled P6SPY ,however without p6spy dependency", e);
            }
        }

        if (this.seata) {
            try {
                Class.forName("io.seata.rm.datasource.DataSourceProxy");
                log.info("dynamic-datasource detect ALIBABA SEATA and enabled it");
            } catch (Exception var2) {
                e = var2;
                throw new RuntimeException("dynamic-datasource enabled ALIBABA SEATA,however without seata dependency", e);
            }
        }

    }

    private void closeDataSource(String ds, DataSource dataSource, boolean graceDestroy) {
        try {
            DataSource realDataSource = null;
            if (dataSource instanceof ItemDataSource) {
                realDataSource = ((ItemDataSource)dataSource).getRealDataSource();
            } else {
                if (this.seata && dataSource instanceof DataSourceProxy) {
                    DataSourceProxy dataSourceProxy = (DataSourceProxy)dataSource;
                    realDataSource = dataSourceProxy.getTargetDataSource();
                }

                if (this.p6spy && dataSource instanceof P6DataSource) {
                    Field realDataSourceField = P6DataSource.class.getDeclaredField("realDataSource");
                    realDataSourceField.setAccessible(true);
                    realDataSource = (DataSource)realDataSourceField.get(dataSource);
                }
            }

            if (null != realDataSource) {
                DataSourceDestroyer destroyer = new DefaultDataSourceDestroyer();
                if (graceDestroy) {
                    destroyer.asyncDestroy(ds, realDataSource);
                } else {
                    destroyer.destroy(ds, realDataSource);
                }
            }
        } catch (Exception var6) {
            Exception e = var6;
            log.warn("dynamic-datasource closed datasource named [{}] failed", ds, e);
        }

    }

    public void setStrategy(final Class<? extends DynamicDataSourceStrategy> strategy) {
        this.strategy = strategy;
    }

    public void setPrimary(final String primary) {
        this.primary = primary;
    }

    public void setStrict(final Boolean strict) {
        this.strict = strict;
    }

    public void setP6spy(final Boolean p6spy) {
        this.p6spy = p6spy;
    }

    public void setSeata(final Boolean seata) {
        this.seata = seata;
    }

    public void setGraceDestroy(final Boolean graceDestroy) {
        this.graceDestroy = graceDestroy;
    }
}
