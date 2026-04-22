package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.NodeSetting;
import com.jeeplus.extension.service.dto.NodeSettingDTO;
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
public class NodeSettingWrapperImpl implements NodeSettingWrapper {

    @Override
    public NodeSetting toEntity(NodeSettingDTO dto) {
        if ( dto == null ) {
            return null;
        }

        NodeSetting nodeSetting = new NodeSetting();

        nodeSetting.setCreateById( dtoCreateById( dto ) );
        nodeSetting.setUpdateById( dtoUpdateById( dto ) );
        nodeSetting.setId( dto.getId() );
        nodeSetting.setCreateTime( dto.getCreateTime() );
        nodeSetting.setUpdateTime( dto.getUpdateTime() );
        nodeSetting.setDelFlag( dto.getDelFlag() );
        nodeSetting.setKey( dto.getKey() );
        nodeSetting.setValue( dto.getValue() );
        nodeSetting.setProcessDefId( dto.getProcessDefId() );
        nodeSetting.setTaskDefId( dto.getTaskDefId() );
        nodeSetting.setNodeKey( dto.getNodeKey() );
        nodeSetting.setNodeValue( dto.getNodeValue() );

        return nodeSetting;
    }

    @Override
    public NodeSettingDTO toDTO(NodeSetting entity) {
        if ( entity == null ) {
            return null;
        }

        NodeSettingDTO nodeSettingDTO = new NodeSettingDTO();

        nodeSettingDTO.setCreateBy( nodeSettingToUserDTO( entity ) );
        nodeSettingDTO.setUpdateBy( nodeSettingToUserDTO1( entity ) );
        nodeSettingDTO.setId( entity.getId() );
        nodeSettingDTO.setCreateTime( entity.getCreateTime() );
        nodeSettingDTO.setUpdateTime( entity.getUpdateTime() );
        nodeSettingDTO.setDelFlag( entity.getDelFlag() );
        nodeSettingDTO.setKey( entity.getKey() );
        nodeSettingDTO.setValue( entity.getValue() );
        nodeSettingDTO.setProcessDefId( entity.getProcessDefId() );
        nodeSettingDTO.setTaskDefId( entity.getTaskDefId() );
        nodeSettingDTO.setNodeKey( entity.getNodeKey() );
        nodeSettingDTO.setNodeValue( entity.getNodeValue() );

        return nodeSettingDTO;
    }

    @Override
    public List<NodeSetting> toEntity(List<NodeSettingDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<NodeSetting> list = new ArrayList<NodeSetting>( dtoList.size() );
        for ( NodeSettingDTO nodeSettingDTO : dtoList ) {
            list.add( toEntity( nodeSettingDTO ) );
        }

        return list;
    }

    @Override
    public List<NodeSettingDTO> toDTO(List<NodeSetting> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NodeSettingDTO> list = new ArrayList<NodeSettingDTO>( entityList.size() );
        for ( NodeSetting nodeSetting : entityList ) {
            list.add( toDTO( nodeSetting ) );
        }

        return list;
    }

    @Override
    public Page<NodeSetting> toEntity(Page<NodeSettingDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<NodeSetting> page1 = new Page<NodeSetting>();

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
    public Page<NodeSettingDTO> toDTO(Page<NodeSetting> page) {
        if ( page == null ) {
            return null;
        }

        Page<NodeSettingDTO> page1 = new Page<NodeSettingDTO>();

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

    private String dtoCreateById(NodeSettingDTO nodeSettingDTO) {
        if ( nodeSettingDTO == null ) {
            return null;
        }
        UserDTO createBy = nodeSettingDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(NodeSettingDTO nodeSettingDTO) {
        if ( nodeSettingDTO == null ) {
            return null;
        }
        UserDTO updateBy = nodeSettingDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO nodeSettingToUserDTO(NodeSetting nodeSetting) {
        if ( nodeSetting == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( nodeSetting.getCreateById() );

        return userDTO;
    }

    protected UserDTO nodeSettingToUserDTO1(NodeSetting nodeSetting) {
        if ( nodeSetting == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( nodeSetting.getUpdateById() );

        return userDTO;
    }
}
