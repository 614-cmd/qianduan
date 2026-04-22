package com.jeeplus.mail.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.mail.domain.MailTrash;
import com.jeeplus.mail.service.dto.MailDTO;
import com.jeeplus.mail.service.dto.MailTrashDTO;
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
public class MailTrashWrapperImpl implements MailTrashWrapper {

    @Override
    public List<MailTrash> toEntity(List<MailTrashDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MailTrash> list = new ArrayList<MailTrash>( dtoList.size() );
        for ( MailTrashDTO mailTrashDTO : dtoList ) {
            list.add( toEntity( mailTrashDTO ) );
        }

        return list;
    }

    @Override
    public List<MailTrashDTO> toDTO(List<MailTrash> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MailTrashDTO> list = new ArrayList<MailTrashDTO>( entityList.size() );
        for ( MailTrash mailTrash : entityList ) {
            list.add( toDTO( mailTrash ) );
        }

        return list;
    }

    @Override
    public Page<MailTrash> toEntity(Page<MailTrashDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<MailTrash> page1 = new Page<MailTrash>();

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
    public Page<MailTrashDTO> toDTO(Page<MailTrash> page) {
        if ( page == null ) {
            return null;
        }

        Page<MailTrashDTO> page1 = new Page<MailTrashDTO>();

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
    public MailTrash toEntity(MailTrashDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MailTrash mailTrash = new MailTrash();

        mailTrash.setMailId( dtoMailDTOId( dto ) );
        mailTrash.setSenderId( dtoSenderId( dto ) );
        mailTrash.setCreateById( dtoCreateById( dto ) );
        mailTrash.setUpdateById( dtoUpdateById( dto ) );
        mailTrash.setId( dto.getId() );
        mailTrash.setCreateTime( dto.getCreateTime() );
        mailTrash.setUpdateTime( dto.getUpdateTime() );
        mailTrash.setDelFlag( dto.getDelFlag() );
        mailTrash.setStatus( dto.getStatus() );
        mailTrash.setReceiverIds( dto.getReceiverIds() );
        mailTrash.setSendTime( dto.getSendTime() );

        return mailTrash;
    }

    @Override
    public MailTrashDTO toDTO(MailTrash entity) {
        if ( entity == null ) {
            return null;
        }

        MailTrashDTO mailTrashDTO = new MailTrashDTO();

        mailTrashDTO.setMailDTO( mailTrashToMailDTO( entity ) );
        mailTrashDTO.setSender( mailTrashToUserDTO( entity ) );
        mailTrashDTO.setCreateBy( mailTrashToUserDTO1( entity ) );
        mailTrashDTO.setUpdateBy( mailTrashToUserDTO2( entity ) );
        mailTrashDTO.setId( entity.getId() );
        mailTrashDTO.setCreateTime( entity.getCreateTime() );
        mailTrashDTO.setUpdateTime( entity.getUpdateTime() );
        mailTrashDTO.setDelFlag( entity.getDelFlag() );
        mailTrashDTO.setStatus( entity.getStatus() );
        mailTrashDTO.setReceiverIds( entity.getReceiverIds() );
        mailTrashDTO.setSendTime( entity.getSendTime() );

        return mailTrashDTO;
    }

    private String dtoMailDTOId(MailTrashDTO mailTrashDTO) {
        if ( mailTrashDTO == null ) {
            return null;
        }
        MailDTO mailDTO = mailTrashDTO.getMailDTO();
        if ( mailDTO == null ) {
            return null;
        }
        String id = mailDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoSenderId(MailTrashDTO mailTrashDTO) {
        if ( mailTrashDTO == null ) {
            return null;
        }
        UserDTO sender = mailTrashDTO.getSender();
        if ( sender == null ) {
            return null;
        }
        String id = sender.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(MailTrashDTO mailTrashDTO) {
        if ( mailTrashDTO == null ) {
            return null;
        }
        UserDTO createBy = mailTrashDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(MailTrashDTO mailTrashDTO) {
        if ( mailTrashDTO == null ) {
            return null;
        }
        UserDTO updateBy = mailTrashDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected MailDTO mailTrashToMailDTO(MailTrash mailTrash) {
        if ( mailTrash == null ) {
            return null;
        }

        MailDTO mailDTO = new MailDTO();

        mailDTO.setId( mailTrash.getMailId() );

        return mailDTO;
    }

    protected UserDTO mailTrashToUserDTO(MailTrash mailTrash) {
        if ( mailTrash == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailTrash.getSenderId() );

        return userDTO;
    }

    protected UserDTO mailTrashToUserDTO1(MailTrash mailTrash) {
        if ( mailTrash == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailTrash.getCreateById() );

        return userDTO;
    }

    protected UserDTO mailTrashToUserDTO2(MailTrash mailTrash) {
        if ( mailTrash == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailTrash.getUpdateById() );

        return userDTO;
    }
}
