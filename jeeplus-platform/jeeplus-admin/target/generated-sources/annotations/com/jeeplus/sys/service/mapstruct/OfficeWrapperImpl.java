package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.sys.domain.Office;
import com.jeeplus.sys.service.dto.OfficeDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:24+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class OfficeWrapperImpl implements OfficeWrapper {

    @Override
    public List<Office> toEntity(List<OfficeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Office> list = new ArrayList<Office>( dtoList.size() );
        for ( OfficeDTO officeDTO : dtoList ) {
            list.add( toEntity( officeDTO ) );
        }

        return list;
    }

    @Override
    public List<OfficeDTO> toDTO(List<Office> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OfficeDTO> list = new ArrayList<OfficeDTO>( entityList.size() );
        for ( Office office : entityList ) {
            list.add( toDTO( office ) );
        }

        return list;
    }

    @Override
    public Page<Office> toEntity(Page<OfficeDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Office> page1 = new Page<Office>();

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
    public Page<OfficeDTO> toDTO(Page<Office> page) {
        if ( page == null ) {
            return null;
        }

        Page<OfficeDTO> page1 = new Page<OfficeDTO>();

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
    public Office copyEntity(Office entity) {
        if ( entity == null ) {
            return null;
        }

        Office office = new Office();

        office.setId( entity.getId() );
        office.setCreateTime( entity.getCreateTime() );
        office.setCreateById( entity.getCreateById() );
        office.setUpdateTime( entity.getUpdateTime() );
        office.setUpdateById( entity.getUpdateById() );
        office.setDelFlag( entity.getDelFlag() );
        office.setParentId( entity.getParentId() );
        office.setParentIds( entity.getParentIds() );
        office.setName( entity.getName() );
        office.setSort( entity.getSort() );
        List<Office> list = entity.getChildren();
        if ( list != null ) {
            office.setChildren( new ArrayList<Office>( list ) );
        }
        office.setArea( entity.getArea() );
        office.setCode( entity.getCode() );
        office.setType( entity.getType() );
        office.setGrade( entity.getGrade() );
        office.setAddress( entity.getAddress() );
        office.setZipCode( entity.getZipCode() );
        office.setMaster( entity.getMaster() );
        office.setPhone( entity.getPhone() );
        office.setFax( entity.getFax() );
        office.setEmail( entity.getEmail() );
        office.setUseable( entity.getUseable() );
        office.setRemarks( entity.getRemarks() );

        return office;
    }

    @Override
    public OfficeDTO copyDTO(OfficeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( dto.getId() );
        officeDTO.setCreateTime( dto.getCreateTime() );
        officeDTO.setCreateBy( dto.getCreateBy() );
        officeDTO.setUpdateTime( dto.getUpdateTime() );
        officeDTO.setUpdateBy( dto.getUpdateBy() );
        officeDTO.setDelFlag( dto.getDelFlag() );
        officeDTO.setParent( copyDTO( dto.getParent() ) );
        List<OfficeDTO> list = dto.getChildren();
        if ( list != null ) {
            officeDTO.setChildren( new ArrayList<OfficeDTO>( list ) );
        }
        officeDTO.setParentIds( dto.getParentIds() );
        officeDTO.setName( dto.getName() );
        officeDTO.setSort( dto.getSort() );
        officeDTO.setArea( dto.getArea() );
        officeDTO.setCode( dto.getCode() );
        officeDTO.setType( dto.getType() );
        officeDTO.setGrade( dto.getGrade() );
        officeDTO.setAddress( dto.getAddress() );
        officeDTO.setZipCode( dto.getZipCode() );
        officeDTO.setMaster( dto.getMaster() );
        officeDTO.setPhone( dto.getPhone() );
        officeDTO.setFax( dto.getFax() );
        officeDTO.setEmail( dto.getEmail() );
        officeDTO.setUseable( dto.getUseable() );
        officeDTO.setRemarks( dto.getRemarks() );
        List<String> list1 = dto.getChildDeptList();
        if ( list1 != null ) {
            officeDTO.setChildDeptList( new ArrayList<String>( list1 ) );
        }
        officeDTO.setDisabled( dto.isDisabled() );

        return officeDTO;
    }

    @Override
    public DragNode<Office> toEntity(DragNode<OfficeDTO> dDragDTO) {
        if ( dDragDTO == null ) {
            return null;
        }

        DragNode<Office> dragNode = new DragNode<Office>();

        dragNode.setDraggingNode( toEntity( dDragDTO.getDraggingNode() ) );
        dragNode.setDropNode( toEntity( dDragDTO.getDropNode() ) );
        dragNode.setDropType( dDragDTO.getDropType() );

        return dragNode;
    }

    @Override
    public Office toEntity(OfficeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Office office = new Office();

        office.setParentId( dtoParentId( dto ) );
        office.setCreateById( dtoCreateById( dto ) );
        office.setUpdateById( dtoUpdateById( dto ) );
        office.setId( dto.getId() );
        office.setCreateTime( dto.getCreateTime() );
        office.setUpdateTime( dto.getUpdateTime() );
        office.setDelFlag( dto.getDelFlag() );
        office.setParentIds( dto.getParentIds() );
        office.setName( dto.getName() );
        office.setSort( dto.getSort() );
        office.setChildren( toEntity( dto.getChildren() ) );
        office.setArea( dto.getArea() );
        office.setCode( dto.getCode() );
        office.setType( dto.getType() );
        office.setGrade( dto.getGrade() );
        office.setAddress( dto.getAddress() );
        office.setZipCode( dto.getZipCode() );
        office.setMaster( dto.getMaster() );
        office.setPhone( dto.getPhone() );
        office.setFax( dto.getFax() );
        office.setEmail( dto.getEmail() );
        office.setUseable( dto.getUseable() );
        office.setRemarks( dto.getRemarks() );

        return office;
    }

    @Override
    public OfficeDTO toDTO(Office entity) {
        if ( entity == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setParent( officeToOfficeDTO( entity ) );
        officeDTO.setCreateBy( officeToUserDTO( entity ) );
        officeDTO.setUpdateBy( officeToUserDTO1( entity ) );
        officeDTO.setId( entity.getId() );
        officeDTO.setCreateTime( entity.getCreateTime() );
        officeDTO.setUpdateTime( entity.getUpdateTime() );
        officeDTO.setDelFlag( entity.getDelFlag() );
        officeDTO.setChildren( toDTO( entity.getChildren() ) );
        officeDTO.setParentIds( entity.getParentIds() );
        officeDTO.setName( entity.getName() );
        officeDTO.setSort( entity.getSort() );
        officeDTO.setArea( entity.getArea() );
        officeDTO.setCode( entity.getCode() );
        officeDTO.setType( entity.getType() );
        officeDTO.setGrade( entity.getGrade() );
        officeDTO.setAddress( entity.getAddress() );
        officeDTO.setZipCode( entity.getZipCode() );
        officeDTO.setMaster( entity.getMaster() );
        officeDTO.setPhone( entity.getPhone() );
        officeDTO.setFax( entity.getFax() );
        officeDTO.setEmail( entity.getEmail() );
        officeDTO.setUseable( entity.getUseable() );
        officeDTO.setRemarks( entity.getRemarks() );

        return officeDTO;
    }

    private String dtoParentId(OfficeDTO officeDTO) {
        if ( officeDTO == null ) {
            return null;
        }
        OfficeDTO parent = officeDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(OfficeDTO officeDTO) {
        if ( officeDTO == null ) {
            return null;
        }
        UserDTO createBy = officeDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(OfficeDTO officeDTO) {
        if ( officeDTO == null ) {
            return null;
        }
        UserDTO updateBy = officeDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected OfficeDTO officeToOfficeDTO(Office office) {
        if ( office == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( office.getParentId() );

        return officeDTO;
    }

    protected UserDTO officeToUserDTO(Office office) {
        if ( office == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( office.getCreateById() );

        return userDTO;
    }

    protected UserDTO officeToUserDTO1(Office office) {
        if ( office == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( office.getUpdateById() );

        return userDTO;
    }
}
