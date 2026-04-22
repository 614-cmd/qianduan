package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.DictType;
import com.jeeplus.sys.service.dto.DictTypeDTO;
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
public class DictTypeWrapperImpl implements DictTypeWrapper {

    @Override
    public DictType toEntity(DictTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DictType dictType = new DictType();

        dictType.setCreateById( dtoCreateById( dto ) );
        dictType.setUpdateById( dtoUpdateById( dto ) );
        dictType.setId( dto.getId() );
        dictType.setCreateTime( dto.getCreateTime() );
        dictType.setUpdateTime( dto.getUpdateTime() );
        dictType.setDelFlag( dto.getDelFlag() );
        dictType.setType( dto.getType() );
        dictType.setRemarks( dto.getRemarks() );

        return dictType;
    }

    @Override
    public DictTypeDTO toDTO(DictType entity) {
        if ( entity == null ) {
            return null;
        }

        DictTypeDTO dictTypeDTO = new DictTypeDTO();

        dictTypeDTO.setCreateBy( dictTypeToUserDTO( entity ) );
        dictTypeDTO.setUpdateBy( dictTypeToUserDTO1( entity ) );
        dictTypeDTO.setId( entity.getId() );
        dictTypeDTO.setCreateTime( entity.getCreateTime() );
        dictTypeDTO.setUpdateTime( entity.getUpdateTime() );
        dictTypeDTO.setDelFlag( entity.getDelFlag() );
        dictTypeDTO.setType( entity.getType() );
        dictTypeDTO.setRemarks( entity.getRemarks() );

        return dictTypeDTO;
    }

    @Override
    public List<DictType> toEntity(List<DictTypeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DictType> list = new ArrayList<DictType>( dtoList.size() );
        for ( DictTypeDTO dictTypeDTO : dtoList ) {
            list.add( toEntity( dictTypeDTO ) );
        }

        return list;
    }

    @Override
    public List<DictTypeDTO> toDTO(List<DictType> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictTypeDTO> list = new ArrayList<DictTypeDTO>( entityList.size() );
        for ( DictType dictType : entityList ) {
            list.add( toDTO( dictType ) );
        }

        return list;
    }

    @Override
    public Page<DictType> toEntity(Page<DictTypeDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DictType> page1 = new Page<DictType>();

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
    public Page<DictTypeDTO> toDTO(Page<DictType> page) {
        if ( page == null ) {
            return null;
        }

        Page<DictTypeDTO> page1 = new Page<DictTypeDTO>();

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

    private String dtoCreateById(DictTypeDTO dictTypeDTO) {
        if ( dictTypeDTO == null ) {
            return null;
        }
        UserDTO createBy = dictTypeDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(DictTypeDTO dictTypeDTO) {
        if ( dictTypeDTO == null ) {
            return null;
        }
        UserDTO updateBy = dictTypeDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO dictTypeToUserDTO(DictType dictType) {
        if ( dictType == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dictType.getCreateById() );

        return userDTO;
    }

    protected UserDTO dictTypeToUserDTO1(DictType dictType) {
        if ( dictType == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dictType.getUpdateById() );

        return userDTO;
    }
}
