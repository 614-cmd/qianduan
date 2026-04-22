package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.FlowAssignee;
import com.jeeplus.extension.service.dto.FlowAssigneeDTO;
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
public class FlowAssigneeWrapperImpl implements FlowAssigneeWrapper {

    @Override
    public List<FlowAssignee> toEntity(List<FlowAssigneeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FlowAssignee> list = new ArrayList<FlowAssignee>( dtoList.size() );
        for ( FlowAssigneeDTO flowAssigneeDTO : dtoList ) {
            list.add( toEntity( flowAssigneeDTO ) );
        }

        return list;
    }

    @Override
    public List<FlowAssigneeDTO> toDTO(List<FlowAssignee> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FlowAssigneeDTO> list = new ArrayList<FlowAssigneeDTO>( entityList.size() );
        for ( FlowAssignee flowAssignee : entityList ) {
            list.add( toDTO( flowAssignee ) );
        }

        return list;
    }

    @Override
    public Page<FlowAssignee> toEntity(Page<FlowAssigneeDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<FlowAssignee> page1 = new Page<FlowAssignee>();

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
    public Page<FlowAssigneeDTO> toDTO(Page<FlowAssignee> page) {
        if ( page == null ) {
            return null;
        }

        Page<FlowAssigneeDTO> page1 = new Page<FlowAssigneeDTO>();

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
    public FlowAssignee toEntity(FlowAssigneeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FlowAssignee flowAssignee = new FlowAssignee();

        flowAssignee.setTaskDefId( dtoTaskDefId( dto ) );
        flowAssignee.setCreateById( dtoCreateById( dto ) );
        flowAssignee.setUpdateById( dtoUpdateById( dto ) );
        flowAssignee.setId( dto.getId() );
        flowAssignee.setCreateTime( dto.getCreateTime() );
        flowAssignee.setUpdateTime( dto.getUpdateTime() );
        flowAssignee.setDelFlag( dto.getDelFlag() );
        flowAssignee.setValue( dto.getValue() );
        flowAssignee.setCondition( dto.getCondition() );
        flowAssignee.setType( dto.getType() );
        flowAssignee.setAssignValue( dto.getAssignValue() );
        flowAssignee.setAssignCondition( dto.getAssignCondition() );
        flowAssignee.setOperationType( dto.getOperationType() );
        flowAssignee.setSort( dto.getSort() );

        return flowAssignee;
    }

    @Override
    public FlowAssigneeDTO toDTO(FlowAssignee entity) {
        if ( entity == null ) {
            return null;
        }

        FlowAssigneeDTO flowAssigneeDTO = new FlowAssigneeDTO();

        flowAssigneeDTO.setTaskDef( flowAssigneeToTaskDefExtensionDTO( entity ) );
        flowAssigneeDTO.setCreateBy( flowAssigneeToUserDTO( entity ) );
        flowAssigneeDTO.setUpdateBy( flowAssigneeToUserDTO1( entity ) );
        flowAssigneeDTO.setId( entity.getId() );
        flowAssigneeDTO.setCreateTime( entity.getCreateTime() );
        flowAssigneeDTO.setUpdateTime( entity.getUpdateTime() );
        flowAssigneeDTO.setDelFlag( entity.getDelFlag() );
        flowAssigneeDTO.setValue( entity.getValue() );
        flowAssigneeDTO.setCondition( entity.getCondition() );
        flowAssigneeDTO.setType( entity.getType() );
        flowAssigneeDTO.setAssignValue( entity.getAssignValue() );
        flowAssigneeDTO.setAssignCondition( entity.getAssignCondition() );
        flowAssigneeDTO.setOperationType( entity.getOperationType() );
        flowAssigneeDTO.setSort( entity.getSort() );

        return flowAssigneeDTO;
    }

    private String dtoTaskDefId(FlowAssigneeDTO flowAssigneeDTO) {
        if ( flowAssigneeDTO == null ) {
            return null;
        }
        TaskDefExtensionDTO taskDef = flowAssigneeDTO.getTaskDef();
        if ( taskDef == null ) {
            return null;
        }
        String id = taskDef.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(FlowAssigneeDTO flowAssigneeDTO) {
        if ( flowAssigneeDTO == null ) {
            return null;
        }
        UserDTO createBy = flowAssigneeDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(FlowAssigneeDTO flowAssigneeDTO) {
        if ( flowAssigneeDTO == null ) {
            return null;
        }
        UserDTO updateBy = flowAssigneeDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TaskDefExtensionDTO flowAssigneeToTaskDefExtensionDTO(FlowAssignee flowAssignee) {
        if ( flowAssignee == null ) {
            return null;
        }

        TaskDefExtensionDTO taskDefExtensionDTO = new TaskDefExtensionDTO();

        taskDefExtensionDTO.setId( flowAssignee.getTaskDefId() );

        return taskDefExtensionDTO;
    }

    protected UserDTO flowAssigneeToUserDTO(FlowAssignee flowAssignee) {
        if ( flowAssignee == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( flowAssignee.getCreateById() );

        return userDTO;
    }

    protected UserDTO flowAssigneeToUserDTO1(FlowAssignee flowAssignee) {
        if ( flowAssignee == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( flowAssignee.getUpdateById() );

        return userDTO;
    }
}
