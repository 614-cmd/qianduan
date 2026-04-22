/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.echarts.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.echarts.domain.Dashboard;
import com.jeeplus.echarts.mapper.DashboardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图表看板Service
 *
 * @author 刘高峰
 * @version 2022-07-20
 */
@Service
@Transactional
public class DashboardService extends ServiceImpl <DashboardMapper, Dashboard> {

}
