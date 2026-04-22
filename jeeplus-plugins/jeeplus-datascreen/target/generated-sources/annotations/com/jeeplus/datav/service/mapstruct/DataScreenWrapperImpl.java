package com.jeeplus.datav.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.datav.domain.DataScreen;
import com.jeeplus.datav.service.dto.DataScreenCategoryDTO;
import com.jeeplus.datav.service.dto.DataScreenDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:35+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class DataScreenWrapperImpl implements DataScreenWrapper {

    @Override
    public List<DataScreen> toEntity(List<DataScreenDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DataScreen> list = new ArrayList<DataScreen>( dtoList.size() );
        for ( DataScreenDTO dataScreenDTO : dtoList ) {
            list.add( toEntity( dataScreenDTO ) );
        }

        return list;
    }

    @Override
    public List<DataScreenDTO> toDTO(List<DataScreen> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DataScreenDTO> list = new ArrayList<DataScreenDTO>( entityList.size() );
        for ( DataScreen dataScreen : entityList ) {
            list.add( toDTO( dataScreen ) );
        }

        return list;
    }

    @Override
    public Page<DataScreen> toEntity(Page<DataScreenDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataScreen> page1 = new Page<DataScreen>();

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
    public Page<DataScreenDTO> toDTO(Page<DataScreen> page) {
        if ( page == null ) {
            return null;
        }

        Page<DataScreenDTO> page1 = new Page<DataScreenDTO>();

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
    public DataScreen toEntity(DataScreenDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DataScreen dataScreen = new DataScreen();

        dataScreen.setCategoryId( dtoCategoryId( dto ) );
        dataScreen.setCreateById( dtoCreateById( dto ) );
        dataScreen.setUpdateById( dtoUpdateById( dto ) );
        dataScreen.setId( dto.getId() );
        dataScreen.setCreateTime( dto.getCreateTime() );
        dataScreen.setUpdateTime( dto.getUpdateTime() );
        dataScreen.setDelFlag( dto.getDelFlag() );
        dataScreen.setComponent( dto.getComponent() );
        dataScreen.setDetail( dto.getDetail() );
        dataScreen.setStatus( dto.getStatus() );
        dataScreen.setName( dto.getName() );
        dataScreen.setScreenShot( dto.getScreenShot() );
        dataScreen.setBackgroundUrl( dto.getBackgroundUrl() );
        dataScreen.setRemarks( dto.getRemarks() );

        return dataScreen;
    }

    @Override
    public DataScreenDTO toDTO(DataScreen entity) {
        if ( entity == null ) {
            return null;
        }

        DataScreenDTO dataScreenDTO = new DataScreenDTO();

        dataScreenDTO.setCategory( dataScreenToDataScreenCategoryDTO( entity ) );
        dataScreenDTO.setCreateBy( dataScreenToUserDTO( entity ) );
        dataScreenDTO.setUpdateBy( dataScreenToUserDTO1( entity ) );
        dataScreenDTO.setId( entity.getId() );
        dataScreenDTO.setCreateTime( entity.getCreateTime() );
        dataScreenDTO.setUpdateTime( entity.getUpdateTime() );
        dataScreenDTO.setDelFlag( entity.getDelFlag() );
        dataScreenDTO.setComponent( entity.getComponent() );
        dataScreenDTO.setDetail( entity.getDetail() );
        dataScreenDTO.setStatus( entity.getStatus() );
        dataScreenDTO.setName( entity.getName() );
        dataScreenDTO.setScreenShot( entity.getScreenShot() );
        dataScreenDTO.setBackgroundUrl( entity.getBackgroundUrl() );
        dataScreenDTO.setRemarks( entity.getRemarks() );

        return dataScreenDTO;
    }

    private String dtoCategoryId(DataScreenDTO dataScreenDTO) {
        if ( dataScreenDTO == null ) {
            return null;
        }
        DataScreenCategoryDTO category = dataScreenDTO.getCategory();
        if ( category == null ) {
            return null;
        }
        String id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(DataScreenDTO dataScreenDTO) {
        if ( dataScreenDTO == null ) {
            return null;
        }
        UserDTO createBy = dataScreenDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(DataScreenDTO dataScreenDTO) {
        if ( dataScreenDTO == null ) {
            return null;
        }
        UserDTO updateBy = dataScreenDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected DataScreenCategoryDTO dataScreenToDataScreenCategoryDTO(DataScreen dataScreen) {
        if ( dataScreen == null ) {
            return null;
        }

        DataScreenCategoryDTO dataScreenCategoryDTO = new DataScreenCategoryDTO();

        dataScreenCategoryDTO.setId( dataScreen.getCategoryId() );

        return dataScreenCategoryDTO;
    }

    protected UserDTO dataScreenToUserDTO(DataScreen dataScreen) {
        if ( dataScreen == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataScreen.getCreateById() );

        return userDTO;
    }

    protected UserDTO dataScreenToUserDTO1(DataScreen dataScreen) {
        if ( dataScreen == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dataScreen.getUpdateById() );

        return userDTO;
    }
}
