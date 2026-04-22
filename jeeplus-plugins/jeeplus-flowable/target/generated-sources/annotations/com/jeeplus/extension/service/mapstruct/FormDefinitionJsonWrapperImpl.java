package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.FormDefinitionJson;
import com.jeeplus.extension.service.dto.FormDefinitionJsonDTO;
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
public class FormDefinitionJsonWrapperImpl implements FormDefinitionJsonWrapper {

    @Override
    public FormDefinitionJson toEntity(FormDefinitionJsonDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FormDefinitionJson formDefinitionJson = new FormDefinitionJson();

        formDefinitionJson.setCreateById( dtoCreateById( dto ) );
        formDefinitionJson.setUpdateById( dtoUpdateById( dto ) );
        formDefinitionJson.setId( dto.getId() );
        formDefinitionJson.setCreateTime( dto.getCreateTime() );
        formDefinitionJson.setUpdateTime( dto.getUpdateTime() );
        formDefinitionJson.setDelFlag( dto.getDelFlag() );
        formDefinitionJson.setJson( dto.getJson() );
        formDefinitionJson.setFormDefinitionId( dto.getFormDefinitionId() );
        formDefinitionJson.setJsonBody( dto.getJsonBody() );
        formDefinitionJson.setVersion( dto.getVersion() );
        formDefinitionJson.setStatus( dto.getStatus() );
        formDefinitionJson.setIsPrimary( dto.getIsPrimary() );

        return formDefinitionJson;
    }

    @Override
    public FormDefinitionJsonDTO toDTO(FormDefinitionJson entity) {
        if ( entity == null ) {
            return null;
        }

        FormDefinitionJsonDTO formDefinitionJsonDTO = new FormDefinitionJsonDTO();

        formDefinitionJsonDTO.setCreateBy( formDefinitionJsonToUserDTO( entity ) );
        formDefinitionJsonDTO.setUpdateBy( formDefinitionJsonToUserDTO1( entity ) );
        formDefinitionJsonDTO.setId( entity.getId() );
        formDefinitionJsonDTO.setCreateTime( entity.getCreateTime() );
        formDefinitionJsonDTO.setUpdateTime( entity.getUpdateTime() );
        formDefinitionJsonDTO.setDelFlag( entity.getDelFlag() );
        formDefinitionJsonDTO.setJson( entity.getJson() );
        formDefinitionJsonDTO.setFormDefinitionId( entity.getFormDefinitionId() );
        formDefinitionJsonDTO.setJsonBody( entity.getJsonBody() );
        formDefinitionJsonDTO.setVersion( entity.getVersion() );
        formDefinitionJsonDTO.setStatus( entity.getStatus() );
        formDefinitionJsonDTO.setIsPrimary( entity.getIsPrimary() );

        return formDefinitionJsonDTO;
    }

    @Override
    public List<FormDefinitionJson> toEntity(List<FormDefinitionJsonDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FormDefinitionJson> list = new ArrayList<FormDefinitionJson>( dtoList.size() );
        for ( FormDefinitionJsonDTO formDefinitionJsonDTO : dtoList ) {
            list.add( toEntity( formDefinitionJsonDTO ) );
        }

        return list;
    }

    @Override
    public List<FormDefinitionJsonDTO> toDTO(List<FormDefinitionJson> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FormDefinitionJsonDTO> list = new ArrayList<FormDefinitionJsonDTO>( entityList.size() );
        for ( FormDefinitionJson formDefinitionJson : entityList ) {
            list.add( toDTO( formDefinitionJson ) );
        }

        return list;
    }

    @Override
    public Page<FormDefinitionJson> toEntity(Page<FormDefinitionJsonDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<FormDefinitionJson> page1 = new Page<FormDefinitionJson>();

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
    public Page<FormDefinitionJsonDTO> toDTO(Page<FormDefinitionJson> page) {
        if ( page == null ) {
            return null;
        }

        Page<FormDefinitionJsonDTO> page1 = new Page<FormDefinitionJsonDTO>();

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

    private String dtoCreateById(FormDefinitionJsonDTO formDefinitionJsonDTO) {
        if ( formDefinitionJsonDTO == null ) {
            return null;
        }
        UserDTO createBy = formDefinitionJsonDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(FormDefinitionJsonDTO formDefinitionJsonDTO) {
        if ( formDefinitionJsonDTO == null ) {
            return null;
        }
        UserDTO updateBy = formDefinitionJsonDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO formDefinitionJsonToUserDTO(FormDefinitionJson formDefinitionJson) {
        if ( formDefinitionJson == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( formDefinitionJson.getCreateById() );

        return userDTO;
    }

    protected UserDTO formDefinitionJsonToUserDTO1(FormDefinitionJson formDefinitionJson) {
        if ( formDefinitionJson == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( formDefinitionJson.getUpdateById() );

        return userDTO;
    }
}
