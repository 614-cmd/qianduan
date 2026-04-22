package com.jeeplus.notify.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.notify.domain.Notify;
import com.jeeplus.notify.service.dto.NotifyDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:31+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class NotifyWrapperImpl implements NotifyWrapper {

    @Override
    public Notify toEntity(NotifyDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Notify notify = new Notify();

        notify.setCreateById( dtoCreateById( dto ) );
        notify.setUpdateById( dtoUpdateById( dto ) );
        notify.setId( dto.getId() );
        notify.setCreateTime( dto.getCreateTime() );
        notify.setUpdateTime( dto.getUpdateTime() );
        notify.setDelFlag( dto.getDelFlag() );
        notify.setType( dto.getType() );
        notify.setTitle( dto.getTitle() );
        notify.setContent( dto.getContent() );
        notify.setFiles( dto.getFiles() );
        notify.setStatus( dto.getStatus() );

        return notify;
    }

    @Override
    public NotifyDTO toDTO(Notify entity) {
        if ( entity == null ) {
            return null;
        }

        NotifyDTO notifyDTO = new NotifyDTO();

        notifyDTO.setCreateBy( notifyToUserDTO( entity ) );
        notifyDTO.setUpdateBy( notifyToUserDTO1( entity ) );
        notifyDTO.setId( entity.getId() );
        notifyDTO.setCreateTime( entity.getCreateTime() );
        notifyDTO.setUpdateTime( entity.getUpdateTime() );
        notifyDTO.setDelFlag( entity.getDelFlag() );
        notifyDTO.setType( entity.getType() );
        notifyDTO.setTitle( entity.getTitle() );
        notifyDTO.setContent( entity.getContent() );
        notifyDTO.setFiles( entity.getFiles() );
        notifyDTO.setStatus( entity.getStatus() );

        return notifyDTO;
    }

    @Override
    public List<Notify> toEntity(List<NotifyDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Notify> list = new ArrayList<Notify>( dtoList.size() );
        for ( NotifyDTO notifyDTO : dtoList ) {
            list.add( toEntity( notifyDTO ) );
        }

        return list;
    }

    @Override
    public List<NotifyDTO> toDTO(List<Notify> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NotifyDTO> list = new ArrayList<NotifyDTO>( entityList.size() );
        for ( Notify notify : entityList ) {
            list.add( toDTO( notify ) );
        }

        return list;
    }

    @Override
    public Page<Notify> toEntity(Page<NotifyDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Notify> page1 = new Page<Notify>();

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
    public Page<NotifyDTO> toDTO(Page<Notify> page) {
        if ( page == null ) {
            return null;
        }

        Page<NotifyDTO> page1 = new Page<NotifyDTO>();

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

    private String dtoCreateById(NotifyDTO notifyDTO) {
        if ( notifyDTO == null ) {
            return null;
        }
        UserDTO createBy = notifyDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(NotifyDTO notifyDTO) {
        if ( notifyDTO == null ) {
            return null;
        }
        UserDTO updateBy = notifyDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO notifyToUserDTO(Notify notify) {
        if ( notify == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notify.getCreateById() );

        return userDTO;
    }

    protected UserDTO notifyToUserDTO1(Notify notify) {
        if ( notify == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notify.getUpdateById() );

        return userDTO;
    }
}
