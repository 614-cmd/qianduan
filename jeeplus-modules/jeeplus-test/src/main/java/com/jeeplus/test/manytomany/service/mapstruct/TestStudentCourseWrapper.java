/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.test.manytomany.service.mapstruct;


import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.test.manytomany.service.dto.TestStudentCourseDTO;
import com.jeeplus.test.manytomany.domain.TestStudentCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestStudentCourseWrapper
 * @author 刘高峰
 * @version 2024-09-01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestStudentCourseWrapper extends EntityWrapper<TestStudentCourseDTO, TestStudentCourse> {

    TestStudentCourseWrapper INSTANCE = Mappers.getMapper(TestStudentCourseWrapper.class);
     @Mappings({
            @Mapping(source = "student.id", target = "studentId"),
            @Mapping(source = "course.id", target = "courseId"),
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping (source = "updateBy.id", target = "updateById")})
    TestStudentCourse toEntity(TestStudentCourseDTO dto);


    @Mappings({
            @Mapping(source = "studentId", target = "student.id"),
            @Mapping(source = "courseId", target = "course.id"),
            @Mapping (source = "createById", target = "createBy.id"),
            @Mapping (source = "updateById", target = "updateBy.id")})
    TestStudentCourseDTO toDTO(TestStudentCourse entity);
}

