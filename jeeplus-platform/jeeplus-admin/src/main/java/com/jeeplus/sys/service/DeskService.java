/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.sys.domain.Desk;
import com.jeeplus.sys.mapper.DeskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 控制台Service
 *
 * @author 刘高峰
 * @version 2022-07-19
 */
@Service
@Transactional
public class DeskService extends ServiceImpl <DeskMapper, Desk> {

}
