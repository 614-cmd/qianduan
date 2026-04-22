package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.FlowCopy;
import com.jeeplus.extension.service.dto.FlowCopyDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:39+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class FlowCopyWrapperImpl implements FlowCopyWrapper {

    @Override
    public FlowCopy toEntity(FlowCopyDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FlowCopy flowCopy = new FlowCopy();

        flowCopy.setCreateById( dtoCreateById( dto ) );
        flowCopy.setUpdateById( dtoUpdateById( dto ) );
        flowCopy.setId( dto.getId() );
        flowCopy.setCreateTime( dto.getCreateTime() );
        flowCopy.setUpdateTime( dto.getUpdateTime() );
        flowCopy.setDelFlag( dto.getDelFlag() );
        flowCopy.setUserId( dto.getUserId() );
        flowCopy.setProcDefId( dto.getProcDefId() );
        flowCopy.setProcInsId( dto.getProcInsId() );
        flowCopy.setProcDefName( dto.getProcDefName() );
        flowCopy.setProcInsName( dto.getProcInsName() );
        flowCopy.setTaskName( dto.getTaskName() );

        return flowCopy;
    }

    @Override
    public FlowCopyDTO toDTO(FlowCopy entity) {
        if ( entity == null ) {
            return null;
        }

        FlowCopyDTO flowCopyDTO = new FlowCopyDTO();

        flowCopyDTO.setCreateBy( flowCopyToUserDTO( entity ) );
        flowCopyDTO.setUpdateBy( flowCopyToUserDTO1( entity ) );
        flowCopyDTO.setId( entity.getId() );
        flowCopyDTO.setCreateTime( entity.getCreateTime() );
        flowCopyDTO.setUpdateTime( entity.getUpdateTime() );
        flowCopyDTO.setDelFlag( entity.getDelFlag() );
        flowCopyDTO.setUserId( entity.getUserId() );
        flowCopyDTO.setProcDefId( entity.getProcDefId() );
        flowCopyDTO.setProcInsId( entity.getProcInsId() );
        flowCopyDTO.setProcDefName( entity.getProcDefName() );
        flowCopyDTO.setProcInsName( entity.getProcInsName() );
        flowCopyDTO.setTaskName( entity.getTaskName() );

        return flowCopyDTO;
    }

    @Override
    public List<FlowCopy> toEntity(List<FlowCopyDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FlowCopy> list = new ArrayList<FlowCopy>( dtoList.size() );
        for ( FlowCopyDTO flowCopyDTO : dtoList ) {
            list.add( toEntity( flowCopyDTO ) );
        }

        return list;
    }

    @Override
    public List<FlowCopyDTO> toDTO(List<FlowCopy> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FlowCopyDTO> list = new ArrayList<FlowCopyDTO>( entityList.size() );
        for ( FlowCopy flowCopy : entityList ) {
            list.add( toDTO( flowCopy ) );
        }

        return list;
    }

    @Override
    public Page<FlowCopy> toEntity(Page<FlowCopyDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<FlowCopy> page1 = new Page<FlowCopy>();

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
    public Page<FlowCopyDTO> toDTO(Page<FlowCopy> page) {
        if ( page == null ) {
            return null;
        }

        Page<FlowCopyDTO> page1 = new Page<FlowCopyDTO>();

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

    private String dtoCreateById(FlowCopyDTO flowCopyDTO) {
        if ( flowCopyDTO == null ) {
            return null;
        }
        UserDTO createBy = flowCopyDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(FlowCopyDTO flowCopyDTO) {
        if ( flowCopyDTO == null ) {
            return null;
        }
        UserDTO updateBy = flowCopyDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO flowCopyToUserDTO(FlowCopy flowCopy) {
        if ( flowCopy == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( flowCopy.getCreateById() );

        return userDTO;
    }

    protected UserDTO flowCopyToUserDTO1(FlowCopy flowCopy) {
        if ( flowCopy == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( flowCopy.getUpdateById() );

        return userDTO;
    }
}
