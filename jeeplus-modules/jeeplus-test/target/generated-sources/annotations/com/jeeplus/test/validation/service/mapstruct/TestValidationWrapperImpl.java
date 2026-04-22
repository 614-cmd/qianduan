package com.jeeplus.test.validation.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.validation.domain.TestValidation;
import com.jeeplus.test.validation.service.dto.TestValidationDTO;
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
public class TestValidationWrapperImpl implements TestValidationWrapper {

    @Override
    public TestValidation toEntity(TestValidationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestValidation testValidation = new TestValidation();

        testValidation.setCreateById( dtoCreateById( dto ) );
        testValidation.setUpdateById( dtoUpdateById( dto ) );
        testValidation.setId( dto.getId() );
        testValidation.setCreateTime( dto.getCreateTime() );
        testValidation.setUpdateTime( dto.getUpdateTime() );
        testValidation.setDelFlag( dto.getDelFlag() );
        testValidation.setNum( dto.getNum() );
        testValidation.setNum2( dto.getNum2() );
        testValidation.setStr( dto.getStr() );
        testValidation.setEmail( dto.getEmail() );
        testValidation.setUrl( dto.getUrl() );
        testValidation.setNewDate( dto.getNewDate() );
        testValidation.setRemarks( dto.getRemarks() );
        testValidation.setC1( dto.getC1() );
        testValidation.setC2( dto.getC2() );
        testValidation.setC3( dto.getC3() );
        testValidation.setC4( dto.getC4() );
        testValidation.setC5( dto.getC5() );
        testValidation.setC6( dto.getC6() );
        testValidation.setEn( dto.getEn() );
        testValidation.setZn( dto.getZn() );
        testValidation.setEnzn( dto.getEnzn() );

        return testValidation;
    }

    @Override
    public TestValidationDTO toDTO(TestValidation entity) {
        if ( entity == null ) {
            return null;
        }

        TestValidationDTO testValidationDTO = new TestValidationDTO();

        testValidationDTO.setCreateBy( testValidationToUserDTO( entity ) );
        testValidationDTO.setUpdateBy( testValidationToUserDTO1( entity ) );
        testValidationDTO.setId( entity.getId() );
        testValidationDTO.setCreateTime( entity.getCreateTime() );
        testValidationDTO.setUpdateTime( entity.getUpdateTime() );
        testValidationDTO.setDelFlag( entity.getDelFlag() );
        testValidationDTO.setNum( entity.getNum() );
        testValidationDTO.setNum2( entity.getNum2() );
        testValidationDTO.setStr( entity.getStr() );
        testValidationDTO.setEmail( entity.getEmail() );
        testValidationDTO.setUrl( entity.getUrl() );
        testValidationDTO.setNewDate( entity.getNewDate() );
        testValidationDTO.setRemarks( entity.getRemarks() );
        testValidationDTO.setC1( entity.getC1() );
        testValidationDTO.setC2( entity.getC2() );
        testValidationDTO.setC3( entity.getC3() );
        testValidationDTO.setC4( entity.getC4() );
        testValidationDTO.setC5( entity.getC5() );
        testValidationDTO.setC6( entity.getC6() );
        testValidationDTO.setEn( entity.getEn() );
        testValidationDTO.setZn( entity.getZn() );
        testValidationDTO.setEnzn( entity.getEnzn() );

        return testValidationDTO;
    }

    @Override
    public List<TestValidation> toEntity(List<TestValidationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestValidation> list = new ArrayList<TestValidation>( dtoList.size() );
        for ( TestValidationDTO testValidationDTO : dtoList ) {
            list.add( toEntity( testValidationDTO ) );
        }

        return list;
    }

    @Override
    public List<TestValidationDTO> toDTO(List<TestValidation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestValidationDTO> list = new ArrayList<TestValidationDTO>( entityList.size() );
        for ( TestValidation testValidation : entityList ) {
            list.add( toDTO( testValidation ) );
        }

        return list;
    }

    @Override
    public Page<TestValidation> toEntity(Page<TestValidationDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestValidation> page1 = new Page<TestValidation>();

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
    public Page<TestValidationDTO> toDTO(Page<TestValidation> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestValidationDTO> page1 = new Page<TestValidationDTO>();

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

    private String dtoCreateById(TestValidationDTO testValidationDTO) {
        if ( testValidationDTO == null ) {
            return null;
        }
        UserDTO createBy = testValidationDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestValidationDTO testValidationDTO) {
        if ( testValidationDTO == null ) {
            return null;
        }
        UserDTO updateBy = testValidationDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO testValidationToUserDTO(TestValidation testValidation) {
        if ( testValidation == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testValidation.getCreateById() );

        return userDTO;
    }

    protected UserDTO testValidationToUserDTO1(TestValidation testValidation) {
        if ( testValidation == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testValidation.getUpdateById() );

        return userDTO;
    }
}
