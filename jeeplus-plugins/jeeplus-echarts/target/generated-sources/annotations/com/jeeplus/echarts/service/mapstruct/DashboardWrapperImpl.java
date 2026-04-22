package com.jeeplus.echarts.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.echarts.domain.Dashboard;
import com.jeeplus.echarts.service.dto.DashboardDTO;
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
public class DashboardWrapperImpl implements DashboardWrapper {

    @Override
    public Dashboard toEntity(DashboardDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Dashboard dashboard = new Dashboard();

        dashboard.setCreateById( dtoCreateById( dto ) );
        dashboard.setUpdateById( dtoUpdateById( dto ) );
        dashboard.setId( dto.getId() );
        dashboard.setCreateTime( dto.getCreateTime() );
        dashboard.setUpdateTime( dto.getUpdateTime() );
        dashboard.setDelFlag( dto.getDelFlag() );
        dashboard.setName( dto.getName() );
        dashboard.setGrid( dto.getGrid() );
        dashboard.setRemarks( dto.getRemarks() );

        return dashboard;
    }

    @Override
    public DashboardDTO toDTO(Dashboard entity) {
        if ( entity == null ) {
            return null;
        }

        DashboardDTO dashboardDTO = new DashboardDTO();

        dashboardDTO.setCreateBy( dashboardToUserDTO( entity ) );
        dashboardDTO.setUpdateBy( dashboardToUserDTO1( entity ) );
        dashboardDTO.setId( entity.getId() );
        dashboardDTO.setCreateTime( entity.getCreateTime() );
        dashboardDTO.setUpdateTime( entity.getUpdateTime() );
        dashboardDTO.setDelFlag( entity.getDelFlag() );
        dashboardDTO.setName( entity.getName() );
        dashboardDTO.setGrid( entity.getGrid() );
        dashboardDTO.setRemarks( entity.getRemarks() );

        return dashboardDTO;
    }

    @Override
    public List<Dashboard> toEntity(List<DashboardDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dashboard> list = new ArrayList<Dashboard>( dtoList.size() );
        for ( DashboardDTO dashboardDTO : dtoList ) {
            list.add( toEntity( dashboardDTO ) );
        }

        return list;
    }

    @Override
    public List<DashboardDTO> toDTO(List<Dashboard> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DashboardDTO> list = new ArrayList<DashboardDTO>( entityList.size() );
        for ( Dashboard dashboard : entityList ) {
            list.add( toDTO( dashboard ) );
        }

        return list;
    }

    @Override
    public Page<Dashboard> toEntity(Page<DashboardDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Dashboard> page1 = new Page<Dashboard>();

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
    public Page<DashboardDTO> toDTO(Page<Dashboard> page) {
        if ( page == null ) {
            return null;
        }

        Page<DashboardDTO> page1 = new Page<DashboardDTO>();

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

    private String dtoCreateById(DashboardDTO dashboardDTO) {
        if ( dashboardDTO == null ) {
            return null;
        }
        UserDTO createBy = dashboardDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(DashboardDTO dashboardDTO) {
        if ( dashboardDTO == null ) {
            return null;
        }
        UserDTO updateBy = dashboardDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO dashboardToUserDTO(Dashboard dashboard) {
        if ( dashboard == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dashboard.getCreateById() );

        return userDTO;
    }

    protected UserDTO dashboardToUserDTO1(Dashboard dashboard) {
        if ( dashboard == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( dashboard.getUpdateById() );

        return userDTO;
    }
}
