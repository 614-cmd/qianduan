package com.jeeplus.test.onetomany.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.onetomany.domain.TestDataChild3;
import com.jeeplus.test.onetomany.service.dto.TestDataChild3DTO;
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
public class TestDataChild3WrapperImpl implements TestDataChild3Wrapper {

    @Override
    public List<TestDataChild3> toEntity(List<TestDataChild3DTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestDataChild3> list = new ArrayList<TestDataChild3>( dtoList.size() );
        for ( TestDataChild3DTO testDataChild3DTO : dtoList ) {
            list.add( toEntity( testDataChild3DTO ) );
        }

        return list;
    }

    @Override
    public List<TestDataChild3DTO> toDTO(List<TestDataChild3> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestDataChild3DTO> list = new ArrayList<TestDataChild3DTO>( entityList.size() );
        for ( TestDataChild3 testDataChild3 : entityList ) {
            list.add( toDTO( testDataChild3 ) );
        }

        return list;
    }

    @Override
    public Page<TestDataChild3> toEntity(Page<TestDataChild3DTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestDataChild3> page1 = new Page<TestDataChild3>();

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
    public Page<TestDataChild3DTO> toDTO(Page<TestDataChild3> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestDataChild3DTO> page1 = new Page<TestDataChild3DTO>();

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
    public TestDataChild3 toEntity(TestDataChild3DTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestDataChild3 testDataChild3 = new TestDataChild3();

        testDataChild3.setTestDataMainId( dtoTestDataMainId( dto ) );
        testDataChild3.setCreateById( dtoCreateById( dto ) );
        testDataChild3.setUpdateById( dtoUpdateById( dto ) );
        testDataChild3.setId( dto.getId() );
        testDataChild3.setCreateTime( dto.getCreateTime() );
        testDataChild3.setUpdateTime( dto.getUpdateTime() );
        testDataChild3.setDelFlag( dto.getDelFlag() );
        testDataChild3.setStartarea( dto.getStartarea() );
        testDataChild3.setEndarea( dto.getEndarea() );
        testDataChild3.setPrice( dto.getPrice() );
        testDataChild3.setStarttime( dto.getStarttime() );
        testDataChild3.setEndtime( dto.getEndtime() );
        testDataChild3.setCreateDate( dto.getCreateDate() );
        testDataChild3.setUpdateDate( dto.getUpdateDate() );
        testDataChild3.setRemarks( dto.getRemarks() );

        return testDataChild3;
    }

    @Override
    public TestDataChild3DTO toDTO(TestDataChild3 entity) {
        if ( entity == null ) {
            return null;
        }

        TestDataChild3DTO testDataChild3DTO = new TestDataChild3DTO();

        testDataChild3DTO.setTestDataMain( testDataChild3ToTestDataMainDTO( entity ) );
        testDataChild3DTO.setCreateBy( testDataChild3ToUserDTO( entity ) );
        testDataChild3DTO.setUpdateBy( testDataChild3ToUserDTO1( entity ) );
        testDataChild3DTO.setId( entity.getId() );
        testDataChild3DTO.setCreateTime( entity.getCreateTime() );
        testDataChild3DTO.setUpdateTime( entity.getUpdateTime() );
        testDataChild3DTO.setDelFlag( entity.getDelFlag() );
        testDataChild3DTO.setStartarea( entity.getStartarea() );
        testDataChild3DTO.setEndarea( entity.getEndarea() );
        testDataChild3DTO.setPrice( entity.getPrice() );
        testDataChild3DTO.setStarttime( entity.getStarttime() );
        testDataChild3DTO.setEndtime( entity.getEndtime() );
        testDataChild3DTO.setCreateDate( entity.getCreateDate() );
        testDataChild3DTO.setUpdateDate( entity.getUpdateDate() );
        testDataChild3DTO.setRemarks( entity.getRemarks() );

        return testDataChild3DTO;
    }

    private String dtoTestDataMainId(TestDataChild3DTO testDataChild3DTO) {
        if ( testDataChild3DTO == null ) {
            return null;
        }
        TestDataMainDTO testDataMain = testDataChild3DTO.getTestDataMain();
        if ( testDataMain == null ) {
            return null;
        }
        String id = testDataMain.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestDataChild3DTO testDataChild3DTO) {
        if ( testDataChild3DTO == null ) {
            return null;
        }
        UserDTO createBy = testDataChild3DTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestDataChild3DTO testDataChild3DTO) {
        if ( testDataChild3DTO == null ) {
            return null;
        }
        UserDTO updateBy = testDataChild3DTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestDataMainDTO testDataChild3ToTestDataMainDTO(TestDataChild3 testDataChild3) {
        if ( testDataChild3 == null ) {
            return null;
        }

        TestDataMainDTO testDataMainDTO = new TestDataMainDTO();

        testDataMainDTO.setId( testDataChild3.getTestDataMainId() );

        return testDataMainDTO;
    }

    protected UserDTO testDataChild3ToUserDTO(TestDataChild3 testDataChild3) {
        if ( testDataChild3 == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testDataChild3.getCreateById() );

        return userDTO;
    }

    protected UserDTO testDataChild3ToUserDTO1(TestDataChild3 testDataChild3) {
        if ( testDataChild3 == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testDataChild3.getUpdateById() );

        return userDTO;
    }
}
