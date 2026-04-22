package com.jeeplus.calendar.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.calendar.domain.MyCalendar;
import com.jeeplus.calendar.service.dto.MyCalendarDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:30+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class MyCalendarWrapperImpl implements MyCalendarWrapper {

    @Override
    public List<MyCalendar> toEntity(List<MyCalendarDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MyCalendar> list = new ArrayList<MyCalendar>( dtoList.size() );
        for ( MyCalendarDTO myCalendarDTO : dtoList ) {
            list.add( toEntity( myCalendarDTO ) );
        }

        return list;
    }

    @Override
    public List<MyCalendarDTO> toDTO(List<MyCalendar> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MyCalendarDTO> list = new ArrayList<MyCalendarDTO>( entityList.size() );
        for ( MyCalendar myCalendar : entityList ) {
            list.add( toDTO( myCalendar ) );
        }

        return list;
    }

    @Override
    public Page<MyCalendar> toEntity(Page<MyCalendarDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<MyCalendar> page1 = new Page<MyCalendar>();

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
    public Page<MyCalendarDTO> toDTO(Page<MyCalendar> page) {
        if ( page == null ) {
            return null;
        }

        Page<MyCalendarDTO> page1 = new Page<MyCalendarDTO>();

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
    public MyCalendar toEntity(MyCalendarDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MyCalendar myCalendar = new MyCalendar();

        myCalendar.setUserId( dtoUserDTOId( dto ) );
        myCalendar.setId( dto.getId() );
        myCalendar.setTitle( dto.getTitle() );
        myCalendar.setStartDate( dto.getStartDate() );
        myCalendar.setEndDate( dto.getEndDate() );
        myCalendar.setAllDay( dto.getAllDay() );
        myCalendar.setColor( dto.getColor() );

        return myCalendar;
    }

    @Override
    public MyCalendarDTO toDTO(MyCalendar entity) {
        if ( entity == null ) {
            return null;
        }

        MyCalendarDTO myCalendarDTO = new MyCalendarDTO();

        myCalendarDTO.setUserDTO( myCalendarToUserDTO( entity ) );
        myCalendarDTO.setId( entity.getId() );
        myCalendarDTO.setTitle( entity.getTitle() );
        myCalendarDTO.setStartDate( entity.getStartDate() );
        myCalendarDTO.setEndDate( entity.getEndDate() );
        myCalendarDTO.setAllDay( entity.getAllDay() );
        myCalendarDTO.setColor( entity.getColor() );

        return myCalendarDTO;
    }

    private String dtoUserDTOId(MyCalendarDTO myCalendarDTO) {
        if ( myCalendarDTO == null ) {
            return null;
        }
        UserDTO userDTO = myCalendarDTO.getUserDTO();
        if ( userDTO == null ) {
            return null;
        }
        String id = userDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO myCalendarToUserDTO(MyCalendar myCalendar) {
        if ( myCalendar == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( myCalendar.getUserId() );

        return userDTO;
    }
}
