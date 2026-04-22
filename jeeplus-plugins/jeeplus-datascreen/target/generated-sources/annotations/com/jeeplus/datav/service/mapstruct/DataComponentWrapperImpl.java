package com.jeeplus.datav.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.datav.domain.DataComponent;
import com.jeeplus.datav.service.dto.DataComponentDTO;
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
public class DataComponentWrapperImpl implements DataComponentWrapper {

    @Override
    public DataComponent toEntity(DataComponentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DataComponent dataComponent = new DataComponent();

        dataComponent.setCreateById( dtoCreateById( dto ) );
        dataComponent.setUpdateById( dtoUpdateById( dto ) );
        dataComponent.setId( dto.getId() );
        dataComponent.setCreateTime( dto.getCreateTime() );
        dataComponent.setUpdateTime( dto.getUpdateTime() );
        dataComponent.setDelFlag( dto.getDelFlag() );
        dataComponent.setName( dto.getName() );
        dataComponent.setContent( dto.getContent() );

        return dataComponent;
    }

    @Override
    public DataComponentDTO toDTO(DataComponent entity) {
        if ( entity == null ) {
            return null;
        }

        DataComponentDTO dataComponentDTO = new DataComponentDTO();

        dataComponentDTO.setCreateBy( dataComponentToUserDTO( entity ) );
        dataComponentDTO.setUpdateBy( dataComponentToUserDTO1( entity ) );
        dataComponentDTO.setId( entity.getId() );
        dataComponentDTO.setCreateTime( entity.getCreateTime() );
        dataComponentDTO.setUpdateTime( entity.getUpdateTime() );
        dataComponentDTO.setDelFlag( entity.getDelFlag() );
        dataComponentDTO.setName( entity.getName() );
        dataComponentDTO.setContent( entity.getContent() );

        return dataComponentDTO;
    }

    @Override
    public List<DataComponent> toEntity(List<DataComponentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DataComponent> list = new ArrayList<DataComponent>( dtoList.size() );
        for ( DataComponentDTO dataComponentDTO : dtoList ) {
            list.add( toEntity( dataComponentDTO ) );
        }

        return list;
    }

    @Override
    public List<DataComponentDTO> toDTO(List<DataComponent> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DataComponentDTO> list = new ArrayList<DataComponentDTO>( entityList.size() );
        for ( DataComponent dataComponent : entityList ) {
            list.add( toDTO( dataComponent ) );
        }

        return list;
    }

    @Override
    public Page<DataComponent> toEntity(Page<DataComponentDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataComponent> page1 = new Page<DataComponent>();

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
    public Page<DataComponentDTO> toDTO(Page<DataComponent> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataComponentDTO> page1 = new Page<DataComponentDTO>();

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

    private String dtoCreateById(DataComponentDTO dataComponentDTO) {
        if ( dataComponentDTO == null ) {
            return null;
        }
        UserDTO createBy = dataComponentDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(DataComponentDTO dataComponentDTO) {
        if ( dataComponentDTO == null ) {
            return null;
        }
        UserDTO updateBy = dataComponentDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO dataComponentToUserDTO(DataComponent dataComponent) {
        if ( dataComponent == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataComponent.getCreateById() );

        return userDTO;
    }

    protected UserDTO dataComponentToUserDTO1(DataComponent dataComponent) {
        if ( dataComponent == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataComponent.getUpdateById() );

        return userDTO;
    }
}
