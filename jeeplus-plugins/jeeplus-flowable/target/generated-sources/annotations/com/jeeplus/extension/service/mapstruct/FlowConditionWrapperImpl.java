package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.FlowCondition;
import com.jeeplus.extension.service.dto.FlowConditionDTO;
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
public class FlowConditionWrapperImpl implements FlowConditionWrapper {

    @Override
    public List<FlowCondition> toEntity(List<FlowConditionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<FlowCondition> list = new ArrayList<FlowCondition>( dtoList.size() );
        for ( FlowConditionDTO flowConditionDTO : dtoList ) {
            list.add( toEntity( flowConditionDTO ) );
        }

        return list;
    }

    @Override
    public List<FlowConditionDTO> toDTO(List<FlowCondition> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FlowConditionDTO> list = new ArrayList<FlowConditionDTO>( entityList.size() );
        for ( FlowCondition flowCondition : entityList ) {
            list.add( toDTO( flowCondition ) );
        }

        return list;
    }

    @Override
    public Page<FlowCondition> toEntity(Page<FlowConditionDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<FlowCondition> page1 = new Page<FlowCondition>();

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
    public Page<FlowConditionDTO> toDTO(Page<FlowCondition> page) {
        if ( page == null ) {
            return null;
        }

        Page<FlowConditionDTO> page1 = new Page<FlowConditionDTO>();

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
    public FlowCondition toEntity(FlowConditionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FlowCondition flowCondition = new FlowCondition();

        flowCondition.setTaskDefId( dtoTaskDefId( dto ) );
        flowCondition.setCreateById( dtoCreateById( dto ) );
        flowCondition.setUpdateById( dtoUpdateById( dto ) );
        flowCondition.setId( dto.getId() );
        flowCondition.setCreateTime( dto.getCreateTime() );
        flowCondition.setUpdateTime( dto.getUpdateTime() );
        flowCondition.setDelFlag( dto.getDelFlag() );
        flowCondition.setField( dto.getField() );
        flowCondition.setCompare( dto.getCompare() );
        flowCondition.setValue( dto.getValue() );
        flowCondition.setLogic( dto.getLogic() );
        flowCondition.setSort( dto.getSort() );

        return flowCondition;
    }

    @Override
    public FlowConditionDTO toDTO(FlowCondition entity) {
        if ( entity == null ) {
            return null;
        }

        FlowConditionDTO flowConditionDTO = new FlowConditionDTO();

        flowConditionDTO.setTaskDef( flowConditionToTaskDefExtensionDTO( entity ) );
        flowConditionDTO.setCreateBy( flowConditionToUserDTO( entity ) );
        flowConditionDTO.setUpdateBy( flowConditionToUserDTO1( entity ) );
        flowConditionDTO.setId( entity.getId() );
        flowConditionDTO.setCreateTime( entity.getCreateTime() );
        flowConditionDTO.setUpdateTime( entity.getUpdateTime() );
        flowConditionDTO.setDelFlag( entity.getDelFlag() );
        flowConditionDTO.setField( entity.getField() );
        flowConditionDTO.setCompare( entity.getCompare() );
        flowConditionDTO.setValue( entity.getValue() );
        flowConditionDTO.setLogic( entity.getLogic() );
        flowConditionDTO.setSort( entity.getSort() );

        return flowConditionDTO;
    }

    private String dtoTaskDefId(FlowConditionDTO flowConditionDTO) {
        if ( flowConditionDTO == null ) {
            return null;
        }
        TaskDefExtensionDTO taskDef = flowConditionDTO.getTaskDef();
        if ( taskDef == null ) {
            return null;
        }
        String id = taskDef.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(FlowConditionDTO flowConditionDTO) {
        if ( flowConditionDTO == null ) {
            return null;
        }
        UserDTO createBy = flowConditionDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(FlowConditionDTO flowConditionDTO) {
        if ( flowConditionDTO == null ) {
            return null;
        }
        UserDTO updateBy = flowConditionDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TaskDefExtensionDTO flowConditionToTaskDefExtensionDTO(FlowCondition flowCondition) {
        if ( flowCondition == null ) {
            return null;
        }

        TaskDefExtensionDTO taskDefExtensionDTO = new TaskDefExtensionDTO();

        taskDefExtensionDTO.setId( flowCondition.getTaskDefId() );

        return taskDefExtensionDTO;
    }

    protected UserDTO flowConditionToUserDTO(FlowCondition flowCondition) {
        if ( flowCondition == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( flowCondition.getCreateById() );

        return userDTO;
    }

    protected UserDTO flowConditionToUserDTO1(FlowCondition flowCondition) {
        if ( flowCondition == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( flowCondition.getUpdateById() );

        return userDTO;
    }
}
