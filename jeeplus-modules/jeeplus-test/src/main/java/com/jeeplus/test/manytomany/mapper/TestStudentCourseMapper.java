/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.test.manytomany.service.dto.TestStudentCourseDTO;
import com.jeeplus.test.manytomany.domain.TestStudentCourse;

/**
 * 学生课程记录MAPPER接口
 * @author 刘高峰
 * @version 2024-09-01
 */
public interface TestStudentCourseMapper extends BaseMapper<TestStudentCourse> {

    /**
     * 根据id获取学生课程记录
     * @param id
     * @return
     */
    TestStudentCourseDTO findById(String id);

    /**
     * 获取学生课程记录列表
     *
     * @param queryWrapper
     * @return
     */
    IPage <TestStudentCourseDTO> findList(Page <TestStudentCourseDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
