package com.jeeplus.test.one.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.OfficeDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.one.domain.TestFormLeave;
import com.jeeplus.test.one.service.dto.TestFormLeaveDTO;
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
public class TestFormLeaveWrapperImpl implements TestFormLeaveWrapper {

    @Override
    public List<TestFormLeave> toEntity(List<TestFormLeaveDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestFormLeave> list = new ArrayList<TestFormLeave>( dtoList.size() );
        for ( TestFormLeaveDTO testFormLeaveDTO : dtoList ) {
            list.add( toEntity( testFormLeaveDTO ) );
        }

        return list;
    }

    @Override
    public List<TestFormLeaveDTO> toDTO(List<TestFormLeave> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestFormLeaveDTO> list = new ArrayList<TestFormLeaveDTO>( entityList.size() );
        for ( TestFormLeave testFormLeave : entityList ) {
            list.add( toDTO( testFormLeave ) );
        }

        return list;
    }

    @Override
    public Page<TestFormLeave> toEntity(Page<TestFormLeaveDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestFormLeave> page1 = new Page<TestFormLeave>();

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
    public Page<TestFormLeaveDTO> toDTO(Page<TestFormLeave> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestFormLeaveDTO> page1 = new Page<TestFormLeaveDTO>();

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
    public TestFormLeave toEntity(TestFormLeaveDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestFormLeave testFormLeave = new TestFormLeave();

        testFormLeave.setOfficeId( dtoOfficeId( dto ) );
        testFormLeave.setUserId( dtoUserId( dto ) );
        testFormLeave.setCreateById( dtoCreateById( dto ) );
        testFormLeave.setUpdateById( dtoUpdateById( dto ) );
        testFormLeave.setId( dto.getId() );
        testFormLeave.setCreateTime( dto.getCreateTime() );
        testFormLeave.setUpdateTime( dto.getUpdateTime() );
        testFormLeave.setDelFlag( dto.getDelFlag() );
        testFormLeave.setArea( dto.getArea() );
        testFormLeave.setBeginDate( dto.getBeginDate() );
        testFormLeave.setEndDate( dto.getEndDate() );

        return testFormLeave;
    }

    @Override
    public TestFormLeaveDTO toDTO(TestFormLeave entity) {
        if ( entity == null ) {
            return null;
        }

        TestFormLeaveDTO testFormLeaveDTO = new TestFormLeaveDTO();

        testFormLeaveDTO.setOffice( testFormLeaveToOfficeDTO( entity ) );
        testFormLeaveDTO.setUser( testFormLeaveToUserDTO( entity ) );
        testFormLeaveDTO.setCreateBy( testFormLeaveToUserDTO1( entity ) );
        testFormLeaveDTO.setUpdateBy( testFormLeaveToUserDTO2( entity ) );
        testFormLeaveDTO.setId( entity.getId() );
        testFormLeaveDTO.setCreateTime( entity.getCreateTime() );
        testFormLeaveDTO.setUpdateTime( entity.getUpdateTime() );
        testFormLeaveDTO.setDelFlag( entity.getDelFlag() );
        testFormLeaveDTO.setArea( entity.getArea() );
        testFormLeaveDTO.setBeginDate( entity.getBeginDate() );
        testFormLeaveDTO.setEndDate( entity.getEndDate() );

        return testFormLeaveDTO;
    }

    private String dtoOfficeId(TestFormLeaveDTO testFormLeaveDTO) {
        if ( testFormLeaveDTO == null ) {
            return null;
        }
        OfficeDTO office = testFormLeaveDTO.getOffice();
        if ( office == null ) {
            return null;
        }
        String id = office.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUserId(TestFormLeaveDTO testFormLeaveDTO) {
        if ( testFormLeaveDTO == null ) {
            return null;
        }
        UserDTO user = testFormLeaveDTO.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestFormLeaveDTO testFormLeaveDTO) {
        if ( testFormLeaveDTO == null ) {
            return null;
        }
        UserDTO createBy = testFormLeaveDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestFormLeaveDTO testFormLeaveDTO) {
        if ( testFormLeaveDTO == null ) {
            return null;
        }
        UserDTO updateBy = testFormLeaveDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected OfficeDTO testFormLeaveToOfficeDTO(TestFormLeave testFormLeave) {
        if ( testFormLeave == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( testFormLeave.getOfficeId() );

        return officeDTO;
    }

    protected UserDTO testFormLeaveToUserDTO(TestFormLeave testFormLeave) {
        if ( testFormLeave == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testFormLeave.getUserId() );

        return userDTO;
    }

    protected UserDTO testFormLeaveToUserDTO1(TestFormLeave testFormLeave) {
        if ( testFormLeave == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testFormLeave.getCreateById() );

        return userDTO;
    }

    protected UserDTO testFormLeaveToUserDTO2(TestFormLeave testFormLeave) {
        if ( testFormLeave == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testFormLeave.getUpdateById() );

        return userDTO;
    }
}
