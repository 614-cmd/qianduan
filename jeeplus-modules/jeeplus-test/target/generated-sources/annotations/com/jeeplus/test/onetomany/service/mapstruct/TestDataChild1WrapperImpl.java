package com.jeeplus.test.onetomany.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.onetomany.domain.TestDataChild1;
import com.jeeplus.test.onetomany.service.dto.TestDataChild1DTO;
import com.jeeplus.test.onetomany.service.dto.TestDataMainDTO;
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
public class TestDataChild1WrapperImpl implements TestDataChild1Wrapper {

    @Override
    public List<TestDataChild1> toEntity(List<TestDataChild1DTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestDataChild1> list = new ArrayList<TestDataChild1>( dtoList.size() );
        for ( TestDataChild1DTO testDataChild1DTO : dtoList ) {
            list.add( toEntity( testDataChild1DTO ) );
        }

        return list;
    }

    @Override
    public List<TestDataChild1DTO> toDTO(List<TestDataChild1> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestDataChild1DTO> list = new ArrayList<TestDataChild1DTO>( entityList.size() );
        for ( TestDataChild1 testDataChild1 : entityList ) {
            list.add( toDTO( testDataChild1 ) );
        }

        return list;
    }

    @Override
    public Page<TestDataChild1> toEntity(Page<TestDataChild1DTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestDataChild1> page1 = new Page<TestDataChild1>();

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
    public Page<TestDataChild1DTO> toDTO(Page<TestDataChild1> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestDataChild1DTO> page1 = new Page<TestDataChild1DTO>();

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
    public TestDataChild1 toEntity(TestDataChild1DTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestDataChild1 testDataChild1 = new TestDataChild1();

        testDataChild1.setTestDataMainId( dtoTestDataMainId( dto ) );
        testDataChild1.setCreateById( dtoCreateById( dto ) );
        testDataChild1.setUpdateById( dtoUpdateById( dto ) );
        testDataChild1.setId( dto.getId() );
        testDataChild1.setCreateTime( dto.getCreateTime() );
        testDataChild1.setUpdateTime( dto.getUpdateTime() );
        testDataChild1.setDelFlag( dto.getDelFlag() );
        testDataChild1.setStartarea( dto.getStartarea() );
        testDataChild1.setEndarea( dto.getEndarea() );
        testDataChild1.setStarttime( dto.getStarttime() );
        testDataChild1.setEndtime( dto.getEndtime() );
        testDataChild1.setPrice( dto.getPrice() );
        testDataChild1.setRemarks( dto.getRemarks() );

        return testDataChild1;
    }

    @Override
    public TestDataChild1DTO toDTO(TestDataChild1 entity) {
        if ( entity == null ) {
            return null;
        }

        TestDataChild1DTO testDataChild1DTO = new TestDataChild1DTO();

        testDataChild1DTO.setTestDataMain( testDataChild1ToTestDataMainDTO( entity ) );
        testDataChild1DTO.setCreateBy( testDataChild1ToUserDTO( entity ) );
        testDataChild1DTO.setUpdateBy( testDataChild1ToUserDTO1( entity ) );
        testDataChild1DTO.setId( entity.getId() );
        testDataChild1DTO.setCreateTime( entity.getCreateTime() );
        testDataChild1DTO.setUpdateTime( entity.getUpdateTime() );
        testDataChild1DTO.setDelFlag( entity.getDelFlag() );
        testDataChild1DTO.setStartarea( entity.getStartarea() );
        testDataChild1DTO.setEndarea( entity.getEndarea() );
        testDataChild1DTO.setStarttime( entity.getStarttime() );
        testDataChild1DTO.setEndtime( entity.getEndtime() );
        testDataChild1DTO.setPrice( entity.getPrice() );
        testDataChild1DTO.setRemarks( entity.getRemarks() );

        return testDataChild1DTO;
    }

    private String dtoTestDataMainId(TestDataChild1DTO testDataChild1DTO) {
        if ( testDataChild1DTO == null ) {
            return null;
        }
        TestDataMainDTO testDataMain = testDataChild1DTO.getTestDataMain();
        if ( testDataMain == null ) {
            return null;
        }
        String id = testDataMain.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestDataChild1DTO testDataChild1DTO) {
        if ( testDataChild1DTO == null ) {
            return null;
        }
        UserDTO createBy = testDataChild1DTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestDataChild1DTO testDataChild1DTO) {
        if ( testDataChild1DTO == null ) {
            return null;
        }
        UserDTO updateBy = testDataChild1DTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestDataMainDTO testDataChild1ToTestDataMainDTO(TestDataChild1 testDataChild1) {
        if ( testDataChild1 == null ) {
            return null;
        }

        TestDataMainDTO testDataMainDTO = new TestDataMainDTO();

        testDataMainDTO.setId( testDataChild1.getTestDataMainId() );

        return testDataMainDTO;
    }

    protected UserDTO testDataChild1ToUserDTO(TestDataChild1 testDataChild1) {
        if ( testDataChild1 == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testDataChild1.getCreateById() );

        return userDTO;
    }

    protected UserDTO testDataChild1ToUserDTO1(TestDataChild1 testDataChild1) {
        if ( testDataChild1 == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testDataChild1.getUpdateById() );

        return userDTO;
    }
}
