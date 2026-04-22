package com.jeeplus.test.activiti.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.OfficeDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.activiti.domain.TestActivitiAudit;
import com.jeeplus.test.activiti.service.dto.TestActivitiAuditDTO;
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
public class TestActivitiAuditWrapperImpl implements TestActivitiAuditWrapper {

    @Override
    public List<TestActivitiAudit> toEntity(List<TestActivitiAuditDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestActivitiAudit> list = new ArrayList<TestActivitiAudit>( dtoList.size() );
        for ( TestActivitiAuditDTO testActivitiAuditDTO : dtoList ) {
            list.add( toEntity( testActivitiAuditDTO ) );
        }

        return list;
    }

    @Override
    public List<TestActivitiAuditDTO> toDTO(List<TestActivitiAudit> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestActivitiAuditDTO> list = new ArrayList<TestActivitiAuditDTO>( entityList.size() );
        for ( TestActivitiAudit testActivitiAudit : entityList ) {
            list.add( toDTO( testActivitiAudit ) );
        }

        return list;
    }

    @Override
    public Page<TestActivitiAudit> toEntity(Page<TestActivitiAuditDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestActivitiAudit> page1 = new Page<TestActivitiAudit>();

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
    public Page<TestActivitiAuditDTO> toDTO(Page<TestActivitiAudit> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestActivitiAuditDTO> page1 = new Page<TestActivitiAuditDTO>();

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
    public TestActivitiAudit toEntity(TestActivitiAuditDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestActivitiAudit testActivitiAudit = new TestActivitiAudit();

        testActivitiAudit.setUserId( dtoUserId( dto ) );
        testActivitiAudit.setOfficeId( dtoOfficeId( dto ) );
        testActivitiAudit.setCreateById( dtoCreateById( dto ) );
        testActivitiAudit.setUpdateById( dtoUpdateById( dto ) );
        testActivitiAudit.setId( dto.getId() );
        testActivitiAudit.setCreateTime( dto.getCreateTime() );
        testActivitiAudit.setUpdateTime( dto.getUpdateTime() );
        testActivitiAudit.setDelFlag( dto.getDelFlag() );
        testActivitiAudit.setProcInsId( dto.getProcInsId() );
        testActivitiAudit.setPost( dto.getPost() );
        testActivitiAudit.setSex( dto.getSex() );
        testActivitiAudit.setContent( dto.getContent() );
        testActivitiAudit.setOldSalray( dto.getOldSalray() );
        testActivitiAudit.setNewSalary( dto.getNewSalary() );
        testActivitiAudit.setExeDate( dto.getExeDate() );
        testActivitiAudit.setRemarks( dto.getRemarks() );

        return testActivitiAudit;
    }

    @Override
    public TestActivitiAuditDTO toDTO(TestActivitiAudit entity) {
        if ( entity == null ) {
            return null;
        }

        TestActivitiAuditDTO testActivitiAuditDTO = new TestActivitiAuditDTO();

        testActivitiAuditDTO.setUser( testActivitiAuditToUserDTO( entity ) );
        testActivitiAuditDTO.setOffice( testActivitiAuditToOfficeDTO( entity ) );
        testActivitiAuditDTO.setCreateBy( testActivitiAuditToUserDTO1( entity ) );
        testActivitiAuditDTO.setUpdateBy( testActivitiAuditToUserDTO2( entity ) );
        testActivitiAuditDTO.setId( entity.getId() );
        testActivitiAuditDTO.setCreateTime( entity.getCreateTime() );
        testActivitiAuditDTO.setUpdateTime( entity.getUpdateTime() );
        testActivitiAuditDTO.setDelFlag( entity.getDelFlag() );
        testActivitiAuditDTO.setProcInsId( entity.getProcInsId() );
        testActivitiAuditDTO.setPost( entity.getPost() );
        testActivitiAuditDTO.setSex( entity.getSex() );
        testActivitiAuditDTO.setContent( entity.getContent() );
        testActivitiAuditDTO.setOldSalray( entity.getOldSalray() );
        testActivitiAuditDTO.setNewSalary( entity.getNewSalary() );
        testActivitiAuditDTO.setExeDate( entity.getExeDate() );
        testActivitiAuditDTO.setRemarks( entity.getRemarks() );

        return testActivitiAuditDTO;
    }

    private String dtoUserId(TestActivitiAuditDTO testActivitiAuditDTO) {
        if ( testActivitiAuditDTO == null ) {
            return null;
        }
        UserDTO user = testActivitiAuditDTO.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoOfficeId(TestActivitiAuditDTO testActivitiAuditDTO) {
        if ( testActivitiAuditDTO == null ) {
            return null;
        }
        OfficeDTO office = testActivitiAuditDTO.getOffice();
        if ( office == null ) {
            return null;
        }
        String id = office.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestActivitiAuditDTO testActivitiAuditDTO) {
        if ( testActivitiAuditDTO == null ) {
            return null;
        }
        UserDTO createBy = testActivitiAuditDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestActivitiAuditDTO testActivitiAuditDTO) {
        if ( testActivitiAuditDTO == null ) {
            return null;
        }
        UserDTO updateBy = testActivitiAuditDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO testActivitiAuditToUserDTO(TestActivitiAudit testActivitiAudit) {
        if ( testActivitiAudit == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testActivitiAudit.getUserId() );

        return userDTO;
    }

    protected OfficeDTO testActivitiAuditToOfficeDTO(TestActivitiAudit testActivitiAudit) {
        if ( testActivitiAudit == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( testActivitiAudit.getOfficeId() );

        return officeDTO;
    }

    protected UserDTO testActivitiAuditToUserDTO1(TestActivitiAudit testActivitiAudit) {
        if ( testActivitiAudit == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testActivitiAudit.getCreateById() );

        return userDTO;
    }

    protected UserDTO testActivitiAuditToUserDTO2(TestActivitiAudit testActivitiAudit) {
        if ( testActivitiAudit == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testActivitiAudit.getUpdateById() );

        return userDTO;
    }
}
