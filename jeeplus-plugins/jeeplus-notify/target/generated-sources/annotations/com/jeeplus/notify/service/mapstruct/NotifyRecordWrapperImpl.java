package com.jeeplus.notify.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.notify.domain.NotifyRecord;
import com.jeeplus.notify.service.dto.NotifyDTO;
import com.jeeplus.notify.service.dto.NotifyRecordDTO;
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
public class NotifyRecordWrapperImpl implements NotifyRecordWrapper {

    @Override
    public List<NotifyRecord> toEntity(List<NotifyRecordDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<NotifyRecord> list = new ArrayList<NotifyRecord>( dtoList.size() );
        for ( NotifyRecordDTO notifyRecordDTO : dtoList ) {
            list.add( toEntity( notifyRecordDTO ) );
        }

        return list;
    }

    @Override
    public List<NotifyRecordDTO> toDTO(List<NotifyRecord> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NotifyRecordDTO> list = new ArrayList<NotifyRecordDTO>( entityList.size() );
        for ( NotifyRecord notifyRecord : entityList ) {
            list.add( toDTO( notifyRecord ) );
        }

        return list;
    }

    @Override
    public Page<NotifyRecord> toEntity(Page<NotifyRecordDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<NotifyRecord> page1 = new Page<NotifyRecord>();

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
    public Page<NotifyRecordDTO> toDTO(Page<NotifyRecord> page) {
        if ( page == null ) {
            return null;
        }

        Page<NotifyRecordDTO> page1 = new Page<NotifyRecordDTO>();

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
    public NotifyRecord toEntity(NotifyRecordDTO dto) {
        if ( dto == null ) {
            return null;
        }

        NotifyRecord notifyRecord = new NotifyRecord();

        notifyRecord.setNotifyId( dtoNotifyDTOId( dto ) );
        notifyRecord.setUserId( dtoUserDTOId( dto ) );
        notifyRecord.setCreateById( dtoCreateById( dto ) );
        notifyRecord.setUpdateById( dtoUpdateById( dto ) );
        notifyRecord.setId( dto.getId() );
        notifyRecord.setCreateTime( dto.getCreateTime() );
        notifyRecord.setUpdateTime( dto.getUpdateTime() );
        notifyRecord.setDelFlag( dto.getDelFlag() );
        notifyRecord.setReadFlag( dto.getReadFlag() );
        notifyRecord.setReadDate( dto.getReadDate() );

        return notifyRecord;
    }

    @Override
    public NotifyRecordDTO toDTO(NotifyRecord entity) {
        if ( entity == null ) {
            return null;
        }

        NotifyRecordDTO notifyRecordDTO = new NotifyRecordDTO();

        notifyRecordDTO.setNotifyDTO( notifyRecordToNotifyDTO( entity ) );
        notifyRecordDTO.setUserDTO( notifyRecordToUserDTO( entity ) );
        notifyRecordDTO.setCreateBy( notifyRecordToUserDTO1( entity ) );
        notifyRecordDTO.setUpdateBy( notifyRecordToUserDTO2( entity ) );
        notifyRecordDTO.setId( entity.getId() );
        notifyRecordDTO.setCreateTime( entity.getCreateTime() );
        notifyRecordDTO.setUpdateTime( entity.getUpdateTime() );
        notifyRecordDTO.setDelFlag( entity.getDelFlag() );
        notifyRecordDTO.setReadFlag( entity.getReadFlag() );
        notifyRecordDTO.setReadDate( entity.getReadDate() );

        return notifyRecordDTO;
    }

    private String dtoNotifyDTOId(NotifyRecordDTO notifyRecordDTO) {
        if ( notifyRecordDTO == null ) {
            return null;
        }
        NotifyDTO notifyDTO = notifyRecordDTO.getNotifyDTO();
        if ( notifyDTO == null ) {
            return null;
        }
        String id = notifyDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUserDTOId(NotifyRecordDTO notifyRecordDTO) {
        if ( notifyRecordDTO == null ) {
            return null;
        }
        UserDTO userDTO = notifyRecordDTO.getUserDTO();
        if ( userDTO == null ) {
            return null;
        }
        String id = userDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(NotifyRecordDTO notifyRecordDTO) {
        if ( notifyRecordDTO == null ) {
            return null;
        }
        UserDTO createBy = notifyRecordDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(NotifyRecordDTO notifyRecordDTO) {
        if ( notifyRecordDTO == null ) {
            return null;
        }
        UserDTO updateBy = notifyRecordDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected NotifyDTO notifyRecordToNotifyDTO(NotifyRecord notifyRecord) {
        if ( notifyRecord == null ) {
            return null;
        }

        NotifyDTO notifyDTO = new NotifyDTO();

        notifyDTO.setId( notifyRecord.getNotifyId() );

        return notifyDTO;
    }

    protected UserDTO notifyRecordToUserDTO(NotifyRecord notifyRecord) {
        if ( notifyRecord == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notifyRecord.getUserId() );

        return userDTO;
    }

    protected UserDTO notifyRecordToUserDTO1(NotifyRecord notifyRecord) {
        if ( notifyRecord == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notifyRecord.getCreateById() );

        return userDTO;
    }

    protected UserDTO notifyRecordToUserDTO2(NotifyRecord notifyRecord) {
        if ( notifyRecord == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( notifyRecord.getUpdateById() );

        return userDTO;
    }
}
