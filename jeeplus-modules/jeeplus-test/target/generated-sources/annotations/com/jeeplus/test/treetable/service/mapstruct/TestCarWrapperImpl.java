package com.jeeplus.test.treetable.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.treetable.domain.TestCar;
import com.jeeplus.test.treetable.service.dto.TestCarDTO;
import com.jeeplus.test.treetable.service.dto.TestCarKindDTO;
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
public class TestCarWrapperImpl implements TestCarWrapper {

    @Override
    public List<TestCar> toEntity(List<TestCarDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestCar> list = new ArrayList<TestCar>( dtoList.size() );
        for ( TestCarDTO testCarDTO : dtoList ) {
            list.add( toEntity( testCarDTO ) );
        }

        return list;
    }

    @Override
    public List<TestCarDTO> toDTO(List<TestCar> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestCarDTO> list = new ArrayList<TestCarDTO>( entityList.size() );
        for ( TestCar testCar : entityList ) {
            list.add( toDTO( testCar ) );
        }

        return list;
    }

    @Override
    public Page<TestCar> toEntity(Page<TestCarDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCar> page1 = new Page<TestCar>();

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
    public Page<TestCarDTO> toDTO(Page<TestCar> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCarDTO> page1 = new Page<TestCarDTO>();

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
    public TestCar toEntity(TestCarDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestCar testCar = new TestCar();

        testCar.setKindId( dtoKindId( dto ) );
        testCar.setCreateById( dtoCreateById( dto ) );
        testCar.setUpdateById( dtoUpdateById( dto ) );
        testCar.setId( dto.getId() );
        testCar.setCreateTime( dto.getCreateTime() );
        testCar.setUpdateTime( dto.getUpdateTime() );
        testCar.setDelFlag( dto.getDelFlag() );
        testCar.setName( dto.getName() );
        testCar.setRemarks( dto.getRemarks() );

        return testCar;
    }

    @Override
    public TestCarDTO toDTO(TestCar entity) {
        if ( entity == null ) {
            return null;
        }

        TestCarDTO testCarDTO = new TestCarDTO();

        testCarDTO.setKind( testCarToTestCarKindDTO( entity ) );
        testCarDTO.setCreateBy( testCarToUserDTO( entity ) );
        testCarDTO.setUpdateBy( testCarToUserDTO1( entity ) );
        testCarDTO.setId( entity.getId() );
        testCarDTO.setCreateTime( entity.getCreateTime() );
        testCarDTO.setUpdateTime( entity.getUpdateTime() );
        testCarDTO.setDelFlag( entity.getDelFlag() );
        testCarDTO.setName( entity.getName() );
        testCarDTO.setRemarks( entity.getRemarks() );

        return testCarDTO;
    }

    private String dtoKindId(TestCarDTO testCarDTO) {
        if ( testCarDTO == null ) {
            return null;
        }
        TestCarKindDTO kind = testCarDTO.getKind();
        if ( kind == null ) {
            return null;
        }
        String id = kind.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestCarDTO testCarDTO) {
        if ( testCarDTO == null ) {
            return null;
        }
        UserDTO createBy = testCarDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestCarDTO testCarDTO) {
        if ( testCarDTO == null ) {
            return null;
        }
        UserDTO updateBy = testCarDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestCarKindDTO testCarToTestCarKindDTO(TestCar testCar) {
        if ( testCar == null ) {
            return null;
        }

        TestCarKindDTO testCarKindDTO = new TestCarKindDTO();

        testCarKindDTO.setId( testCar.getKindId() );

        return testCarKindDTO;
    }

    protected UserDTO testCarToUserDTO(TestCar testCar) {
        if ( testCar == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCar.getCreateById() );

        return userDTO;
    }

    protected UserDTO testCarToUserDTO1(TestCar testCar) {
        if ( testCar == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCar.getUpdateById() );

        return userDTO;
    }
}
