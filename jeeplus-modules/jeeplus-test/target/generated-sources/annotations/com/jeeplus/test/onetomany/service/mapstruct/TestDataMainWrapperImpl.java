package com.jeeplus.test.onetomany.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.OfficeDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.onetomany.domain.TestDataMain;
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
public class TestDataMainWrapperImpl implements TestDataMainWrapper {

    @Override
    public List<TestDataMain> toEntity(List<TestDataMainDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestDataMain> list = new ArrayList<TestDataMain>( dtoList.size() );
        for ( TestDataMainDTO testDataMainDTO : dtoList ) {
            list.add( toEntity( testDataMainDTO ) );
        }

        return list;
    }

    @Override
    public List<TestDataMainDTO> toDTO(List<TestDataMain> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestDataMainDTO> list = new ArrayList<TestDataMainDTO>( entityList.size() );
        for ( TestDataMain testDataMain : entityList ) {
            list.add( toDTO( testDataMain ) );
        }

        return list;
    }

    @Override
    public Page<TestDataMain> toEntity(Page<TestDataMainDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestDataMain> page1 = new Page<TestDataMain>();

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
    public Page<TestDataMainDTO> toDTO(Page<TestDataMain> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestDataMainDTO> page1 = new Page<TestDataMainDTO>();

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
    public TestDataMain toEntity(TestDataMainDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestDataMain testDataMain = new TestDataMain();

        testDataMain.setUserId( dtoUserId( dto ) );
        testDataMain.setOfficeId( dtoOfficeId( dto ) );
        testDataMain.setCreateById( dtoCreateById( dto ) );
        testDataMain.setUpdateById( dtoUpdateById( dto ) );
        testDataMain.setId( dto.getId() );
        testDataMain.setCreateTime( dto.getCreateTime() );
        testDataMain.setUpdateTime( dto.getUpdateTime() );
        testDataMain.setDelFlag( dto.getDelFlag() );
        testDataMain.setArea( dto.getArea() );
        testDataMain.setName( dto.getName() );
        testDataMain.setSex( dto.getSex() );
        testDataMain.setFile( dto.getFile() );
        testDataMain.setInDate( dto.getInDate() );
        testDataMain.setRemarks( dto.getRemarks() );

        return testDataMain;
    }

    @Override
    public TestDataMainDTO toDTO(TestDataMain entity) {
        if ( entity == null ) {
            return null;
        }

        TestDataMainDTO testDataMainDTO = new TestDataMainDTO();

        testDataMainDTO.setUser( testDataMainToUserDTO( entity ) );
        testDataMainDTO.setOffice( testDataMainToOfficeDTO( entity ) );
        testDataMainDTO.setCreateBy( testDataMainToUserDTO1( entity ) );
        testDataMainDTO.setUpdateBy( testDataMainToUserDTO2( entity ) );
        testDataMainDTO.setId( entity.getId() );
        testDataMainDTO.setCreateTime( entity.getCreateTime() );
        testDataMainDTO.setUpdateTime( entity.getUpdateTime() );
        testDataMainDTO.setDelFlag( entity.getDelFlag() );
        testDataMainDTO.setArea( entity.getArea() );
        testDataMainDTO.setName( entity.getName() );
        testDataMainDTO.setSex( entity.getSex() );
        testDataMainDTO.setFile( entity.getFile() );
        testDataMainDTO.setInDate( entity.getInDate() );
        testDataMainDTO.setRemarks( entity.getRemarks() );

        return testDataMainDTO;
    }

    private String dtoUserId(TestDataMainDTO testDataMainDTO) {
        if ( testDataMainDTO == null ) {
            return null;
        }
        UserDTO user = testDataMainDTO.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoOfficeId(TestDataMainDTO testDataMainDTO) {
        if ( testDataMainDTO == null ) {
            return null;
        }
        OfficeDTO office = testDataMainDTO.getOffice();
        if ( office == null ) {
            return null;
        }
        String id = office.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestDataMainDTO testDataMainDTO) {
        if ( testDataMainDTO == null ) {
            return null;
        }
        UserDTO createBy = testDataMainDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestDataMainDTO testDataMainDTO) {
        if ( testDataMainDTO == null ) {
            return null;
        }
        UserDTO updateBy = testDataMainDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO testDataMainToUserDTO(TestDataMain testDataMain) {
        if ( testDataMain == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testDataMain.getUserId() );

        return userDTO;
    }

    protected OfficeDTO testDataMainToOfficeDTO(TestDataMain testDataMain) {
        if ( testDataMain == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( testDataMain.getOfficeId() );

        return officeDTO;
    }

    protected UserDTO testDataMainToUserDTO1(TestDataMain testDataMain) {
        if ( testDataMain == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testDataMain.getCreateById() );

        return userDTO;
    }

    protected UserDTO testDataMainToUserDTO2(TestDataMain testDataMain) {
        if ( testDataMain == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testDataMain.getUpdateById() );

        return userDTO;
    }
}
