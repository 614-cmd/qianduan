package com.jeeplus.test.grid.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.grid.domain.TestCountry;
import com.jeeplus.test.grid.service.dto.TestContinentDTO;
import com.jeeplus.test.grid.service.dto.TestCountryDTO;
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
public class TestCountryWrapperImpl implements TestCountryWrapper {

    @Override
    public List<TestCountry> toEntity(List<TestCountryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestCountry> list = new ArrayList<TestCountry>( dtoList.size() );
        for ( TestCountryDTO testCountryDTO : dtoList ) {
            list.add( toEntity( testCountryDTO ) );
        }

        return list;
    }

    @Override
    public List<TestCountryDTO> toDTO(List<TestCountry> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestCountryDTO> list = new ArrayList<TestCountryDTO>( entityList.size() );
        for ( TestCountry testCountry : entityList ) {
            list.add( toDTO( testCountry ) );
        }

        return list;
    }

    @Override
    public Page<TestCountry> toEntity(Page<TestCountryDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCountry> page1 = new Page<TestCountry>();

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
    public Page<TestCountryDTO> toDTO(Page<TestCountry> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCountryDTO> page1 = new Page<TestCountryDTO>();

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
    public TestCountry toEntity(TestCountryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestCountry testCountry = new TestCountry();

        testCountry.setContinentId( dtoContinentId( dto ) );
        testCountry.setCreateById( dtoCreateById( dto ) );
        testCountry.setUpdateById( dtoUpdateById( dto ) );
        testCountry.setId( dto.getId() );
        testCountry.setCreateTime( dto.getCreateTime() );
        testCountry.setUpdateTime( dto.getUpdateTime() );
        testCountry.setDelFlag( dto.getDelFlag() );
        testCountry.setName( dto.getName() );
        testCountry.setSum( dto.getSum() );
        testCountry.setRemarks( dto.getRemarks() );

        return testCountry;
    }

    @Override
    public TestCountryDTO toDTO(TestCountry entity) {
        if ( entity == null ) {
            return null;
        }

        TestCountryDTO testCountryDTO = new TestCountryDTO();

        testCountryDTO.setContinent( testCountryToTestContinentDTO( entity ) );
        testCountryDTO.setCreateBy( testCountryToUserDTO( entity ) );
        testCountryDTO.setUpdateBy( testCountryToUserDTO1( entity ) );
        testCountryDTO.setId( entity.getId() );
        testCountryDTO.setCreateTime( entity.getCreateTime() );
        testCountryDTO.setUpdateTime( entity.getUpdateTime() );
        testCountryDTO.setDelFlag( entity.getDelFlag() );
        testCountryDTO.setName( entity.getName() );
        testCountryDTO.setSum( entity.getSum() );
        testCountryDTO.setRemarks( entity.getRemarks() );

        return testCountryDTO;
    }

    private String dtoContinentId(TestCountryDTO testCountryDTO) {
        if ( testCountryDTO == null ) {
            return null;
        }
        TestContinentDTO continent = testCountryDTO.getContinent();
        if ( continent == null ) {
            return null;
        }
        String id = continent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestCountryDTO testCountryDTO) {
        if ( testCountryDTO == null ) {
            return null;
        }
        UserDTO createBy = testCountryDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestCountryDTO testCountryDTO) {
        if ( testCountryDTO == null ) {
            return null;
        }
        UserDTO updateBy = testCountryDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestContinentDTO testCountryToTestContinentDTO(TestCountry testCountry) {
        if ( testCountry == null ) {
            return null;
        }

        TestContinentDTO testContinentDTO = new TestContinentDTO();

        testContinentDTO.setId( testCountry.getContinentId() );

        return testContinentDTO;
    }

    protected UserDTO testCountryToUserDTO(TestCountry testCountry) {
        if ( testCountry == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCountry.getCreateById() );

        return userDTO;
    }

    protected UserDTO testCountryToUserDTO1(TestCountry testCountry) {
        if ( testCountry == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCountry.getUpdateById() );

        return userDTO;
    }
}
