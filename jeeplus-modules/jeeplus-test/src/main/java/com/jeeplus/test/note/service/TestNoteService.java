/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.note.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeeplus.test.note.domain.TestNote;
import com.jeeplus.test.note.mapper.TestNoteMapper;

/**
 * 富文本Service
 * @author 刘高峰
 * @version 2024-09-01
 */
@Service
@Transactional
public class TestNoteService extends ServiceImpl<TestNoteMapper, TestNote> {

}
