/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.service;

import com.jeeplus.extension.domain.FormRef;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.extension.mapper.FormRefMapper;

/**
 * 工作流数据Service
 * @author 刘高峰
 * @version 2024-06-25
 */
@Service
@Transactional
public class FormRefService extends ServiceImpl<FormRefMapper, FormRef> {

}
