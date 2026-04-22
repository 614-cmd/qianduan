package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.Listener;
import com.jeeplus.extension.service.dto.ListenerDTO;
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
public class ListenerWrapperImpl implements ListenerWrapper {

    @Override
    public Listener toEntity(ListenerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Listener listener = new Listener();

        listener.setCreateById( dtoCreateById( dto ) );
        listener.setUpdateById( dtoUpdateById( dto ) );
        listener.setId( dto.getId() );
        listener.setCreateTime( dto.getCreateTime() );
        listener.setUpdateTime( dto.getUpdateTime() );
        listener.setDelFlag( dto.getDelFlag() );
        listener.setName( dto.getName() );
        listener.setListenerType( dto.getListenerType() );
        listener.setEvent( dto.getEvent() );
        listener.setValueType( dto.getValueType() );
        listener.setValue( dto.getValue() );

        return listener;
    }

    @Override
    public ListenerDTO toDTO(Listener entity) {
        if ( entity == null ) {
            return null;
        }

        ListenerDTO listenerDTO = new ListenerDTO();

        listenerDTO.setCreateBy( listenerToUserDTO( entity ) );
        listenerDTO.setUpdateBy( listenerToUserDTO1( entity ) );
        listenerDTO.setId( entity.getId() );
        listenerDTO.setCreateTime( entity.getCreateTime() );
        listenerDTO.setUpdateTime( entity.getUpdateTime() );
        listenerDTO.setDelFlag( entity.getDelFlag() );
        listenerDTO.setName( entity.getName() );
        listenerDTO.setListenerType( entity.getListenerType() );
        listenerDTO.setEvent( entity.getEvent() );
        listenerDTO.setValueType( entity.getValueType() );
        listenerDTO.setValue( entity.getValue() );

        return listenerDTO;
    }

    @Override
    public List<Listener> toEntity(List<ListenerDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Listener> list = new ArrayList<Listener>( dtoList.size() );
        for ( ListenerDTO listenerDTO : dtoList ) {
            list.add( toEntity( listenerDTO ) );
        }

        return list;
    }

    @Override
    public List<ListenerDTO> toDTO(List<Listener> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ListenerDTO> list = new ArrayList<ListenerDTO>( entityList.size() );
        for ( Listener listener : entityList ) {
            list.add( toDTO( listener ) );
        }

        return list;
    }

    @Override
    public Page<Listener> toEntity(Page<ListenerDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Listener> page1 = new Page<Listener>();

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
    public Page<ListenerDTO> toDTO(Page<Listener> page) {
        if ( page == null ) {
            return null;
        }

        Page<ListenerDTO> page1 = new Page<ListenerDTO>();

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

    private String dtoCreateById(ListenerDTO listenerDTO) {
        if ( listenerDTO == null ) {
            return null;
        }
        UserDTO createBy = listenerDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(ListenerDTO listenerDTO) {
        if ( listenerDTO == null ) {
            return null;
        }
        UserDTO updateBy = listenerDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO listenerToUserDTO(Listener listener) {
        if ( listener == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( listener.getCreateById() );

        return userDTO;
    }

    protected UserDTO listenerToUserDTO1(Listener listener) {
        if ( listener == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( listener.getUpdateById() );

        return userDTO;
    }
}
