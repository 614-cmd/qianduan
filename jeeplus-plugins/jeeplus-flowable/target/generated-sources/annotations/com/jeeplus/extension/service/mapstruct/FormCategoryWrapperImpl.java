package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.extension.domain.FormCategory;
import com.jeeplus.extension.service.dto.FormCategoryDTO;
import com.jeeplus.extension.service.dto.FormDefinitionDTO;
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
public class FormCategoryWrapperImpl implements FormCategoryWrapper {

    @Override
    public List<FormCategory> toEntity(List<FormCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FormCategory> list = new ArrayList<FormCategory>( dtoList.size() );
        for ( FormCategoryDTO formCategoryDTO : dtoList ) {
            list.add( toEntity( formCategoryDTO ) );
        }

        return list;
    }

    @Override
    public List<FormCategoryDTO> toDTO(List<FormCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FormCategoryDTO> list = new ArrayList<FormCategoryDTO>( entityList.size() );
        for ( FormCategory formCategory : entityList ) {
            list.add( toDTO( formCategory ) );
        }

        return list;
    }

    @Override
    public Page<FormCategory> toEntity(Page<FormCategoryDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<FormCategory> page1 = new Page<FormCategory>();

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
    public Page<FormCategoryDTO> toDTO(Page<FormCategory> page) {
        if ( page == null ) {
            return null;
        }

        Page<FormCategoryDTO> page1 = new Page<FormCategoryDTO>();

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
    public FormCategory toEntity(FormCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FormCategory formCategory = new FormCategory();

        formCategory.setParentId( dtoParentId( dto ) );
        formCategory.setCreateById( dtoCreateById( dto ) );
        formCategory.setUpdateById( dtoUpdateById( dto ) );
        formCategory.setId( dto.getId() );
        formCategory.setCreateTime( dto.getCreateTime() );
        formCategory.setUpdateTime( dto.getUpdateTime() );
        formCategory.setDelFlag( dto.getDelFlag() );
        formCategory.setParentIds( dto.getParentIds() );
        formCategory.setName( dto.getName() );
        formCategory.setSort( dto.getSort() );
        formCategory.setChildren( toEntity( dto.getChildren() ) );

        return formCategory;
    }

    @Override
    public FormCategoryDTO toDTO(FormCategory entity) {
        if ( entity == null ) {
            return null;
        }

        FormCategoryDTO formCategoryDTO = new FormCategoryDTO();

        formCategoryDTO.setParent( formCategoryToFormCategoryDTO( entity ) );
        formCategoryDTO.setCreateBy( formCategoryToUserDTO( entity ) );
        formCategoryDTO.setUpdateBy( formCategoryToUserDTO1( entity ) );
        formCategoryDTO.setId( entity.getId() );
        formCategoryDTO.setCreateTime( entity.getCreateTime() );
        formCategoryDTO.setUpdateTime( entity.getUpdateTime() );
        formCategoryDTO.setDelFlag( entity.getDelFlag() );
        formCategoryDTO.setChildren( toDTO( entity.getChildren() ) );
        formCategoryDTO.setParentIds( entity.getParentIds() );
        formCategoryDTO.setName( entity.getName() );
        formCategoryDTO.setSort( entity.getSort() );

        return formCategoryDTO;
    }

    @Override
    public FormCategory copyEntity(FormCategory entity) {
        if ( entity == null ) {
            return null;
        }

        FormCategory formCategory = new FormCategory();

        formCategory.setId( entity.getId() );
        formCategory.setCreateTime( entity.getCreateTime() );
        formCategory.setCreateById( entity.getCreateById() );
        formCategory.setUpdateTime( entity.getUpdateTime() );
        formCategory.setUpdateById( entity.getUpdateById() );
        formCategory.setDelFlag( entity.getDelFlag() );
        formCategory.setParentId( entity.getParentId() );
        formCategory.setParentIds( entity.getParentIds() );
        formCategory.setName( entity.getName() );
        formCategory.setSort( entity.getSort() );
        List<FormCategory> list = entity.getChildren();
        if ( list != null ) {
            formCategory.setChildren( new ArrayList<FormCategory>( list ) );
        }

        return formCategory;
    }

    @Override
    public FormCategoryDTO copyDTO(FormCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FormCategoryDTO formCategoryDTO = new FormCategoryDTO();

        formCategoryDTO.setId( dto.getId() );
        formCategoryDTO.setCreateTime( dto.getCreateTime() );
        formCategoryDTO.setCreateBy( dto.getCreateBy() );
        formCategoryDTO.setUpdateTime( dto.getUpdateTime() );
        formCategoryDTO.setUpdateBy( dto.getUpdateBy() );
        formCategoryDTO.setDelFlag( dto.getDelFlag() );
        formCategoryDTO.setParent( copyDTO( dto.getParent() ) );
        List<FormCategoryDTO> list = dto.getChildren();
        if ( list != null ) {
            formCategoryDTO.setChildren( new ArrayList<FormCategoryDTO>( list ) );
        }
        formCategoryDTO.setParentIds( dto.getParentIds() );
        formCategoryDTO.setName( dto.getName() );
        formCategoryDTO.setSort( dto.getSort() );
        List<FormDefinitionDTO> list1 = dto.getFormDefinitionList();
        if ( list1 != null ) {
            formCategoryDTO.setFormDefinitionList( new ArrayList<FormDefinitionDTO>( list1 ) );
        }

        return formCategoryDTO;
    }

    @Override
    public DragNode<FormCategory> toEntity(DragNode<FormCategoryDTO> dDragDTO) {
        if ( dDragDTO == null ) {
            return null;
        }

        DragNode<FormCategory> dragNode = new DragNode<FormCategory>();

        dragNode.setDraggingNode( toEntity( dDragDTO.getDraggingNode() ) );
        dragNode.setDropNode( toEntity( dDragDTO.getDropNode() ) );
        dragNode.setDropType( dDragDTO.getDropType() );

        return dragNode;
    }

    private String dtoParentId(FormCategoryDTO formCategoryDTO) {
        if ( formCategoryDTO == null ) {
            return null;
        }
        FormCategoryDTO parent = formCategoryDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(FormCategoryDTO formCategoryDTO) {
        if ( formCategoryDTO == null ) {
            return null;
        }
        UserDTO createBy = formCategoryDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(FormCategoryDTO formCategoryDTO) {
        if ( formCategoryDTO == null ) {
            return null;
        }
        UserDTO updateBy = formCategoryDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected FormCategoryDTO formCategoryToFormCategoryDTO(FormCategory formCategory) {
        if ( formCategory == null ) {
            return null;
        }

        FormCategoryDTO formCategoryDTO = new FormCategoryDTO();

        formCategoryDTO.setId( formCategory.getParentId() );

        return formCategoryDTO;
    }

    protected UserDTO formCategoryToUserDTO(FormCategory formCategory) {
        if ( formCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( formCategory.getCreateById() );

        return userDTO;
    }

    protected UserDTO formCategoryToUserDTO1(FormCategory formCategory) {
        if ( formCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( formCategory.getUpdateById() );

        return userDTO;
    }
}
