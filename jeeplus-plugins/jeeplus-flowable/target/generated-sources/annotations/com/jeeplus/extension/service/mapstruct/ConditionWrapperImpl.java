package com.jeeplus.extension.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.Condition;
import com.jeeplus.extension.service.dto.ConditionDTO;
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
public class ConditionWrapperImpl implements ConditionWrapper {

    @Override
    public Condition toEntity(ConditionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Condition condition = new Condition();

        condition.setCreateById( dtoCreateById( dto ) );
        condition.setUpdateById( dtoUpdateById( dto ) );
        condition.setId( dto.getId() );
        condition.setCreateTime( dto.getCreateTime() );
        condition.setUpdateTime( dto.getUpdateTime() );
        condition.setDelFlag( dto.getDelFlag() );
        condition.setName( dto.getName() );
        condition.setExpression( dto.getExpression() );
        condition.setRemarks( dto.getRemarks() );

        return condition;
    }

    @Override
    public ConditionDTO toDTO(Condition entity) {
        if ( entity == null ) {
            return null;
        }

        ConditionDTO conditionDTO = new ConditionDTO();

        conditionDTO.setCreateBy( conditionToUserDTO( entity ) );
        conditionDTO.setUpdateBy( conditionToUserDTO1( entity ) );
        conditionDTO.setId( entity.getId() );
        conditionDTO.setCreateTime( entity.getCreateTime() );
        conditionDTO.setUpdateTime( entity.getUpdateTime() );
        conditionDTO.setDelFlag( entity.getDelFlag() );
        conditionDTO.setName( entity.getName() );
        conditionDTO.setExpression( entity.getExpression() );
        conditionDTO.setRemarks( entity.getRemarks() );

        return conditionDTO;
    }

    @Override
    public List<Condition> toEntity(List<ConditionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Condition> list = new ArrayList<Condition>( dtoList.size() );
        for ( ConditionDTO conditionDTO : dtoList ) {
            list.add( toEntity( conditionDTO ) );
        }

        return list;
    }

    @Override
    public List<ConditionDTO> toDTO(List<Condition> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ConditionDTO> list = new ArrayList<ConditionDTO>( entityList.size() );
        for ( Condition condition : entityList ) {
            list.add( toDTO( condition ) );
        }

        return list;
    }

    @Override
    public Page<Condition> toEntity(Page<ConditionDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Condition> page1 = new Page<Condition>();

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
    public Page<ConditionDTO> toDTO(Page<Condition> page) {
        if ( page == null ) {
            return null;
        }

        Page<ConditionDTO> page1 = new Page<ConditionDTO>();

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

    private String dtoCreateById(ConditionDTO conditionDTO) {
        if ( conditionDTO == null ) {
            return null;
        }
        UserDTO createBy = conditionDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(ConditionDTO conditionDTO) {
        if ( conditionDTO == null ) {
            return null;
        }
        UserDTO updateBy = conditionDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO conditionToUserDTO(Condition condition) {
        if ( condition == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( condition.getCreateById() );

        return userDTO;
    }

    protected UserDTO conditionToUserDTO1(Condition condition) {
        if ( condition == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( condition.getUpdateById() );

        return userDTO;
    }
}
