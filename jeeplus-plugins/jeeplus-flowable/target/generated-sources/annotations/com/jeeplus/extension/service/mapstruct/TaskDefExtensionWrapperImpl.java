package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.TaskDefExtension;
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
public class TaskDefExtensionWrapperImpl implements TaskDefExtensionWrapper {

    @Override
    public TaskDefExtension toEntity(TaskDefExtensionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TaskDefExtension taskDefExtension = new TaskDefExtension();

        taskDefExtension.setCreateById( dtoCreateById( dto ) );
        taskDefExtension.setUpdateById( dtoUpdateById( dto ) );
        taskDefExtension.setId( dto.getId() );
        taskDefExtension.setCreateTime( dto.getCreateTime() );
        taskDefExtension.setUpdateTime( dto.getUpdateTime() );
        taskDefExtension.setDelFlag( dto.getDelFlag() );
        taskDefExtension.setProcessDefId( dto.getProcessDefId() );
        taskDefExtension.setTaskDefId( dto.getTaskDefId() );

        return taskDefExtension;
    }

    @Override
    public TaskDefExtensionDTO toDTO(TaskDefExtension entity) {
        if ( entity == null ) {
            return null;
        }

        TaskDefExtensionDTO taskDefExtensionDTO = new TaskDefExtensionDTO();

        taskDefExtensionDTO.setCreateBy( taskDefExtensionToUserDTO( entity ) );
        taskDefExtensionDTO.setUpdateBy( taskDefExtensionToUserDTO1( entity ) );
        taskDefExtensionDTO.setId( entity.getId() );
        taskDefExtensionDTO.setCreateTime( entity.getCreateTime() );
        taskDefExtensionDTO.setUpdateTime( entity.getUpdateTime() );
        taskDefExtensionDTO.setDelFlag( entity.getDelFlag() );
        taskDefExtensionDTO.setProcessDefId( entity.getProcessDefId() );
        taskDefExtensionDTO.setTaskDefId( entity.getTaskDefId() );

        return taskDefExtensionDTO;
    }

    @Override
    public List<TaskDefExtension> toEntity(List<TaskDefExtensionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TaskDefExtension> list = new ArrayList<TaskDefExtension>( dtoList.size() );
        for ( TaskDefExtensionDTO taskDefExtensionDTO : dtoList ) {
            list.add( toEntity( taskDefExtensionDTO ) );
        }

        return list;
    }

    @Override
    public List<TaskDefExtensionDTO> toDTO(List<TaskDefExtension> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TaskDefExtensionDTO> list = new ArrayList<TaskDefExtensionDTO>( entityList.size() );
        for ( TaskDefExtension taskDefExtension : entityList ) {
            list.add( toDTO( taskDefExtension ) );
        }

        return list;
    }

    @Override
    public Page<TaskDefExtension> toEntity(Page<TaskDefExtensionDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TaskDefExtension> page1 = new Page<TaskDefExtension>();

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
    public Page<TaskDefExtensionDTO> toDTO(Page<TaskDefExtension> page) {
        if ( page == null ) {
            return null;
        }

        Page<TaskDefExtensionDTO> page1 = new Page<TaskDefExtensionDTO>();

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

    private String dtoCreateById(TaskDefExtensionDTO taskDefExtensionDTO) {
        if ( taskDefExtensionDTO == null ) {
            return null;
        }
        UserDTO createBy = taskDefExtensionDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TaskDefExtensionDTO taskDefExtensionDTO) {
        if ( taskDefExtensionDTO == null ) {
            return null;
        }
        UserDTO updateBy = taskDefExtensionDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO taskDefExtensionToUserDTO(TaskDefExtension taskDefExtension) {
        if ( taskDefExtension == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( taskDefExtension.getCreateById() );

        return userDTO;
    }

    protected UserDTO taskDefExtensionToUserDTO1(TaskDefExtension taskDefExtension) {
        if ( taskDefExtension == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( taskDefExtension.getUpdateById() );

        return userDTO;
    }
}
