package com.jeeplus.office.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.office.domain.DocCategory;
import com.jeeplus.office.service.dto.DocCategoryDTO;
import com.jeeplus.office.service.dto.DocTemplateDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:32+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class DocCategoryWrapperImpl implements DocCategoryWrapper {

    @Override
    public List<DocCategory> toEntity(List<DocCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DocCategory> list = new ArrayList<DocCategory>( dtoList.size() );
        for ( DocCategoryDTO docCategoryDTO : dtoList ) {
            list.add( toEntity( docCategoryDTO ) );
        }

        return list;
    }

    @Override
    public List<DocCategoryDTO> toDTO(List<DocCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DocCategoryDTO> list = new ArrayList<DocCategoryDTO>( entityList.size() );
        for ( DocCategory docCategory : entityList ) {
            list.add( toDTO( docCategory ) );
        }

        return list;
    }

    @Override
    public Page<DocCategory> toEntity(Page<DocCategoryDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DocCategory> page1 = new Page<DocCategory>();

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
    public Page<DocCategoryDTO> toDTO(Page<DocCategory> page) {
        if ( page == null ) {
            return null;
        }

        Page<DocCategoryDTO> page1 = new Page<DocCategoryDTO>();

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
    public DocCategory toEntity(DocCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DocCategory docCategory = new DocCategory();

        docCategory.setParentId( dtoParentId( dto ) );
        docCategory.setCreateById( dtoCreateById( dto ) );
        docCategory.setUpdateById( dtoUpdateById( dto ) );
        docCategory.setId( dto.getId() );
        docCategory.setCreateTime( dto.getCreateTime() );
        docCategory.setUpdateTime( dto.getUpdateTime() );
        docCategory.setDelFlag( dto.getDelFlag() );
        docCategory.setParentIds( dto.getParentIds() );
        docCategory.setName( dto.getName() );
        docCategory.setSort( dto.getSort() );
        docCategory.setChildren( toEntity( dto.getChildren() ) );
        docCategory.setRemarks( dto.getRemarks() );

        return docCategory;
    }

    @Override
    public DocCategoryDTO toDTO(DocCategory entity) {
        if ( entity == null ) {
            return null;
        }

        DocCategoryDTO docCategoryDTO = new DocCategoryDTO();

        docCategoryDTO.setParent( docCategoryToDocCategoryDTO( entity ) );
        docCategoryDTO.setCreateBy( docCategoryToUserDTO( entity ) );
        docCategoryDTO.setUpdateBy( docCategoryToUserDTO1( entity ) );
        docCategoryDTO.setId( entity.getId() );
        docCategoryDTO.setCreateTime( entity.getCreateTime() );
        docCategoryDTO.setUpdateTime( entity.getUpdateTime() );
        docCategoryDTO.setDelFlag( entity.getDelFlag() );
        docCategoryDTO.setChildren( toDTO( entity.getChildren() ) );
        docCategoryDTO.setParentIds( entity.getParentIds() );
        docCategoryDTO.setName( entity.getName() );
        docCategoryDTO.setSort( entity.getSort() );
        docCategoryDTO.setRemarks( entity.getRemarks() );

        return docCategoryDTO;
    }

    @Override
    public DocCategory copyEntity(DocCategory entity) {
        if ( entity == null ) {
            return null;
        }

        DocCategory docCategory = new DocCategory();

        docCategory.setId( entity.getId() );
        docCategory.setCreateTime( entity.getCreateTime() );
        docCategory.setCreateById( entity.getCreateById() );
        docCategory.setUpdateTime( entity.getUpdateTime() );
        docCategory.setUpdateById( entity.getUpdateById() );
        docCategory.setDelFlag( entity.getDelFlag() );
        docCategory.setParentId( entity.getParentId() );
        docCategory.setParentIds( entity.getParentIds() );
        docCategory.setName( entity.getName() );
        docCategory.setSort( entity.getSort() );
        List<DocCategory> list = entity.getChildren();
        if ( list != null ) {
            docCategory.setChildren( new ArrayList<DocCategory>( list ) );
        }
        docCategory.setRemarks( entity.getRemarks() );

        return docCategory;
    }

    @Override
    public DocCategoryDTO copyDTO(DocCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DocCategoryDTO docCategoryDTO = new DocCategoryDTO();

        docCategoryDTO.setId( dto.getId() );
        docCategoryDTO.setCreateTime( dto.getCreateTime() );
        docCategoryDTO.setCreateBy( dto.getCreateBy() );
        docCategoryDTO.setUpdateTime( dto.getUpdateTime() );
        docCategoryDTO.setUpdateBy( dto.getUpdateBy() );
        docCategoryDTO.setDelFlag( dto.getDelFlag() );
        docCategoryDTO.setParent( copyDTO( dto.getParent() ) );
        List<DocCategoryDTO> list = dto.getChildren();
        if ( list != null ) {
            docCategoryDTO.setChildren( new ArrayList<DocCategoryDTO>( list ) );
        }
        docCategoryDTO.setParentIds( dto.getParentIds() );
        docCategoryDTO.setName( dto.getName() );
        docCategoryDTO.setSort( dto.getSort() );
        docCategoryDTO.setRemarks( dto.getRemarks() );
        List<DocTemplateDTO> list1 = dto.getDocTemplateDTOList();
        if ( list1 != null ) {
            docCategoryDTO.setDocTemplateDTOList( new ArrayList<DocTemplateDTO>( list1 ) );
        }

        return docCategoryDTO;
    }

    @Override
    public DragNode<DocCategory> toEntity(DragNode<DocCategoryDTO> dDragDTO) {
        if ( dDragDTO == null ) {
            return null;
        }

        DragNode<DocCategory> dragNode = new DragNode<DocCategory>();

        dragNode.setDraggingNode( toEntity( dDragDTO.getDraggingNode() ) );
        dragNode.setDropNode( toEntity( dDragDTO.getDropNode() ) );
        dragNode.setDropType( dDragDTO.getDropType() );

        return dragNode;
    }

    private String dtoParentId(DocCategoryDTO docCategoryDTO) {
        if ( docCategoryDTO == null ) {
            return null;
        }
        DocCategoryDTO parent = docCategoryDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(DocCategoryDTO docCategoryDTO) {
        if ( docCategoryDTO == null ) {
            return null;
        }
        UserDTO createBy = docCategoryDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(DocCategoryDTO docCategoryDTO) {
        if ( docCategoryDTO == null ) {
            return null;
        }
        UserDTO updateBy = docCategoryDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected DocCategoryDTO docCategoryToDocCategoryDTO(DocCategory docCategory) {
        if ( docCategory == null ) {
            return null;
        }

        DocCategoryDTO docCategoryDTO = new DocCategoryDTO();

        docCategoryDTO.setId( docCategory.getParentId() );

        return docCategoryDTO;
    }

    protected UserDTO docCategoryToUserDTO(DocCategory docCategory) {
        if ( docCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( docCategory.getCreateById() );

        return userDTO;
    }

    protected UserDTO docCategoryToUserDTO1(DocCategory docCategory) {
        if ( docCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( docCategory.getUpdateById() );

        return userDTO;
    }
}
