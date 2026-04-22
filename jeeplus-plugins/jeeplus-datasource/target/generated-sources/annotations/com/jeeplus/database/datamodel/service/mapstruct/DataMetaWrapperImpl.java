package com.jeeplus.database.datamodel.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.database.datamodel.domain.DataMeta;
import com.jeeplus.database.datamodel.service.dto.DataMetaDTO;
import com.jeeplus.database.datamodel.service.dto.DataSetDTO;
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
public class DataMetaWrapperImpl implements DataMetaWrapper {

    @Override
    public List<DataMeta> toEntity(List<DataMetaDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DataMeta> list = new ArrayList<DataMeta>( dtoList.size() );
        for ( DataMetaDTO dataMetaDTO : dtoList ) {
            list.add( toEntity( dataMetaDTO ) );
        }

        return list;
    }

    @Override
    public List<DataMetaDTO> toDTO(List<DataMeta> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DataMetaDTO> list = new ArrayList<DataMetaDTO>( entityList.size() );
        for ( DataMeta dataMeta : entityList ) {
            list.add( toDTO( dataMeta ) );
        }

        return list;
    }

    @Override
    public Page<DataMeta> toEntity(Page<DataMetaDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataMeta> page1 = new Page<DataMeta>();

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
    public Page<DataMetaDTO> toDTO(Page<DataMeta> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataMetaDTO> page1 = new Page<DataMetaDTO>();

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
    public DataMeta toEntity(DataMetaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DataMeta dataMeta = new DataMeta();

        dataMeta.setDataSetId( dtoDataSetDTOId( dto ) );
        dataMeta.setCreateById( dtoCreateById( dto ) );
        dataMeta.setUpdateById( dtoUpdateById( dto ) );
        dataMeta.setId( dto.getId() );
        dataMeta.setCreateTime( dto.getCreateTime() );
        dataMeta.setUpdateTime( dto.getUpdateTime() );
        dataMeta.setDelFlag( dto.getDelFlag() );
        dataMeta.setName( dto.getName() );
        dataMeta.setLabel( dto.getLabel() );
        dataMeta.setType( dto.getType() );
        dataMeta.setIsNeed( dto.getIsNeed() );
        dataMeta.setSort( dto.getSort() );

        return dataMeta;
    }

    @Override
    public DataMetaDTO toDTO(DataMeta entity) {
        if ( entity == null ) {
            return null;
        }

        DataMetaDTO dataMetaDTO = new DataMetaDTO();

        dataMetaDTO.setDataSetDTO( dataMetaToDataSetDTO( entity ) );
        dataMetaDTO.setCreateBy( dataMetaToUserDTO( entity ) );
        dataMetaDTO.setUpdateBy( dataMetaToUserDTO1( entity ) );
        dataMetaDTO.setId( entity.getId() );
        dataMetaDTO.setCreateTime( entity.getCreateTime() );
        dataMetaDTO.setUpdateTime( entity.getUpdateTime() );
        dataMetaDTO.setDelFlag( entity.getDelFlag() );
        dataMetaDTO.setName( entity.getName() );
        dataMetaDTO.setLabel( entity.getLabel() );
        dataMetaDTO.setType( entity.getType() );
        dataMetaDTO.setIsNeed( entity.getIsNeed() );
        dataMetaDTO.setSort( entity.getSort() );

        return dataMetaDTO;
    }

    private String dtoDataSetDTOId(DataMetaDTO dataMetaDTO) {
        if ( dataMetaDTO == null ) {
            return null;
        }
        DataSetDTO dataSetDTO = dataMetaDTO.getDataSetDTO();
        if ( dataSetDTO == null ) {
            return null;
        }
        String id = dataSetDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(DataMetaDTO dataMetaDTO) {
        if ( dataMetaDTO == null ) {
            return null;
        }
        UserDTO createBy = dataMetaDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(DataMetaDTO dataMetaDTO) {
        if ( dataMetaDTO == null ) {
            return null;
        }
        UserDTO updateBy = dataMetaDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected DataSetDTO dataMetaToDataSetDTO(DataMeta dataMeta) {
        if ( dataMeta == null ) {
            return null;
        }

        DataSetDTO dataSetDTO = new DataSetDTO();

        dataSetDTO.setId( dataMeta.getDataSetId() );

        return dataSetDTO;
    }

    protected UserDTO dataMetaToUserDTO(DataMeta dataMeta) {
        if ( dataMeta == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataMeta.getCreateById() );

        return userDTO;
    }

    protected UserDTO dataMetaToUserDTO1(DataMeta dataMeta) {
        if ( dataMeta == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataMeta.getUpdateById() );

        return userDTO;
    }
}
