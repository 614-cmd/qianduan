package com.jeeplus.test.manytomany.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.manytomany.domain.TestStudent;
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
public class TestStudentWrapperImpl implements TestStudentWrapper {

    @Override
    public TestStudent toEntity(TestStudentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestStudent testStudent = new TestStudent();

        testStudent.setCreateById( dtoCreateById( dto ) );
        testStudent.setUpdateById( dtoUpdateById( dto ) );
        testStudent.setId( dto.getId() );
        testStudent.setCreateTime( dto.getCreateTime() );
        testStudent.setUpdateTime( dto.getUpdateTime() );
        testStudent.setDelFlag( dto.getDelFlag() );
        testStudent.setName( dto.getName() );
        testStudent.setCreateDate( dto.getCreateDate() );
        testStudent.setUpdateDate( dto.getUpdateDate() );
        testStudent.setRemarks( dto.getRemarks() );

        return testStudent;
    }

    @Override
    public TestStudentDTO toDTO(TestStudent entity) {
        if ( entity == null ) {
            return null;
        }

        TestStudentDTO testStudentDTO = new TestStudentDTO();

        testStudentDTO.setCreateBy( testStudentToUserDTO( entity ) );
        testStudentDTO.setUpdateBy( testStudentToUserDTO1( entity ) );
        testStudentDTO.setId( entity.getId() );
        testStudentDTO.setCreateTime( entity.getCreateTime() );
        testStudentDTO.setUpdateTime( entity.getUpdateTime() );
        testStudentDTO.setDelFlag( entity.getDelFlag() );
        testStudentDTO.setName( entity.getName() );
        testStudentDTO.setCreateDate( entity.getCreateDate() );
        testStudentDTO.setUpdateDate( entity.getUpdateDate() );
        testStudentDTO.setRemarks( entity.getRemarks() );

        return testStudentDTO;
    }

    @Override
    public List<TestStudent> toEntity(List<TestStudentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestStudent> list = new ArrayList<TestStudent>( dtoList.size() );
        for ( TestStudentDTO testStudentDTO : dtoList ) {
            list.add( toEntity( testStudentDTO ) );
        }

        return list;
    }

    @Override
    public List<TestStudentDTO> toDTO(List<TestStudent> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestStudentDTO> list = new ArrayList<TestStudentDTO>( entityList.size() );
        for ( TestStudent testStudent : entityList ) {
            list.add( toDTO( testStudent ) );
        }

        return list;
    }

    @Override
    public Page<TestStudent> toEntity(Page<TestStudentDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestStudent> page1 = new Page<TestStudent>();

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
    public Page<TestStudentDTO> toDTO(Page<TestStudent> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestStudentDTO> page1 = new Page<TestStudentDTO>();

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

    private String dtoCreateById(TestStudentDTO testStudentDTO) {
        if ( testStudentDTO == null ) {
            return null;
        }
        UserDTO createBy = testStudentDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestStudentDTO testStudentDTO) {
        if ( testStudentDTO == null ) {
            return null;
        }
        UserDTO updateBy = testStudentDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO testStudentToUserDTO(TestStudent testStudent) {
        if ( testStudent == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testStudent.getCreateById() );

        return userDTO;
    }

    protected UserDTO testStudentToUserDTO1(TestStudent testStudent) {
        if ( testStudent == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testStudent.getUpdateById() );

        return userDTO;
    }
}
