package com.jeeplus.test.onetomany.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.onetomany.domain.TestDataChild2;
import com.jeeplus.test.onetomany.service.dto.TestDataChild2DTO;
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
public class TestDataChild2WrapperImpl implements TestDataChild2Wrapper {

    @Override
    public List<TestDataChild2> toEntity(List<TestDataChild2DTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestDataChild2> list = new ArrayList<TestDataChild2>( dtoList.size() );
        for ( TestDataChild2DTO testDataChild2DTO : dtoList ) {
            list.add( toEntity( testDataChild2DTO ) );
        }

        return list;
    }

    @Override
    public List<TestDataChild2DTO> toDTO(List<TestDataChild2> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestDataChild2DTO> list = new ArrayList<TestDataChild2DTO>( entityList.size() );
        for ( TestDataChild2 testDataChild2 : entityList ) {
            list.add( toDTO( testDataChild2 ) );
        }

        return list;
    }

    @Override
    public Page<TestDataChild2> toEntity(Page<TestDataChild2DTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestDataChild2> page1 = new Page<TestDataChild2>();

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
    public Page<TestDataChild2DTO> toDTO(Page<TestDataChild2> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestDataChild2DTO> page1 = new Page<TestDataChild2DTO>();

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
    public TestDataChild2 toEntity(TestDataChild2DTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestDataChild2 testDataChild2 = new TestDataChild2();

        testDataChild2.setTestDataMainId( dtoTestDataMainId( dto ) );
        testDataChild2.setCreateById( dtoCreateById( dto ) );
        testDataChild2.setUpdateById( dtoUpdateById( dto ) );
        testDataChild2.setId( dto.getId() );
        testDataChild2.setCreateTime( dto.getCreateTime() );
        testDataChild2.setUpdateTime( dto.getUpdateTime() );
        testDataChild2.setDelFlag( dto.getDelFlag() );
        testDataChild2.setStartarea( dto.getStartarea() );
        testDataChild2.setEndarea( dto.getEndarea() );
        testDataChild2.setStarttime( dto.getStarttime() );
        testDataChild2.setEndtime( dto.getEndtime() );
        testDataChild2.setPrice( dto.getPrice() );
        testDataChild2.setRemarks( dto.getRemarks() );

        return testDataChild2;
    }

    @Override
    public TestDataChild2DTO toDTO(TestDataChild2 entity) {
        if ( entity == null ) {
            return null;
        }

        TestDataChild2DTO testDataChild2DTO = new TestDataChild2DTO();

        testDataChild2DTO.setTestDataMain( testDataChild2ToTestDataMainDTO( entity ) );
        testDataChild2DTO.setCreateBy( testDataChild2ToUserDTO( entity ) );
        testDataChild2DTO.setUpdateBy( testDataChild2ToUserDTO1( entity ) );
        testDataChild2DTO.setId( entity.getId() );
        testDataChild2DTO.setCreateTime( entity.getCreateTime() );
        testDataChild2DTO.setUpdateTime( entity.getUpdateTime() );
        testDataChild2DTO.setDelFlag( entity.getDelFlag() );
        testDataChild2DTO.setStartarea( entity.getStartarea() );
        testDataChild2DTO.setEndarea( entity.getEndarea() );
        testDataChild2DTO.setStarttime( entity.getStarttime() );
        testDataChild2DTO.setEndtime( entity.getEndtime() );
        testDataChild2DTO.setPrice( entity.getPrice() );
        testDataChild2DTO.setRemarks( entity.getRemarks() );

        return testDataChild2DTO;
    }

    private String dtoTestDataMainId(TestDataChild2DTO testDataChild2DTO) {
        if ( testDataChild2DTO == null ) {
            return null;
        }
        TestDataMainDTO testDataMain = testDataChild2DTO.getTestDataMain();
        if ( testDataMain == null ) {
            return null;
        }
        String id = testDataMain.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestDataChild2DTO testDataChild2DTO) {
        if ( testDataChild2DTO == null ) {
            return null;
        }
        UserDTO createBy = testDataChild2DTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestDataChild2DTO testDataChild2DTO) {
        if ( testDataChild2DTO == null ) {
            return null;
        }
        UserDTO updateBy = testDataChild2DTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestDataMainDTO testDataChild2ToTestDataMainDTO(TestDataChild2 testDataChild2) {
        if ( testDataChild2 == null ) {
            return null;
        }

        TestDataMainDTO testDataMainDTO = new TestDataMainDTO();

        testDataMainDTO.setId( testDataChild2.getTestDataMainId() );

        return testDataMainDTO;
    }

    protected UserDTO testDataChild2ToUserDTO(TestDataChild2 testDataChild2) {
        if ( testDataChild2 == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testDataChild2.getCreateById() );

        return userDTO;
    }

    protected UserDTO testDataChild2ToUserDTO1(TestDataChild2 testDataChild2) {
        if ( testDataChild2 == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testDataChild2.getUpdateById() );

        return userDTO;
    }
}
