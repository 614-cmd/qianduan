package com.jeeplus.mail.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.mail.domain.MailCompose;
import com.jeeplus.mail.service.dto.MailComposeDTO;
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
public class MailComposeWrapperImpl implements MailComposeWrapper {

    @Override
    public List<MailCompose> toEntity(List<MailComposeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MailCompose> list = new ArrayList<MailCompose>( dtoList.size() );
        for ( MailComposeDTO mailComposeDTO : dtoList ) {
            list.add( toEntity( mailComposeDTO ) );
        }

        return list;
    }

    @Override
    public List<MailComposeDTO> toDTO(List<MailCompose> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MailComposeDTO> list = new ArrayList<MailComposeDTO>( entityList.size() );
        for ( MailCompose mailCompose : entityList ) {
            list.add( toDTO( mailCompose ) );
        }

        return list;
    }

    @Override
    public Page<MailCompose> toEntity(Page<MailComposeDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<MailCompose> page1 = new Page<MailCompose>();

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
    public Page<MailComposeDTO> toDTO(Page<MailCompose> page) {
        if ( page == null ) {
            return null;
        }

        Page<MailComposeDTO> page1 = new Page<MailComposeDTO>();

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
    public MailCompose toEntity(MailComposeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MailCompose mailCompose = new MailCompose();

        mailCompose.setMailId( dtoMailDTOId( dto ) );
        mailCompose.setSenderId( dtoSenderId( dto ) );
        mailCompose.setCreateById( dtoCreateById( dto ) );
        mailCompose.setUpdateById( dtoUpdateById( dto ) );
        mailCompose.setId( dto.getId() );
        mailCompose.setCreateTime( dto.getCreateTime() );
        mailCompose.setUpdateTime( dto.getUpdateTime() );
        mailCompose.setDelFlag( dto.getDelFlag() );
        mailCompose.setStatus( dto.getStatus() );
        mailCompose.setReceiverIds( dto.getReceiverIds() );
        mailCompose.setSendTime( dto.getSendTime() );

        return mailCompose;
    }

    @Override
    public MailComposeDTO toDTO(MailCompose entity) {
        if ( entity == null ) {
            return null;
        }

        MailComposeDTO mailComposeDTO = new MailComposeDTO();

        mailComposeDTO.setMailDTO( mailComposeToMailDTO( entity ) );
        mailComposeDTO.setSender( mailComposeToUserDTO( entity ) );
        mailComposeDTO.setCreateBy( mailComposeToUserDTO1( entity ) );
        mailComposeDTO.setUpdateBy( mailComposeToUserDTO2( entity ) );
        mailComposeDTO.setId( entity.getId() );
        mailComposeDTO.setCreateTime( entity.getCreateTime() );
        mailComposeDTO.setUpdateTime( entity.getUpdateTime() );
        mailComposeDTO.setDelFlag( entity.getDelFlag() );
        mailComposeDTO.setStatus( entity.getStatus() );
        mailComposeDTO.setReceiverIds( entity.getReceiverIds() );
        mailComposeDTO.setSendTime( entity.getSendTime() );

        return mailComposeDTO;
    }

    private String dtoMailDTOId(MailComposeDTO mailComposeDTO) {
        if ( mailComposeDTO == null ) {
            return null;
        }
        MailDTO mailDTO = mailComposeDTO.getMailDTO();
        if ( mailDTO == null ) {
            return null;
        }
        String id = mailDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoSenderId(MailComposeDTO mailComposeDTO) {
        if ( mailComposeDTO == null ) {
            return null;
        }
        UserDTO sender = mailComposeDTO.getSender();
        if ( sender == null ) {
            return null;
        }
        String id = sender.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(MailComposeDTO mailComposeDTO) {
        if ( mailComposeDTO == null ) {
            return null;
        }
        UserDTO createBy = mailComposeDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(MailComposeDTO mailComposeDTO) {
        if ( mailComposeDTO == null ) {
            return null;
        }
        UserDTO updateBy = mailComposeDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected MailDTO mailComposeToMailDTO(MailCompose mailCompose) {
        if ( mailCompose == null ) {
            return null;
        }

        MailDTO mailDTO = new MailDTO();

        mailDTO.setId( mailCompose.getMailId() );

        return mailDTO;
    }

    protected UserDTO mailComposeToUserDTO(MailCompose mailCompose) {
        if ( mailCompose == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailCompose.getSenderId() );

        return userDTO;
    }

    protected UserDTO mailComposeToUserDTO1(MailCompose mailCompose) {
        if ( mailCompose == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailCompose.getCreateById() );

        return userDTO;
    }

    protected UserDTO mailComposeToUserDTO2(MailCompose mailCompose) {
        if ( mailCompose == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mailCompose.getUpdateById() );

        return userDTO;
    }
}
