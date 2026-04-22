package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.Desk;
import com.jeeplus.sys.service.dto.DeskDTO;
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
public class DeskWrapperImpl implements DeskWrapper {

    @Override
    public Desk toEntity(DeskDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Desk desk = new Desk();

        desk.setCreateById( dtoCreateById( dto ) );
        desk.setUpdateById( dtoUpdateById( dto ) );
        desk.setId( dto.getId() );
        desk.setCreateTime( dto.getCreateTime() );
        desk.setUpdateTime( dto.getUpdateTime() );
        desk.setDelFlag( dto.getDelFlag() );
        desk.setGrid( dto.getGrid() );

        return desk;
    }

    @Override
    public DeskDTO toDTO(Desk entity) {
        if ( entity == null ) {
            return null;
        }

        DeskDTO deskDTO = new DeskDTO();

        deskDTO.setCreateBy( deskToUserDTO( entity ) );
        deskDTO.setUpdateBy( deskToUserDTO1( entity ) );
        deskDTO.setId( entity.getId() );
        deskDTO.setCreateTime( entity.getCreateTime() );
        deskDTO.setUpdateTime( entity.getUpdateTime() );
        deskDTO.setDelFlag( entity.getDelFlag() );
        deskDTO.setGrid( entity.getGrid() );

        return deskDTO;
    }

    @Override
    public List<Desk> toEntity(List<DeskDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Desk> list = new ArrayList<Desk>( dtoList.size() );
        for ( DeskDTO deskDTO : dtoList ) {
            list.add( toEntity( deskDTO ) );
        }

        return list;
    }

    @Override
    public List<DeskDTO> toDTO(List<Desk> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DeskDTO> list = new ArrayList<DeskDTO>( entityList.size() );
        for ( Desk desk : entityList ) {
            list.add( toDTO( desk ) );
        }

        return list;
    }

    @Override
    public Page<Desk> toEntity(Page<DeskDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Desk> page1 = new Page<Desk>();

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
    public Page<DeskDTO> toDTO(Page<Desk> page) {
        if ( page == null ) {
            return null;
        }

        Page<DeskDTO> page1 = new Page<DeskDTO>();

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

    private String dtoCreateById(DeskDTO deskDTO) {
        if ( deskDTO == null ) {
            return null;
        }
        UserDTO createBy = deskDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(DeskDTO deskDTO) {
        if ( deskDTO == null ) {
            return null;
        }
        UserDTO updateBy = deskDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO deskToUserDTO(Desk desk) {
        if ( desk == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( desk.getCreateById() );

        return userDTO;
    }

    protected UserDTO deskToUserDTO1(Desk desk) {
        if ( desk == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( desk.getUpdateById() );

        return userDTO;
    }
}
