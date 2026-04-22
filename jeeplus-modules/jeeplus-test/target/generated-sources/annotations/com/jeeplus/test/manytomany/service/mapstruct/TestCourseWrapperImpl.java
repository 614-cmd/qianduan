package com.jeeplus.test.manytomany.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.manytomany.domain.TestCourse;
import com.jeeplus.test.manytomany.service.dto.TestCourseDTO;
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
public class TestCourseWrapperImpl implements TestCourseWrapper {

    @Override
    public TestCourse toEntity(TestCourseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestCourse testCourse = new TestCourse();

        testCourse.setCreateById( dtoCreateById( dto ) );
        testCourse.setUpdateById( dtoUpdateById( dto ) );
        testCourse.setId( dto.getId() );
        testCourse.setCreateTime( dto.getCreateTime() );
        testCourse.setUpdateTime( dto.getUpdateTime() );
        testCourse.setDelFlag( dto.getDelFlag() );
        testCourse.setName( dto.getName() );
        testCourse.setRemarks( dto.getRemarks() );
        testCourse.setCreateDate( dto.getCreateDate() );
        testCourse.setUpdateDate( dto.getUpdateDate() );

        return testCourse;
    }

    @Override
    public TestCourseDTO toDTO(TestCourse entity) {
        if ( entity == null ) {
            return null;
        }

        TestCourseDTO testCourseDTO = new TestCourseDTO();

        testCourseDTO.setCreateBy( testCourseToUserDTO( entity ) );
        testCourseDTO.setUpdateBy( testCourseToUserDTO1( entity ) );
        testCourseDTO.setId( entity.getId() );
        testCourseDTO.setCreateTime( entity.getCreateTime() );
        testCourseDTO.setUpdateTime( entity.getUpdateTime() );
        testCourseDTO.setDelFlag( entity.getDelFlag() );
        testCourseDTO.setName( entity.getName() );
        testCourseDTO.setRemarks( entity.getRemarks() );
        testCourseDTO.setCreateDate( entity.getCreateDate() );
        testCourseDTO.setUpdateDate( entity.getUpdateDate() );

        return testCourseDTO;
    }

    @Override
    public List<TestCourse> toEntity(List<TestCourseDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestCourse> list = new ArrayList<TestCourse>( dtoList.size() );
        for ( TestCourseDTO testCourseDTO : dtoList ) {
            list.add( toEntity( testCourseDTO ) );
        }

        return list;
    }

    @Override
    public List<TestCourseDTO> toDTO(List<TestCourse> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestCourseDTO> list = new ArrayList<TestCourseDTO>( entityList.size() );
        for ( TestCourse testCourse : entityList ) {
            list.add( toDTO( testCourse ) );
        }

        return list;
    }

    @Override
    public Page<TestCourse> toEntity(Page<TestCourseDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCourse> page1 = new Page<TestCourse>();

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
    public Page<TestCourseDTO> toDTO(Page<TestCourse> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCourseDTO> page1 = new Page<TestCourseDTO>();

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

    private String dtoCreateById(TestCourseDTO testCourseDTO) {
        if ( testCourseDTO == null ) {
            return null;
        }
        UserDTO createBy = testCourseDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestCourseDTO testCourseDTO) {
        if ( testCourseDTO == null ) {
            return null;
        }
        UserDTO updateBy = testCourseDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO testCourseToUserDTO(TestCourse testCourse) {
        if ( testCourse == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCourse.getCreateById() );

        return userDTO;
    }

    protected UserDTO testCourseToUserDTO1(TestCourse testCourse) {
        if ( testCourse == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCourse.getUpdateById() );

        return userDTO;
    }
}
