package com.jeeplus.database.datamodel.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.database.datamodel.domain.DataParam;
import com.jeeplus.database.datamodel.service.dto.DataParamDTO;
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
public class DataParamWrapperImpl implements DataParamWrapper {

    @Override
    public List<DataParam> toEntity(List<DataParamDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DataParam> list = new ArrayList<DataParam>( dtoList.size() );
        for ( DataParamDTO dataParamDTO : dtoList ) {
            list.add( toEntity( dataParamDTO ) );
        }

        return list;
    }

    @Override
    public List<DataParamDTO> toDTO(List<DataParam> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DataParamDTO> list = new ArrayList<DataParamDTO>( entityList.size() );
        for ( DataParam dataParam : entityList ) {
            list.add( toDTO( dataParam ) );
        }

        return list;
    }

    @Override
    public Page<DataParam> toEntity(Page<DataParamDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataParam> page1 = new Page<DataParam>();

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
    public Page<DataParamDTO> toDTO(Page<DataParam> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataParamDTO> page1 = new Page<DataParamDTO>();

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
    public DataParam toEntity(DataParamDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DataParam dataParam = new DataParam();

        dataParam.setDataSetId( dtoDataSetDTOId( dto ) );
        dataParam.setCreateById( dtoCreateById( dto ) );
        dataParam.setUpdateById( dtoUpdateById( dto ) );
        dataParam.setId( dto.getId() );
        dataParam.setCreateTime( dto.getCreateTime() );
        dataParam.setUpdateTime( dto.getUpdateTime() );
        dataParam.setDelFlag( dto.getDelFlag() );
        dataParam.setField( dto.getField() );
        dataParam.setDefaultValue( dto.getDefaultValue() );
        dataParam.setSort( dto.getSort() );

        return dataParam;
    }

    @Override
    public DataParamDTO toDTO(DataParam entity) {
        if ( entity == null ) {
            return null;
        }

        DataParamDTO dataParamDTO = new DataParamDTO();

        dataParamDTO.setDataSetDTO( dataParamToDataSetDTO( entity ) );
        dataParamDTO.setCreateBy( dataParamToUserDTO( entity ) );
        dataParamDTO.setUpdateBy( dataParamToUserDTO1( entity ) );
        dataParamDTO.setId( entity.getId() );
        dataParamDTO.setCreateTime( entity.getCreateTime() );
        dataParamDTO.setUpdateTime( entity.getUpdateTime() );
        dataParamDTO.setDelFlag( entity.getDelFlag() );
        dataParamDTO.setField( entity.getField() );
        dataParamDTO.setDefaultValue( entity.getDefaultValue() );
        dataParamDTO.setSort( entity.getSort() );

        return dataParamDTO;
    }

    private String dtoDataSetDTOId(DataParamDTO dataParamDTO) {
        if ( dataParamDTO == null ) {
            return null;
        }
        DataSetDTO dataSetDTO = dataParamDTO.getDataSetDTO();
        if ( dataSetDTO == null ) {
            return null;
        }
        String id = dataSetDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(DataParamDTO dataParamDTO) {
        if ( dataParamDTO == null ) {
            return null;
        }
        UserDTO createBy = dataParamDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(DataParamDTO dataParamDTO) {
        if ( dataParamDTO == null ) {
            return null;
        }
        UserDTO updateBy = dataParamDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected DataSetDTO dataParamToDataSetDTO(DataParam dataParam) {
        if ( dataParam == null ) {
            return null;
        }

        DataSetDTO dataSetDTO = new DataSetDTO();

        dataSetDTO.setId( dataParam.getDataSetId() );

        return dataSetDTO;
    }

    protected UserDTO dataParamToUserDTO(DataParam dataParam) {
        if ( dataParam == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataParam.getCreateById() );

        return userDTO;
    }

    protected UserDTO dataParamToUserDTO1(DataParam dataParam) {
        if ( dataParam == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataParam.getUpdateById() );

        return userDTO;
    }
}
