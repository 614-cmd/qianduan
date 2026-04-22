package com.jeeplus.test.treetable.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.treetable.domain.TestCarKind;
import com.jeeplus.test.treetable.service.dto.TestCarDTO;
import com.jeeplus.test.treetable.service.dto.TestCarKindDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:43+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class TestCarKindWrapperImpl implements TestCarKindWrapper {

    @Override
    public List<TestCarKind> toEntity(List<TestCarKindDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestCarKind> list = new ArrayList<TestCarKind>( dtoList.size() );
        for ( TestCarKindDTO testCarKindDTO : dtoList ) {
            list.add( toEntity( testCarKindDTO ) );
        }

        return list;
    }

    @Override
    public List<TestCarKindDTO> toDTO(List<TestCarKind> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestCarKindDTO> list = new ArrayList<TestCarKindDTO>( entityList.size() );
        for ( TestCarKind testCarKind : entityList ) {
            list.add( toDTO( testCarKind ) );
        }

        return list;
    }

    @Override
    public Page<TestCarKind> toEntity(Page<TestCarKindDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCarKind> page1 = new Page<TestCarKind>();

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
    public Page<TestCarKindDTO> toDTO(Page<TestCarKind> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCarKindDTO> page1 = new Page<TestCarKindDTO>();

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
    public TestCarKind toEntity(TestCarKindDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestCarKind testCarKind = new TestCarKind();

        testCarKind.setParentId( dtoParentId( dto ) );
        testCarKind.setCreateById( dtoCreateById( dto ) );
        testCarKind.setUpdateById( dtoUpdateById( dto ) );
        testCarKind.setId( dto.getId() );
        testCarKind.setCreateTime( dto.getCreateTime() );
        testCarKind.setUpdateTime( dto.getUpdateTime() );
        testCarKind.setDelFlag( dto.getDelFlag() );
        testCarKind.setParentIds( dto.getParentIds() );
        testCarKind.setSort( dto.getSort() );
        testCarKind.setChildren( toEntity( dto.getChildren() ) );
        testCarKind.setName( dto.getName() );
        testCarKind.setRemarks( dto.getRemarks() );

        return testCarKind;
    }

    @Override
    public TestCarKindDTO toDTO(TestCarKind entity) {
        if ( entity == null ) {
            return null;
        }

        TestCarKindDTO testCarKindDTO = new TestCarKindDTO();

        testCarKindDTO.setParent( testCarKindToTestCarKindDTO( entity ) );
        testCarKindDTO.setCreateBy( testCarKindToUserDTO( entity ) );
        testCarKindDTO.setUpdateBy( testCarKindToUserDTO1( entity ) );
        testCarKindDTO.setId( entity.getId() );
        testCarKindDTO.setCreateTime( entity.getCreateTime() );
        testCarKindDTO.setUpdateTime( entity.getUpdateTime() );
        testCarKindDTO.setDelFlag( entity.getDelFlag() );
        testCarKindDTO.setChildren( toDTO( entity.getChildren() ) );
        testCarKindDTO.setParentIds( entity.getParentIds() );
        testCarKindDTO.setSort( entity.getSort() );
        testCarKindDTO.setName( entity.getName() );
        testCarKindDTO.setRemarks( entity.getRemarks() );

        return testCarKindDTO;
    }

    @Override
    public TestCarKind copyEntity(TestCarKind entity) {
        if ( entity == null ) {
            return null;
        }

        TestCarKind testCarKind = new TestCarKind();

        testCarKind.setId( entity.getId() );
        testCarKind.setCreateTime( entity.getCreateTime() );
        testCarKind.setCreateById( entity.getCreateById() );
        testCarKind.setUpdateTime( entity.getUpdateTime() );
        testCarKind.setUpdateById( entity.getUpdateById() );
        testCarKind.setDelFlag( entity.getDelFlag() );
        testCarKind.setParentId( entity.getParentId() );
        testCarKind.setParentIds( entity.getParentIds() );
        testCarKind.setSort( entity.getSort() );
        List<TestCarKind> list = entity.getChildren();
        if ( list != null ) {
            testCarKind.setChildren( new ArrayList<TestCarKind>( list ) );
        }
        testCarKind.setName( entity.getName() );
        testCarKind.setRemarks( entity.getRemarks() );

        return testCarKind;
    }

    @Override
    public TestCarKindDTO copyDTO(TestCarKindDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestCarKindDTO testCarKindDTO = new TestCarKindDTO();

        testCarKindDTO.setId( dto.getId() );
        testCarKindDTO.setCreateTime( dto.getCreateTime() );
        testCarKindDTO.setCreateBy( dto.getCreateBy() );
        testCarKindDTO.setUpdateTime( dto.getUpdateTime() );
        testCarKindDTO.setUpdateBy( dto.getUpdateBy() );
        testCarKindDTO.setDelFlag( dto.getDelFlag() );
        testCarKindDTO.setParent( copyDTO( dto.getParent() ) );
        List<TestCarKindDTO> list = dto.getChildren();
        if ( list != null ) {
            testCarKindDTO.setChildren( new ArrayList<TestCarKindDTO>( list ) );
        }
        testCarKindDTO.setParentIds( dto.getParentIds() );
        testCarKindDTO.setSort( dto.getSort() );
        testCarKindDTO.setName( dto.getName() );
        testCarKindDTO.setRemarks( dto.getRemarks() );
        List<TestCarDTO> list1 = dto.getTestCarDTOList();
        if ( list1 != null ) {
            testCarKindDTO.setTestCarDTOList( new ArrayList<TestCarDTO>( list1 ) );
        }

        return testCarKindDTO;
    }

    @Override
    public DragNode<TestCarKind> toEntity(DragNode<TestCarKindDTO> dDragDTO) {
        if ( dDragDTO == null ) {
            return null;
        }

        DragNode<TestCarKind> dragNode = new DragNode<TestCarKind>();

        dragNode.setDraggingNode( toEntity( dDragDTO.getDraggingNode() ) );
        dragNode.setDropNode( toEntity( dDragDTO.getDropNode() ) );
        dragNode.setDropType( dDragDTO.getDropType() );

        return dragNode;
    }

    private String dtoParentId(TestCarKindDTO testCarKindDTO) {
        if ( testCarKindDTO == null ) {
            return null;
        }
        TestCarKindDTO parent = testCarKindDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestCarKindDTO testCarKindDTO) {
        if ( testCarKindDTO == null ) {
            return null;
        }
        UserDTO createBy = testCarKindDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestCarKindDTO testCarKindDTO) {
        if ( testCarKindDTO == null ) {
            return null;
        }
        UserDTO updateBy = testCarKindDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestCarKindDTO testCarKindToTestCarKindDTO(TestCarKind testCarKind) {
        if ( testCarKind == null ) {
            return null;
        }

        TestCarKindDTO testCarKindDTO = new TestCarKindDTO();

        testCarKindDTO.setId( testCarKind.getParentId() );

        return testCarKindDTO;
    }

    protected UserDTO testCarKindToUserDTO(TestCarKind testCarKind) {
        if ( testCarKind == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCarKind.getCreateById() );

        return userDTO;
    }

    protected UserDTO testCarKindToUserDTO1(TestCarKind testCarKind) {
        if ( testCarKind == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCarKind.getUpdateById() );

        return userDTO;
    }
}
