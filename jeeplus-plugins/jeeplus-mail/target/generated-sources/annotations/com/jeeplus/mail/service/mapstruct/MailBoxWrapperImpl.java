package com.jeeplus.mail.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.mail.domain.MailBox;
import com.jeeplus.mail.service.dto.MailBoxDTO;
import com.jeeplus.mail.service.dto.MailDTO;
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
public class MailBoxWrapperImpl implements MailBoxWrapper {

    @Override
    public List<MailBox> toEntity(List<MailBoxDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MailBox> list = new ArrayList<MailBox>( dtoList.size() );
        for ( MailBoxDTO mailBoxDTO : dtoList ) {
            list.add( toEntity( mailBoxDTO ) );
        }

        return list;
    }

    @Override
    public List<MailBoxDTO> toDTO(List<MailBox> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MailBoxDTO> list = new ArrayList<MailBoxDTO>( entityList.size() );
        for ( MailBox mailBox : entityList ) {
            list.add( toDTO( mailBox ) );
        }

        return list;
    }

    @Override
    public Page<MailBox> toEntity(Page<MailBoxDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<MailBox> page1 = new Page<MailBox>();

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
    public Page<MailBoxDTO> toDTO(Page<MailBox> page) {
        if ( page == null ) {
            return null;
        }

        Page<MailBoxDTO> page1 = new Page<MailBoxDTO>();

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
    public MailBox toEntity(MailBoxDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MailBox mailBox = new MailBox();

        mailBox.setMailId( dtoMailDTOId( dto ) );
        mailBox.setSenderId( dtoSenderId( dto ) );
        mailBox.setReceiverId( dtoReceiverId( dto ) );
        mailBox.setCreateById( dtoCreateById( dto ) );
        mailBox.setUpdateById( dtoUpdateById( dto ) );
        mailBox.setId( dto.getId() );
        mailBox.setCreateTime( dto.getCreateTime() );
        mailBox.setUpdateTime( dto.getUpdateTime() );
        mailBox.setDelFlag( dto.getDelFlag() );
        mailBox.setReadStatus( dto.getReadStatus() );
        mailBox.setReceiverIds( dto.getReceiverIds() );
        mailBox.setSendTime( dto.getSendTime() );

        return mailBox;
    }

    @Override
    public MailBoxDTO toDTO(MailBox entity) {
        if ( entity == null ) {
            return null;
        }

        MailBoxDTO mailBoxDTO = new MailBoxDTO();

        mailBoxDTO.setMailDTO( mailBoxToMailDTO( entity ) );
        mailBoxDTO.setSender( mailBoxToUserDTO( entity ) );
        mailBoxDTO.setReceiver( mailBoxToUserDTO1( entity ) );
        mailBoxDTO.setCreateBy( mailBoxToUserDTO2( entity ) );
        mailBoxDTO.setUpdateBy( mailBoxToUserDTO3( entity ) );
        mailBoxDTO.setId( entity.getId() );
        mailBoxDTO.setCreateTime( entity.getCreateTime() );
        mailBoxDTO.setUpdateTime( entity.getUpdateTime() );
        mailBoxDTO.setDelFlag( entity.getDelFlag() );
        mailBoxDTO.setReadStatus( entity.getReadStatus() );
        mailBoxDTO.setReceiverIds( entity.getReceiverIds() );
        mailBoxDTO.setSendTime( entity.getSendTime() );

        return mailBoxDTO;
    }

    private String dtoMailDTOId(MailBoxDTO mailBoxDTO) {
        if ( mailBoxDTO == null ) {
            return null;
        }
        MailDTO mailDTO = mailBoxDTO.getMailDTO();
        if ( mailDTO == null ) {
            return null;
        }
        String id = mailDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoSenderId(MailBoxDTO mailBoxDTO) {
        if ( mailBoxDTO == null ) {
            return null;
        }
        UserDTO sender = mailBoxDTO.getSender();
        if ( sender == null ) {
            return null;
        }
        String id = sender.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoReceiverId(MailBoxDTO mailBoxDTO) {
        if ( mailBoxDTO == null ) {
            return null;
        }
        UserDTO receiver = mailBoxDTO.getReceiver();
        if ( receiver == null ) {
            return null;
        }
        String id = receiver.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(MailBoxDTO mailBoxDTO) {
        if ( mailBoxDTO == null ) {
            return null;
        }
        UserDTO createBy = mailBoxDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(MailBoxDTO mailBoxDTO) {
        if ( mailBoxDTO == null ) {
            return null;
        }
        UserDTO updateBy = mailBoxDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected MailDTO mailBoxToMailDTO(MailBox mailBox) {
        if ( mailBox == null ) {
            return null;
        }

        MailDTO mailDTO = new MailDTO();

        mailDTO.setId( mailBox.getMailId() );

        return mailDTO;
    }

    protected UserDTO mailBoxToUserDTO(MailBox mailBox) {
        if ( mailBox == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailBox.getSenderId() );

        return userDTO;
    }

    protected UserDTO mailBoxToUserDTO1(MailBox mailBox) {
        if ( mailBox == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailBox.getReceiverId() );

        return userDTO;
    }

    protected UserDTO mailBoxToUserDTO2(MailBox mailBox) {
        if ( mailBox == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailBox.getCreateById() );

        return userDTO;
    }

    protected UserDTO mailBoxToUserDTO3(MailBox mailBox) {
        if ( mailBox == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailBox.getUpdateById() );

        return userDTO;
    }
}
