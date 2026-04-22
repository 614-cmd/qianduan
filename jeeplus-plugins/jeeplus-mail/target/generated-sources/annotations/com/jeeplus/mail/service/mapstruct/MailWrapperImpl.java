package com.jeeplus.mail.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.mail.domain.Mail;
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
public class MailWrapperImpl implements MailWrapper {

    @Override
    public List<Mail> toEntity(List<MailDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Mail> list = new ArrayList<Mail>( dtoList.size() );
        for ( MailDTO mailDTO : dtoList ) {
            list.add( toEntity( mailDTO ) );
        }

        return list;
    }

    @Override
    public List<MailDTO> toDTO(List<Mail> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MailDTO> list = new ArrayList<MailDTO>( entityList.size() );
        for ( Mail mail : entityList ) {
            list.add( toDTO( mail ) );
        }

        return list;
    }

    @Override
    public Page<Mail> toEntity(Page<MailDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Mail> page1 = new Page<Mail>();

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
    public Page<MailDTO> toDTO(Page<Mail> page) {
        if ( page == null ) {
            return null;
        }

        Page<MailDTO> page1 = new Page<MailDTO>();

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
    public Mail toEntity(MailDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Mail mail = new Mail();

        mail.setCreateById( dtoCreateById( dto ) );
        mail.setUpdateById( dtoUpdateById( dto ) );
        mail.setId( dto.getId() );
        mail.setCreateTime( dto.getCreateTime() );
        mail.setUpdateTime( dto.getUpdateTime() );
        mail.setDelFlag( dto.getDelFlag() );
        mail.setTitle( dto.getTitle() );
        mail.setOverview( dto.getOverview() );
        mail.setContent( dto.getContent() );

        return mail;
    }

    @Override
    public MailDTO toDTO(Mail entity) {
        if ( entity == null ) {
            return null;
        }

        MailDTO mailDTO = new MailDTO();

        mailDTO.setCreateBy( mailToUserDTO( entity ) );
        mailDTO.setUpdateBy( mailToUserDTO1( entity ) );
        mailDTO.setId( entity.getId() );
        mailDTO.setCreateTime( entity.getCreateTime() );
        mailDTO.setUpdateTime( entity.getUpdateTime() );
        mailDTO.setDelFlag( entity.getDelFlag() );
        mailDTO.setTitle( entity.getTitle() );
        mailDTO.setOverview( entity.getOverview() );
        mailDTO.setContent( entity.getContent() );

        return mailDTO;
    }

    private String dtoCreateById(MailDTO mailDTO) {
        if ( mailDTO == null ) {
            return null;
        }
        UserDTO createBy = mailDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(MailDTO mailDTO) {
        if ( mailDTO == null ) {
            return null;
        }
        UserDTO updateBy = mailDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO mailToUserDTO(Mail mail) {
        if ( mail == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mail.getCreateById() );

        return userDTO;
    }

    protected UserDTO mailToUserDTO1(Mail mail) {
        if ( mail == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( mail.getUpdateById() );

        return userDTO;
    }
}
