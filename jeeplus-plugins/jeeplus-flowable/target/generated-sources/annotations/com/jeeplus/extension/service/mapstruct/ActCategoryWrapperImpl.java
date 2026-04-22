package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.extension.domain.ActCategory;
import com.jeeplus.extension.service.dto.ActCategoryDTO;
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
public class ActCategoryWrapperImpl implements ActCategoryWrapper {

    @Override
    public List<ActCategory> toEntity(List<ActCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ActCategory> list = new ArrayList<ActCategory>( dtoList.size() );
        for ( ActCategoryDTO actCategoryDTO : dtoList ) {
            list.add( toEntity( actCategoryDTO ) );
        }

        return list;
    }

    @Override
    public List<ActCategoryDTO> toDTO(List<ActCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ActCategoryDTO> list = new ArrayList<ActCategoryDTO>( entityList.size() );
        for ( ActCategory actCategory : entityList ) {
            list.add( toDTO( actCategory ) );
        }

        return list;
    }

    @Override
    public Page<ActCategory> toEntity(Page<ActCategoryDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<ActCategory> page1 = new Page<ActCategory>();

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
    public Page<ActCategoryDTO> toDTO(Page<ActCategory> page) {
        if ( page == null ) {
            return null;
        }

        Page<ActCategoryDTO> page1 = new Page<ActCategoryDTO>();

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
    public ActCategory toEntity(ActCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ActCategory actCategory = new ActCategory();

        actCategory.setParentId( dtoParentId( dto ) );
        actCategory.setCreateById( dtoCreateById( dto ) );
        actCategory.setUpdateById( dtoUpdateById( dto ) );
        actCategory.setId( dto.getId() );
        actCategory.setCreateTime( dto.getCreateTime() );
        actCategory.setUpdateTime( dto.getUpdateTime() );
        actCategory.setDelFlag( dto.getDelFlag() );
        actCategory.setParentIds( dto.getParentIds() );
        actCategory.setName( dto.getName() );
        actCategory.setSort( dto.getSort() );
        actCategory.setChildren( toEntity( dto.getChildren() ) );
        actCategory.setRemarks( dto.getRemarks() );

        return actCategory;
    }

    @Override
    public ActCategoryDTO toDTO(ActCategory entity) {
        if ( entity == null ) {
            return null;
        }

        ActCategoryDTO actCategoryDTO = new ActCategoryDTO();

        actCategoryDTO.setParent( actCategoryToActCategoryDTO( entity ) );
        actCategoryDTO.setCreateBy( actCategoryToUserDTO( entity ) );
        actCategoryDTO.setUpdateBy( actCategoryToUserDTO1( entity ) );
        actCategoryDTO.setId( entity.getId() );
        actCategoryDTO.setCreateTime( entity.getCreateTime() );
        actCategoryDTO.setUpdateTime( entity.getUpdateTime() );
        actCategoryDTO.setDelFlag( entity.getDelFlag() );
        actCategoryDTO.setChildren( toDTO( entity.getChildren() ) );
        actCategoryDTO.setParentIds( entity.getParentIds() );
        actCategoryDTO.setName( entity.getName() );
        actCategoryDTO.setSort( entity.getSort() );
        actCategoryDTO.setRemarks( entity.getRemarks() );

        return actCategoryDTO;
    }

    @Override
    public ActCategory copyEntity(ActCategory entity) {
        if ( entity == null ) {
            return null;
        }

        ActCategory actCategory = new ActCategory();

        actCategory.setId( entity.getId() );
        actCategory.setCreateTime( entity.getCreateTime() );
        actCategory.setCreateById( entity.getCreateById() );
        actCategory.setUpdateTime( entity.getUpdateTime() );
        actCategory.setUpdateById( entity.getUpdateById() );
        actCategory.setDelFlag( entity.getDelFlag() );
        actCategory.setParentId( entity.getParentId() );
        actCategory.setParentIds( entity.getParentIds() );
        actCategory.setName( entity.getName() );
        actCategory.setSort( entity.getSort() );
        List<ActCategory> list = entity.getChildren();
        if ( list != null ) {
            actCategory.setChildren( new ArrayList<ActCategory>( list ) );
        }
        actCategory.setRemarks( entity.getRemarks() );

        return actCategory;
    }

    @Override
    public ActCategoryDTO copyDTO(ActCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ActCategoryDTO actCategoryDTO = new ActCategoryDTO();

        actCategoryDTO.setId( dto.getId() );
        actCategoryDTO.setCreateTime( dto.getCreateTime() );
        actCategoryDTO.setCreateBy( dto.getCreateBy() );
        actCategoryDTO.setUpdateTime( dto.getUpdateTime() );
        actCategoryDTO.setUpdateBy( dto.getUpdateBy() );
        actCategoryDTO.setDelFlag( dto.getDelFlag() );
        actCategoryDTO.setParent( copyDTO( dto.getParent() ) );
        List<ActCategoryDTO> list = dto.getChildren();
        if ( list != null ) {
            actCategoryDTO.setChildren( new ArrayList<ActCategoryDTO>( list ) );
        }
        actCategoryDTO.setParentIds( dto.getParentIds() );
        actCategoryDTO.setName( dto.getName() );
        actCategoryDTO.setSort( dto.getSort() );
        actCategoryDTO.setRemarks( dto.getRemarks() );

        return actCategoryDTO;
    }

    @Override
    public DragNode<ActCategory> toEntity(DragNode<ActCategoryDTO> dDragDTO) {
        if ( dDragDTO == null ) {
            return null;
        }

        DragNode<ActCategory> dragNode = new DragNode<ActCategory>();

        dragNode.setDraggingNode( toEntity( dDragDTO.getDraggingNode() ) );
        dragNode.setDropNode( toEntity( dDragDTO.getDropNode() ) );
        dragNode.setDropType( dDragDTO.getDropType() );

        return dragNode;
    }

    private String dtoParentId(ActCategoryDTO actCategoryDTO) {
        if ( actCategoryDTO == null ) {
            return null;
        }
        ActCategoryDTO parent = actCategoryDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(ActCategoryDTO actCategoryDTO) {
        if ( actCategoryDTO == null ) {
            return null;
        }
        UserDTO createBy = actCategoryDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(ActCategoryDTO actCategoryDTO) {
        if ( actCategoryDTO == null ) {
            return null;
        }
        UserDTO updateBy = actCategoryDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected ActCategoryDTO actCategoryToActCategoryDTO(ActCategory actCategory) {
        if ( actCategory == null ) {
            return null;
        }

        ActCategoryDTO actCategoryDTO = new ActCategoryDTO();

        actCategoryDTO.setId( actCategory.getParentId() );

        return actCategoryDTO;
    }

    protected UserDTO actCategoryToUserDTO(ActCategory actCategory) {
        if ( actCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( actCategory.getCreateById() );

        return userDTO;
    }

    protected UserDTO actCategoryToUserDTO1(ActCategory actCategory) {
        if ( actCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( actCategory.getUpdateById() );

        return userDTO;
    }
}
