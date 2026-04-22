package com.jeeplus.echarts.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.jeeplus.database.datamodel.domain.DataMeta;
import com.jeeplus.database.datamodel.service.DataMetaService;
import com.jeeplus.database.datamodel.service.DataSetService;
import com.jeeplus.echarts.domain.vo.ChartDataVO;
import com.jeeplus.sys.constant.CommonConstants;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

public class OptionUtil {

    public static ChartDataVO getChartData(String dataSetId, String xnames, String ynames, HttpServletRequest request) throws Exception {
        DataSetService dataSetService = SpringUtil.getBean ( DataSetService.class );
        DataMetaService dataMetaService = SpringUtil.getBean ( DataMetaService.class );
        List <DataMeta> dataMetas = dataMetaService.lambdaQuery ( ).eq ( DataMeta::getIsNeed, CommonConstants.TRUE ).eq ( DataMeta::getDataSetId, dataSetId ).list ( );
        List <Map <String, Object>> valueList = dataSetService.queryForListById ( dataSetId, request );
        ChartDataVO chartData = new ChartDataVO ( );
        List <String> columns = Lists.newArrayList ( );
        columns.addAll ( Arrays.asList ( xnames.split ( "," ) ) );
        columns.addAll ( Arrays.asList ( ynames.split ( "," ) ) );
        Map labelMap = new HashMap ( );
        for (String name : columns) {
            String label = "";
            for (DataMeta dataMeta : dataMetas) {
                if ( name.equals ( dataMeta.getName ( ) ) ) {
                    label = dataMeta.getLabel ( );
                }
            }
            label = StrUtil.isNotEmpty ( label ) ? label : name;
            chartData.getColumns ( ).add ( label );
            labelMap.put ( name, label );
        }
        for (Map valueMap : valueList) {
            Map nMap = new LinkedHashMap ( );
            for (String name : columns) {
                nMap.put ( labelMap.get ( name ), valueMap.get ( name ) );
            }
            chartData.getRows ( ).add ( nMap );
        }

        return chartData;
    }

}
