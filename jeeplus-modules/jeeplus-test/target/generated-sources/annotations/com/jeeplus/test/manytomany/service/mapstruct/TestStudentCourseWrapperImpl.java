package com.jeeplus.test.manytomany.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.manytomany.domain.TestStudentCourse;
import com.jeeplus.test.manytomany.service.dto.TestCourseDTO;
import com.jeeplus.test.manytomany.service.dto.TestStudentCourseDTO;
import com.jeeplus.test.manytomany.service.dto.TestStudentDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:43+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class TestStudentCourseWrapperImpl implements TestStudentCourseWrapper {

    @Override
    public List<TestStudentCourse> toEntity(List<TestStudentCourseDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestStudentCourse> list = new ArrayList<TestStudentCourse>( dtoList.size() );
        for ( TestStudentCourseDTO testStudentCourseDTO : dtoList ) {
            list.add( toEntity( testStudentCourseDTO ) );
        }

        return list;
    }

    @Override
    public List<TestStudentCourseDTO> toDTO(List<TestStudentCourse> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestStudentCourseDTO> list = new ArrayList<TestStudentCourseDTO>( entityList.size() );
        for ( TestStudentCourse testStudentCourse : entityList ) {
            list.add( toDTO( testStudentCourse ) );
        }

        return list;
    }

    @Override
    public Page<TestStudentCourse> toEntity(Page<TestStudentCourseDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestStudentCourse> page1 = new Page<TestStudentCourse>();

        page1.setPages( page.getPages() );
        page1.setRecords( toEntity( page.getRecords() ) );
        page1.setTotal( page.getTotal() );
        page1.setSize( page.getSize() );
        page1.setCurrent( page.getCurrent() );
        List<OrderItem> list1 = page.getOrders();
        if ( list1 != null ) {
            page1.setOrders( new ArrayList<OrderItem>( list1 ) );
        }

        return page1;
    }

    @Override
    public Page<TestStudentCourseDTO> toDTO(Page<TestStudentCourse> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestStudentCourseDTO> page1 = new Page<TestStudentCourseDTO>();

        page1.setPages( page.getPages() );
        page1.setRecords( toDTO( page.getRecords() ) );
        page1.setTotal( page.getTotal() );
        page1.setSize( page.getSize() );
        page1.setCurrent( page.getCurrent() );
        List<OrderItem> list1 = page.getOrders();
        if ( list1 != null ) {
            page1.setOrders( new ArrayList<OrderItem>( list1 ) );
        }

        return page1;
    }

    @Override
    public TestStudentCourse toEntity(TestStudentCourseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestStudentCourse testStudentCourse = new TestStudentCourse();

        testStudentCourse.setStudentId( dtoStudentId( dto ) );
        testStudentCourse.setCourseId( dtoCourseId( dto ) );
        testStudentCourse.setCreateById( dtoCreateById( dto ) );
        testStudentCourse.setUpdateById( dtoUpdateById( dto ) );
        testStudentCourse.setId( dto.getId() );
        testStudentCourse.setCreateTime( dto.getCreateTime() );
        testStudentCourse.setUpdateTime( dto.getUpdateTime() );
        testStudentCourse.setDelFlag( dto.getDelFlag() );
        testStudentCourse.setScore( dto.getScore() );
        testStudentCourse.setCreateDate( dto.getCreateDate() );
        testStudentCourse.setUpdateDate( dto.getUpdateDate() );
        testStudentCourse.setRemarks( dto.getRemarks() );

        return testStudentCourse;
    }

    @Override
    public TestStudentCourseDTO toDTO(TestStudentCourse entity) {
        if ( entity == null ) {
            return null;
        }

        TestStudentCourseDTO testStudentCourseDTO = new TestStudentCourseDTO();

        testStudentCourseDTO.setStudent( testStudentCourseToTestStudentDTO( entity ) );
        testStudentCourseDTO.setCourse( testStudentCourseToTestCourseDTO( entity ) );
        testStudentCourseDTO.setCreateBy( testStudentCourseToUserDTO( entity ) );
        testStudentCourseDTO.setUpdateBy( testStudentCourseToUserDTO1( entity ) );
        testStudentCourseDTO.setId( entity.getId() );
        testStudentCourseDTO.setCreateTime( entity.getCreateTime() );
        testStudentCourseDTO.setUpdateTime( entity.getUpdateTime() );
        testStudentCourseDTO.setDelFlag( entity.getDelFlag() );
        testStudentCourseDTO.setScore( entity.getScore() );
        testStudentCourseDTO.setCreateDate( entity.getCreateDate() );
        testStudentCourseDTO.setUpdateDate( entity.getUpdateDate() );
        testStudentCourseDTO.setRemarks( entity.getRemarks() );

        return testStudentCourseDTO;
    }

    private String dtoStudentId(TestStudentCourseDTO testStudentCourseDTO) {
        if ( testStudentCourseDTO == null ) {
            return null;
        }
        TestStudentDTO student = testStudentCourseDTO.getStudent();
        if ( student == null ) {
            return null;
        }
        String id = student.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCourseId(TestStudentCourseDTO testStudentCourseDTO) {
        if ( testStudentCourseDTO == null ) {
            return null;
        }
        TestCourseDTO course = testStudentCourseDTO.getCourse();
        if ( course == null ) {
            return null;
        }
        String id = course.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestStudentCourseDTO testStudentCourseDTO) {
        if ( testStudentCourseDTO == null ) {
            return null;
        }
        UserDTO createBy = testStudentCourseDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestStudentCourseDTO testStudentCourseDTO) {
        if ( testStudentCourseDTO == null ) {
            return null;
        }
        UserDTO updateBy = testStudentCourseDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestStudentDTO testStudentCourseToTestStudentDTO(TestStudentCourse testStudentCourse) {
        if ( testStudentCourse == null ) {
            return null;
        }

        TestStudentDTO testStudentDTO = new TestStudentDTO();

        testStudentDTO.setId( testStudentCourse.getStudentId() );

        return testStudentDTO;
    }

    protected TestCourseDTO testStudentCourseToTestCourseDTO(TestStudentCourse testStudentCourse) {
        if ( testStudentCourse == null ) {
            return null;
        }

        TestCourseDTO testCourseDTO = new TestCourseDTO();

        testCourseDTO.setId( testStudentCourse.getCourseId() );

        return testCourseDTO;
    }

    protected UserDTO testStudentCourseToUserDTO(TestStudentCourse testStudentCourse) {
        if ( testStudentCourse == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testStudentCourse.getCreateById() );

        return userDTO;
    }

    protected UserDTO testStudentCourseToUserDTO1(TestStudentCourse testStudentCourse) {
        if ( testStudentCourse == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testStudentCourse.getUpdateById() );

        return userDTO;
    }
}
