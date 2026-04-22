package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.database.datalink.domain.DataSource;
import com.jeeplus.extension.domain.FormDefinition;
import com.jeeplus.extension.service.dto.FormCategoryDTO;
import com.jeeplus.extension.service.dto.FormDefinitionDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:38+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class FormDefinitionWrapperImpl implements FormDefinitionWrapper {

    @Override
    public List<FormDefinition> toEntity(List<FormDefinitionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FormDefinition> list = new ArrayList<FormDefinition>( dtoList.size() );
        for ( FormDefinitionDTO formDefinitionDTO : dtoList ) {
            list.add( toEntity( formDefinitionDTO ) );
        }

        return list;
    }

    @Override
    public List<FormDefinitionDTO> toDTO(List<FormDefinition> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FormDefinitionDTO> list = new ArrayList<FormDefinitionDTO>( entityList.size() );
        for ( FormDefinition formDefinition : entityList ) {
            list.add( toDTO( formDefinition ) );
        }

        return list;
    }

    @Override
    public Page<FormDefinition> toEntity(Page<FormDefinitionDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<FormDefinition> page1 = new Page<FormDefinition>();

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
    public Page<FormDefinitionDTO> toDTO(Page<FormDefinition> page) {
        if ( page == null ) {
            return null;
        }

        Page<FormDefinitionDTO> page1 = new Page<FormDefinitionDTO>();

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
    public FormDefinition toEntity(FormDefinitionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FormDefinition formDefinition = new FormDefinition();

        formDefinition.setCreateById( dtoCreateById( dto ) );
        formDefinition.setUpdateById( dtoUpdateById( dto ) );
        formDefinition.setDataSourceId( dtoDataSourceId( dto ) );
        formDefinition.setCategoryId( dtoCategoryId( dto ) );
        formDefinition.setId( dto.getId() );
        formDefinition.setCreateTime( dto.getCreateTime() );
        formDefinition.setUpdateTime( dto.getUpdateTime() );
        formDefinition.setDelFlag( dto.getDelFlag() );
        formDefinition.setName( dto.getName() );
        formDefinition.setIsRelatedTable( dto.getIsRelatedTable() );
        formDefinition.setAutoCreate( dto.getAutoCreate() );
        formDefinition.setTableName( dto.getTableName() );

        return formDefinition;
    }

    @Override
    public FormDefinitionDTO toDTO(FormDefinition entity) {
        if ( entity == null ) {
            return null;
        }

        FormDefinitionDTO formDefinitionDTO = new FormDefinitionDTO();

        formDefinitionDTO.setCreateBy( formDefinitionToUserDTO( entity ) );
        formDefinitionDTO.setUpdateBy( formDefinitionToUserDTO1( entity ) );
        formDefinitionDTO.setDataSource( formDefinitionToDataSource( entity ) );
        formDefinitionDTO.setCategory( formDefinitionToFormCategoryDTO( entity ) );
        formDefinitionDTO.setId( entity.getId() );
        formDefinitionDTO.setCreateTime( entity.getCreateTime() );
        formDefinitionDTO.setUpdateTime( entity.getUpdateTime() );
        formDefinitionDTO.setDelFlag( entity.getDelFlag() );
        formDefinitionDTO.setName( entity.getName() );
        formDefinitionDTO.setIsRelatedTable( entity.getIsRelatedTable() );
        formDefinitionDTO.setAutoCreate( entity.getAutoCreate() );
        formDefinitionDTO.setTableName( entity.getTableName() );

        return formDefinitionDTO;
    }

    private String dtoCreateById(FormDefinitionDTO formDefinitionDTO) {
        if ( formDefinitionDTO == null ) {
            return null;
        }
        UserDTO createBy = formDefinitionDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(FormDefinitionDTO formDefinitionDTO) {
        if ( formDefinitionDTO == null ) {
            return null;
        }
        UserDTO updateBy = formDefinitionDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoDataSourceId(FormDefinitionDTO formDefinitionDTO) {
        if ( formDefinitionDTO == null ) {
            return null;
        }
        DataSource dataSource = formDefinitionDTO.getDataSource();
        if ( dataSource == null ) {
            return null;
        }
        String id = dataSource.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCategoryId(FormDefinitionDTO formDefinitionDTO) {
        if ( formDefinitionDTO == null ) {
            return null;
        }
        FormCategoryDTO category = formDefinitionDTO.getCategory();
        if ( category == null ) {
            return null;
        }
        String id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO formDefinitionToUserDTO(FormDefinition formDefinition) {
        if ( formDefinition == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( formDefinition.getCreateById() );

        return userDTO;
    }

    protected UserDTO formDefinitionToUserDTO1(FormDefinition formDefinition) {
        if ( formDefinition == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( formDefinition.getUpdateById() );

        return userDTO;
    }

    protected DataSource formDefinitionToDataSource(FormDefinition formDefinition) {
        if ( formDefinition == null ) {
            return null;
        }

        DataSource dataSource = new DataSource();

        dataSource.setId( formDefinition.getDataSourceId() );

        return dataSource;
    }

    protected FormCategoryDTO formDefinitionToFormCategoryDTO(FormDefinition formDefinition) {
        if ( formDefinition == null ) {
            return null;
        }

        FormCategoryDTO formCategoryDTO = new FormCategoryDTO();

        formCategoryDTO.setId( formDefinition.getCategoryId() );

        return formCategoryDTO;
    }
}
