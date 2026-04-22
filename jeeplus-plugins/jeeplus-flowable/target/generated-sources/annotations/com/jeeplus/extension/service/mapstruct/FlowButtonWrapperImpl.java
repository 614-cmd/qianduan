package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.FlowButton;
import com.jeeplus.extension.service.dto.FlowButtonDTO;
import com.jeeplus.extension.service.dto.TaskDefExtensionDTO;
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
public class FlowButtonWrapperImpl implements FlowButtonWrapper {

    @Override
    public List<FlowButton> toEntity(List<FlowButtonDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FlowButton> list = new ArrayList<FlowButton>( dtoList.size() );
        for ( FlowButtonDTO flowButtonDTO : dtoList ) {
            list.add( toEntity( flowButtonDTO ) );
        }

        return list;
    }

    @Override
    public List<FlowButtonDTO> toDTO(List<FlowButton> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FlowButtonDTO> list = new ArrayList<FlowButtonDTO>( entityList.size() );
        for ( FlowButton flowButton : entityList ) {
            list.add( toDTO( flowButton ) );
        }

        return list;
    }

    @Override
    public Page<FlowButton> toEntity(Page<FlowButtonDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<FlowButton> page1 = new Page<FlowButton>();

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
    public Page<FlowButtonDTO> toDTO(Page<FlowButton> page) {
        if ( page == null ) {
            return null;
        }

        Page<FlowButtonDTO> page1 = new Page<FlowButtonDTO>();

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
    public FlowButton toEntity(FlowButtonDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FlowButton flowButton = new FlowButton();

        flowButton.setTaskDefId( dtoTaskDefId( dto ) );
        flowButton.setCreateById( dtoCreateById( dto ) );
        flowButton.setUpdateById( dtoUpdateById( dto ) );
        flowButton.setId( dto.getId() );
        flowButton.setCreateTime( dto.getCreateTime() );
        flowButton.setUpdateTime( dto.getUpdateTime() );
        flowButton.setDelFlag( dto.getDelFlag() );
        flowButton.setName( dto.getName() );
        flowButton.setCode( dto.getCode() );
        flowButton.setIsHide( dto.getIsHide() );
        flowButton.setNextAssignee( dto.getNextAssignee() );
        flowButton.setSort( dto.getSort() );

        return flowButton;
    }

    @Override
    public FlowButtonDTO toDTO(FlowButton entity) {
        if ( entity == null ) {
            return null;
        }

        FlowButtonDTO flowButtonDTO = new FlowButtonDTO();

        flowButtonDTO.setTaskDef( flowButtonToTaskDefExtensionDTO( entity ) );
        flowButtonDTO.setCreateBy( flowButtonToUserDTO( entity ) );
        flowButtonDTO.setUpdateBy( flowButtonToUserDTO1( entity ) );
        flowButtonDTO.setId( entity.getId() );
        flowButtonDTO.setCreateTime( entity.getCreateTime() );
        flowButtonDTO.setUpdateTime( entity.getUpdateTime() );
        flowButtonDTO.setDelFlag( entity.getDelFlag() );
        flowButtonDTO.setName( entity.getName() );
        flowButtonDTO.setCode( entity.getCode() );
        flowButtonDTO.setIsHide( entity.getIsHide() );
        flowButtonDTO.setNextAssignee( entity.getNextAssignee() );
        flowButtonDTO.setSort( entity.getSort() );

        return flowButtonDTO;
    }

    private String dtoTaskDefId(FlowButtonDTO flowButtonDTO) {
        if ( flowButtonDTO == null ) {
            return null;
        }
        TaskDefExtensionDTO taskDef = flowButtonDTO.getTaskDef();
        if ( taskDef == null ) {
            return null;
        }
        String id = taskDef.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(FlowButtonDTO flowButtonDTO) {
        if ( flowButtonDTO == null ) {
            return null;
        }
        UserDTO createBy = flowButtonDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(FlowButtonDTO flowButtonDTO) {
        if ( flowButtonDTO == null ) {
            return null;
        }
        UserDTO updateBy = flowButtonDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TaskDefExtensionDTO flowButtonToTaskDefExtensionDTO(FlowButton flowButton) {
        if ( flowButton == null ) {
            return null;
        }

        TaskDefExtensionDTO taskDefExtensionDTO = new TaskDefExtensionDTO();

        taskDefExtensionDTO.setId( flowButton.getTaskDefId() );

        return taskDefExtensionDTO;
    }

    protected UserDTO flowButtonToUserDTO(FlowButton flowButton) {
        if ( flowButton == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( flowButton.getCreateById() );

        return userDTO;
    }

    protected UserDTO flowButtonToUserDTO1(FlowButton flowButton) {
        if ( flowButton == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( flowButton.getUpdateById() );

        return userDTO;
    }
}
