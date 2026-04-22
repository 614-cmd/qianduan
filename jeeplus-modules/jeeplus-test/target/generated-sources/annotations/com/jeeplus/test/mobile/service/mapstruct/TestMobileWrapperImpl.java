package com.jeeplus.test.mobile.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.OfficeDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.mobile.domain.TestMobile;
import com.jeeplus.test.mobile.service.dto.TestMobileDTO;
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
public class TestMobileWrapperImpl implements TestMobileWrapper {

    @Override
    public List<TestMobile> toEntity(List<TestMobileDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestMobile> list = new ArrayList<TestMobile>( dtoList.size() );
        for ( TestMobileDTO testMobileDTO : dtoList ) {
            list.add( toEntity( testMobileDTO ) );
        }

        return list;
    }

    @Override
    public List<TestMobileDTO> toDTO(List<TestMobile> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestMobileDTO> list = new ArrayList<TestMobileDTO>( entityList.size() );
        for ( TestMobile testMobile : entityList ) {
            list.add( toDTO( testMobile ) );
        }

        return list;
    }

    @Override
    public Page<TestMobile> toEntity(Page<TestMobileDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestMobile> page1 = new Page<TestMobile>();

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
    public Page<TestMobileDTO> toDTO(Page<TestMobile> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestMobileDTO> page1 = new Page<TestMobileDTO>();

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
    public TestMobile toEntity(TestMobileDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestMobile testMobile = new TestMobile();

        testMobile.setCompanyId( dtoCompanyId( dto ) );
        testMobile.setOfficeId( dtoOfficeId( dto ) );
        testMobile.setCreateById( dtoCreateById( dto ) );
        testMobile.setUpdateById( dtoUpdateById( dto ) );
        testMobile.setId( dto.getId() );
        testMobile.setCreateTime( dto.getCreateTime() );
        testMobile.setUpdateTime( dto.getUpdateTime() );
        testMobile.setDelFlag( dto.getDelFlag() );
        testMobile.setName( dto.getName() );
        testMobile.setAge( dto.getAge() );
        testMobile.setAreaId( dto.getAreaId() );
        testMobile.setSex( dto.getSex() );
        testMobile.setTeImage( dto.getTeImage() );
        testMobile.setIsSingle( dto.getIsSingle() );
        testMobile.setCks( dto.getCks() );
        testMobile.setTextArea( dto.getTextArea() );
        testMobile.setTextNumber( dto.getTextNumber() );
        testMobile.setTeDate( dto.getTeDate() );
        testMobile.setEmail( dto.getEmail() );
        testMobile.setPhone( dto.getPhone() );
        testMobile.setTel( dto.getTel() );

        return testMobile;
    }

    @Override
    public TestMobileDTO toDTO(TestMobile entity) {
        if ( entity == null ) {
            return null;
        }

        TestMobileDTO testMobileDTO = new TestMobileDTO();

        testMobileDTO.setCompany( testMobileToOfficeDTO( entity ) );
        testMobileDTO.setOffice( testMobileToOfficeDTO1( entity ) );
        testMobileDTO.setCreateBy( testMobileToUserDTO( entity ) );
        testMobileDTO.setUpdateBy( testMobileToUserDTO1( entity ) );
        testMobileDTO.setId( entity.getId() );
        testMobileDTO.setCreateTime( entity.getCreateTime() );
        testMobileDTO.setUpdateTime( entity.getUpdateTime() );
        testMobileDTO.setDelFlag( entity.getDelFlag() );
        testMobileDTO.setName( entity.getName() );
        testMobileDTO.setAge( entity.getAge() );
        testMobileDTO.setAreaId( entity.getAreaId() );
        testMobileDTO.setSex( entity.getSex() );
        testMobileDTO.setTeImage( entity.getTeImage() );
        testMobileDTO.setIsSingle( entity.getIsSingle() );
        testMobileDTO.setCks( entity.getCks() );
        testMobileDTO.setTextArea( entity.getTextArea() );
        testMobileDTO.setTextNumber( entity.getTextNumber() );
        testMobileDTO.setTeDate( entity.getTeDate() );
        testMobileDTO.setEmail( entity.getEmail() );
        testMobileDTO.setPhone( entity.getPhone() );
        testMobileDTO.setTel( entity.getTel() );

        return testMobileDTO;
    }

    private String dtoCompanyId(TestMobileDTO testMobileDTO) {
        if ( testMobileDTO == null ) {
            return null;
        }
        OfficeDTO company = testMobileDTO.getCompany();
        if ( company == null ) {
            return null;
        }
        String id = company.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoOfficeId(TestMobileDTO testMobileDTO) {
        if ( testMobileDTO == null ) {
            return null;
        }
        OfficeDTO office = testMobileDTO.getOffice();
        if ( office == null ) {
            return null;
        }
        String id = office.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestMobileDTO testMobileDTO) {
        if ( testMobileDTO == null ) {
            return null;
        }
        UserDTO createBy = testMobileDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestMobileDTO testMobileDTO) {
        if ( testMobileDTO == null ) {
            return null;
        }
        UserDTO updateBy = testMobileDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected OfficeDTO testMobileToOfficeDTO(TestMobile testMobile) {
        if ( testMobile == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( testMobile.getCompanyId() );

        return officeDTO;
    }

    protected OfficeDTO testMobileToOfficeDTO1(TestMobile testMobile) {
        if ( testMobile == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( testMobile.getOfficeId() );

        return officeDTO;
    }

    protected UserDTO testMobileToUserDTO(TestMobile testMobile) {
        if ( testMobile == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testMobile.getCreateById() );

        return userDTO;
    }

    protected UserDTO testMobileToUserDTO1(TestMobile testMobile) {
        if ( testMobile == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testMobile.getUpdateById() );

        return userDTO;
    }
}
