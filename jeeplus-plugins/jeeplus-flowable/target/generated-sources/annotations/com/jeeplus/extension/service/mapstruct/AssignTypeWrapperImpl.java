package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.AssignType;
import com.jeeplus.extension.service.dto.AssignTypeDTO;
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
public class AssignTypeWrapperImpl implements AssignTypeWrapper {

    @Override
    public AssignType toEntity(AssignTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AssignType assignType = new AssignType();

        assignType.setCreateById( dtoCreateById( dto ) );
        assignType.setUpdateById( dtoUpdateById( dto ) );
        assignType.setId( dto.getId() );
        assignType.setCreateTime( dto.getCreateTime() );
        assignType.setUpdateTime( dto.getUpdateTime() );
        assignType.setDelFlag( dto.getDelFlag() );
        assignType.setCode( dto.getCode() );
        assignType.setName( dto.getName() );
        assignType.setSort( dto.getSort() );
        assignType.setRemarks( dto.getRemarks() );

        return assignType;
    }

    @Override
    public AssignTypeDTO toDTO(AssignType entity) {
        if ( entity == null ) {
            return null;
        }

        AssignTypeDTO assignTypeDTO = new AssignTypeDTO();

        assignTypeDTO.setCreateBy( assignTypeToUserDTO( entity ) );
        assignTypeDTO.setUpdateBy( assignTypeToUserDTO1( entity ) );
        assignTypeDTO.setId( entity.getId() );
        assignTypeDTO.setCreateTime( entity.getCreateTime() );
        assignTypeDTO.setUpdateTime( entity.getUpdateTime() );
        assignTypeDTO.setDelFlag( entity.getDelFlag() );
        assignTypeDTO.setCode( entity.getCode() );
        assignTypeDTO.setName( entity.getName() );
        assignTypeDTO.setSort( entity.getSort() );
        assignTypeDTO.setRemarks( entity.getRemarks() );

        return assignTypeDTO;
    }

    @Override
    public List<AssignType> toEntity(List<AssignTypeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<AssignType> list = new ArrayList<AssignType>( dtoList.size() );
        for ( AssignTypeDTO assignTypeDTO : dtoList ) {
            list.add( toEntity( assignTypeDTO ) );
        }

        return list;
    }

    @Override
    public List<AssignTypeDTO> toDTO(List<AssignType> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AssignTypeDTO> list = new ArrayList<AssignTypeDTO>( entityList.size() );
        for ( AssignType assignType : entityList ) {
            list.add( toDTO( assignType ) );
        }

        return list;
    }

    @Override
    public Page<AssignType> toEntity(Page<AssignTypeDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<AssignType> page1 = new Page<AssignType>();

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
    public Page<AssignTypeDTO> toDTO(Page<AssignType> page) {
        if ( page == null ) {
            return null;
        }

        Page<AssignTypeDTO> page1 = new Page<AssignTypeDTO>();

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

    private String dtoCreateById(AssignTypeDTO assignTypeDTO) {
        if ( assignTypeDTO == null ) {
            return null;
        }
        UserDTO createBy = assignTypeDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(AssignTypeDTO assignTypeDTO) {
        if ( assignTypeDTO == null ) {
            return null;
        }
        UserDTO updateBy = assignTypeDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO assignTypeToUserDTO(AssignType assignType) {
        if ( assignType == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( assignType.getCreateById() );

        return userDTO;
    }

    protected UserDTO assignTypeToUserDTO1(AssignType assignType) {
        if ( assignType == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( assignType.getUpdateById() );

        return userDTO;
    }
}
