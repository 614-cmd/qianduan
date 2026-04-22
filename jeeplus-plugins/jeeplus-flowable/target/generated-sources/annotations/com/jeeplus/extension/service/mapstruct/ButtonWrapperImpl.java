package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.Button;
import com.jeeplus.extension.service.dto.ButtonDTO;
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
public class ButtonWrapperImpl implements ButtonWrapper {

    @Override
    public Button toEntity(ButtonDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Button button = new Button();

        button.setCreateById( dtoCreateById( dto ) );
        button.setUpdateById( dtoUpdateById( dto ) );
        button.setId( dto.getId() );
        button.setCreateTime( dto.getCreateTime() );
        button.setUpdateTime( dto.getUpdateTime() );
        button.setDelFlag( dto.getDelFlag() );
        button.setName( dto.getName() );
        button.setCode( dto.getCode() );
        button.setSort( dto.getSort() );

        return button;
    }

    @Override
    public ButtonDTO toDTO(Button entity) {
        if ( entity == null ) {
            return null;
        }

        ButtonDTO buttonDTO = new ButtonDTO();

        buttonDTO.setCreateBy( buttonToUserDTO( entity ) );
        buttonDTO.setUpdateBy( buttonToUserDTO1( entity ) );
        buttonDTO.setId( entity.getId() );
        buttonDTO.setCreateTime( entity.getCreateTime() );
        buttonDTO.setUpdateTime( entity.getUpdateTime() );
        buttonDTO.setDelFlag( entity.getDelFlag() );
        buttonDTO.setName( entity.getName() );
        buttonDTO.setCode( entity.getCode() );
        buttonDTO.setSort( entity.getSort() );

        return buttonDTO;
    }

    @Override
    public List<Button> toEntity(List<ButtonDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Button> list = new ArrayList<Button>( dtoList.size() );
        for ( ButtonDTO buttonDTO : dtoList ) {
            list.add( toEntity( buttonDTO ) );
        }

        return list;
    }

    @Override
    public List<ButtonDTO> toDTO(List<Button> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ButtonDTO> list = new ArrayList<ButtonDTO>( entityList.size() );
        for ( Button button : entityList ) {
            list.add( toDTO( button ) );
        }

        return list;
    }

    @Override
    public Page<Button> toEntity(Page<ButtonDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Button> page1 = new Page<Button>();

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
    public Page<ButtonDTO> toDTO(Page<Button> page) {
        if ( page == null ) {
            return null;
        }

        Page<ButtonDTO> page1 = new Page<ButtonDTO>();

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

    private String dtoCreateById(ButtonDTO buttonDTO) {
        if ( buttonDTO == null ) {
            return null;
        }
        UserDTO createBy = buttonDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(ButtonDTO buttonDTO) {
        if ( buttonDTO == null ) {
            return null;
        }
        UserDTO updateBy = buttonDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO buttonToUserDTO(Button button) {
        if ( button == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( button.getCreateById() );

        return userDTO;
    }

    protected UserDTO buttonToUserDTO1(Button button) {
        if ( button == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( button.getUpdateById() );

        return userDTO;
    }
}
