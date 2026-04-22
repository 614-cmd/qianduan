package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.FormRef;
import com.jeeplus.extension.service.dto.FormDataDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:39+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class FormRefWrapperImpl implements FormRefWrapper {

    @Override
    public FormRef toEntity(FormDataDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FormRef formRef = new FormRef();

        formRef.setCreateById( dtoCreateById( dto ) );
        formRef.setUpdateById( dtoUpdateById( dto ) );
        formRef.setId( dto.getId() );
        formRef.setCreateTime( dto.getCreateTime() );
        formRef.setUpdateTime( dto.getUpdateTime() );
        formRef.setDelFlag( dto.getDelFlag() );
        formRef.setProcDefKey( dto.getProcDefKey() );
        formRef.setProcDefId( dto.getProcDefId() );
        formRef.setProcInsId( dto.getProcInsId() );
        formRef.setTaskId( dto.getTaskId() );
        formRef.setBusinessTable( dto.getBusinessTable() );
        formRef.setBusinessId( dto.getBusinessId() );

        return formRef;
    }

    @Override
    public FormDataDTO toDTO(FormRef entity) {
        if ( entity == null ) {
            return null;
        }

        FormDataDTO formDataDTO = new FormDataDTO();

        formDataDTO.setCreateBy( formRefToUserDTO( entity ) );
        formDataDTO.setUpdateBy( formRefToUserDTO1( entity ) );
        formDataDTO.setId( entity.getId() );
        formDataDTO.setCreateTime( entity.getCreateTime() );
        formDataDTO.setUpdateTime( entity.getUpdateTime() );
        formDataDTO.setDelFlag( entity.getDelFlag() );
        formDataDTO.setProcDefKey( entity.getProcDefKey() );
        formDataDTO.setProcDefId( entity.getProcDefId() );
        formDataDTO.setProcInsId( entity.getProcInsId() );
        formDataDTO.setTaskId( entity.getTaskId() );
        formDataDTO.setBusinessTable( entity.getBusinessTable() );
        formDataDTO.setBusinessId( entity.getBusinessId() );

        return formDataDTO;
    }

    @Override
    public List<FormRef> toEntity(List<FormDataDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FormRef> list = new ArrayList<FormRef>( dtoList.size() );
        for ( FormDataDTO formDataDTO : dtoList ) {
            list.add( toEntity( formDataDTO ) );
        }

        return list;
    }

    @Override
    public List<FormDataDTO> toDTO(List<FormRef> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FormDataDTO> list = new ArrayList<FormDataDTO>( entityList.size() );
        for ( FormRef formRef : entityList ) {
            list.add( toDTO( formRef ) );
        }

        return list;
    }

    @Override
    public Page<FormRef> toEntity(Page<FormDataDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<FormRef> page1 = new Page<FormRef>();

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
    public Page<FormDataDTO> toDTO(Page<FormRef> page) {
        if ( page == null ) {
            return null;
        }

        Page<FormDataDTO> page1 = new Page<FormDataDTO>();

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

    private String dtoCreateById(FormDataDTO formDataDTO) {
        if ( formDataDTO == null ) {
            return null;
        }
        UserDTO createBy = formDataDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(FormDataDTO formDataDTO) {
        if ( formDataDTO == null ) {
            return null;
        }
        UserDTO updateBy = formDataDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO formRefToUserDTO(FormRef formRef) {
        if ( formRef == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( formRef.getCreateById() );

        return userDTO;
    }

    protected UserDTO formRefToUserDTO1(FormRef formRef) {
        if ( formRef == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( formRef.getUpdateById() );

        return userDTO;
    }
}
