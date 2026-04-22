package com.jeeplus.form.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.database.datalink.domain.DataSource;
import com.jeeplus.form.domain.Form;
import com.jeeplus.form.service.dto.FormDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:35+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class FormWrapperImpl implements FormWrapper {

    @Override
    public List<Form> toEntity(List<FormDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Form> list = new ArrayList<Form>( dtoList.size() );
        for ( FormDTO formDTO : dtoList ) {
            list.add( toEntity( formDTO ) );
        }

        return list;
    }

    @Override
    public List<FormDTO> toDTO(List<Form> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FormDTO> list = new ArrayList<FormDTO>( entityList.size() );
        for ( Form form : entityList ) {
            list.add( toDTO( form ) );
        }

        return list;
    }

    @Override
    public Page<Form> toEntity(Page<FormDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Form> page1 = new Page<Form>();

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
    public Page<FormDTO> toDTO(Page<Form> page) {
        if ( page == null ) {
            return null;
        }

        Page<FormDTO> page1 = new Page<FormDTO>();

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
    public Form toEntity(FormDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Form form = new Form();

        form.setCreateById( dtoCreateById( dto ) );
        form.setUpdateById( dtoUpdateById( dto ) );
        form.setDataSourceId( dtoDataSourceId( dto ) );
        form.setId( dto.getId() );
        form.setCreateTime( dto.getCreateTime() );
        form.setUpdateTime( dto.getUpdateTime() );
        form.setDelFlag( dto.getDelFlag() );
        form.setCode( dto.getCode() );
        form.setAutoCreate( dto.getAutoCreate() );
        form.setName( dto.getName() );
        form.setTableName( dto.getTableName() );
        form.setSource( dto.getSource() );
        form.setVersion( dto.getVersion() );
        form.setRemarks( dto.getRemarks() );

        return form;
    }

    @Override
    public FormDTO toDTO(Form entity) {
        if ( entity == null ) {
            return null;
        }

        FormDTO formDTO = new FormDTO();

        formDTO.setCreateBy( formToUserDTO( entity ) );
        formDTO.setUpdateBy( formToUserDTO1( entity ) );
        formDTO.setDataSource( formToDataSource( entity ) );
        formDTO.setId( entity.getId() );
        formDTO.setCreateTime( entity.getCreateTime() );
        formDTO.setUpdateTime( entity.getUpdateTime() );
        formDTO.setDelFlag( entity.getDelFlag() );
        formDTO.setCode( entity.getCode() );
        formDTO.setAutoCreate( entity.getAutoCreate() );
        formDTO.setName( entity.getName() );
        formDTO.setTableName( entity.getTableName() );
        formDTO.setSource( entity.getSource() );
        formDTO.setVersion( entity.getVersion() );
        formDTO.setRemarks( entity.getRemarks() );

        return formDTO;
    }

    private String dtoCreateById(FormDTO formDTO) {
        if ( formDTO == null ) {
            return null;
        }
        UserDTO createBy = formDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(FormDTO formDTO) {
        if ( formDTO == null ) {
            return null;
        }
        UserDTO updateBy = formDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoDataSourceId(FormDTO formDTO) {
        if ( formDTO == null ) {
            return null;
        }
        DataSource dataSource = formDTO.getDataSource();
        if ( dataSource == null ) {
            return null;
        }
        String id = dataSource.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO formToUserDTO(Form form) {
        if ( form == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( form.getCreateById() );

        return userDTO;
    }

    protected UserDTO formToUserDTO1(Form form) {
        if ( form == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( form.getUpdateById() );

        return userDTO;
    }

    protected DataSource formToDataSource(Form form) {
        if ( form == null ) {
            return null;
        }

        DataSource dataSource = new DataSource();

        dataSource.setId( form.getDataSourceId() );

        return dataSource;
    }
}
