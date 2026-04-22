package com.jeeplus.test.grid.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.grid.domain.TestContinent;
import com.jeeplus.test.grid.service.dto.TestContinentDTO;
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
public class TestContinentWrapperImpl implements TestContinentWrapper {

    @Override
    public TestContinent toEntity(TestContinentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestContinent testContinent = new TestContinent();

        testContinent.setCreateById( dtoCreateById( dto ) );
        testContinent.setUpdateById( dtoUpdateById( dto ) );
        testContinent.setId( dto.getId() );
        testContinent.setCreateTime( dto.getCreateTime() );
        testContinent.setUpdateTime( dto.getUpdateTime() );
        testContinent.setDelFlag( dto.getDelFlag() );
        testContinent.setName( dto.getName() );
        testContinent.setRemarks( dto.getRemarks() );

        return testContinent;
    }

    @Override
    public TestContinentDTO toDTO(TestContinent entity) {
        if ( entity == null ) {
            return null;
        }

        TestContinentDTO testContinentDTO = new TestContinentDTO();

        testContinentDTO.setCreateBy( testContinentToUserDTO( entity ) );
        testContinentDTO.setUpdateBy( testContinentToUserDTO1( entity ) );
        testContinentDTO.setId( entity.getId() );
        testContinentDTO.setCreateTime( entity.getCreateTime() );
        testContinentDTO.setUpdateTime( entity.getUpdateTime() );
        testContinentDTO.setDelFlag( entity.getDelFlag() );
        testContinentDTO.setName( entity.getName() );
        testContinentDTO.setRemarks( entity.getRemarks() );

        return testContinentDTO;
    }

    @Override
    public List<TestContinent> toEntity(List<TestContinentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestContinent> list = new ArrayList<TestContinent>( dtoList.size() );
        for ( TestContinentDTO testContinentDTO : dtoList ) {
            list.add( toEntity( testContinentDTO ) );
        }

        return list;
    }

    @Override
    public List<TestContinentDTO> toDTO(List<TestContinent> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestContinentDTO> list = new ArrayList<TestContinentDTO>( entityList.size() );
        for ( TestContinent testContinent : entityList ) {
            list.add( toDTO( testContinent ) );
        }

        return list;
    }

    @Override
    public Page<TestContinent> toEntity(Page<TestContinentDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestContinent> page1 = new Page<TestContinent>();

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
    public Page<TestContinentDTO> toDTO(Page<TestContinent> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestContinentDTO> page1 = new Page<TestContinentDTO>();

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

    private String dtoCreateById(TestContinentDTO testContinentDTO) {
        if ( testContinentDTO == null ) {
            return null;
        }
        UserDTO createBy = testContinentDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestContinentDTO testContinentDTO) {
        if ( testContinentDTO == null ) {
            return null;
        }
        UserDTO updateBy = testContinentDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO testContinentToUserDTO(TestContinent testContinent) {
        if ( testContinent == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testContinent.getCreateById() );

        return userDTO;
    }

    protected UserDTO testContinentToUserDTO1(TestContinent testContinent) {
        if ( testContinent == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testContinent.getUpdateById() );

        return userDTO;
    }
}
