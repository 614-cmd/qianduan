package com.jeeplus.test.tree.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.tree.domain.TestTree;
import com.jeeplus.test.tree.service.dto.TestTreeDTO;
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
public class TestTreeWrapperImpl implements TestTreeWrapper {

    @Override
    public List<TestTree> toEntity(List<TestTreeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestTree> list = new ArrayList<TestTree>( dtoList.size() );
        for ( TestTreeDTO testTreeDTO : dtoList ) {
            list.add( toEntity( testTreeDTO ) );
        }

        return list;
    }

    @Override
    public List<TestTreeDTO> toDTO(List<TestTree> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestTreeDTO> list = new ArrayList<TestTreeDTO>( entityList.size() );
        for ( TestTree testTree : entityList ) {
            list.add( toDTO( testTree ) );
        }

        return list;
    }

    @Override
    public Page<TestTree> toEntity(Page<TestTreeDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestTree> page1 = new Page<TestTree>();

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
    public Page<TestTreeDTO> toDTO(Page<TestTree> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestTreeDTO> page1 = new Page<TestTreeDTO>();

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
    public TestTree toEntity(TestTreeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestTree testTree = new TestTree();

        testTree.setParentId( dtoParentId( dto ) );
        testTree.setCreateById( dtoCreateById( dto ) );
        testTree.setUpdateById( dtoUpdateById( dto ) );
        testTree.setId( dto.getId() );
        testTree.setCreateTime( dto.getCreateTime() );
        testTree.setUpdateTime( dto.getUpdateTime() );
        testTree.setDelFlag( dto.getDelFlag() );
        testTree.setParentIds( dto.getParentIds() );
        testTree.setSort( dto.getSort() );
        testTree.setChildren( toEntity( dto.getChildren() ) );
        testTree.setName( dto.getName() );
        testTree.setRemarks( dto.getRemarks() );

        return testTree;
    }

    @Override
    public TestTreeDTO toDTO(TestTree entity) {
        if ( entity == null ) {
            return null;
        }

        TestTreeDTO testTreeDTO = new TestTreeDTO();

        testTreeDTO.setParent( testTreeToTestTreeDTO( entity ) );
        testTreeDTO.setCreateBy( testTreeToUserDTO( entity ) );
        testTreeDTO.setUpdateBy( testTreeToUserDTO1( entity ) );
        testTreeDTO.setId( entity.getId() );
        testTreeDTO.setCreateTime( entity.getCreateTime() );
        testTreeDTO.setUpdateTime( entity.getUpdateTime() );
        testTreeDTO.setDelFlag( entity.getDelFlag() );
        testTreeDTO.setChildren( toDTO( entity.getChildren() ) );
        testTreeDTO.setParentIds( entity.getParentIds() );
        testTreeDTO.setSort( entity.getSort() );
        testTreeDTO.setName( entity.getName() );
        testTreeDTO.setRemarks( entity.getRemarks() );

        return testTreeDTO;
    }

    @Override
    public TestTree copyEntity(TestTree entity) {
        if ( entity == null ) {
            return null;
        }

        TestTree testTree = new TestTree();

        testTree.setId( entity.getId() );
        testTree.setCreateTime( entity.getCreateTime() );
        testTree.setCreateById( entity.getCreateById() );
        testTree.setUpdateTime( entity.getUpdateTime() );
        testTree.setUpdateById( entity.getUpdateById() );
        testTree.setDelFlag( entity.getDelFlag() );
        testTree.setParentId( entity.getParentId() );
        testTree.setParentIds( entity.getParentIds() );
        testTree.setSort( entity.getSort() );
        List<TestTree> list = entity.getChildren();
        if ( list != null ) {
            testTree.setChildren( new ArrayList<TestTree>( list ) );
        }
        testTree.setName( entity.getName() );
        testTree.setRemarks( entity.getRemarks() );

        return testTree;
    }

    @Override
    public TestTreeDTO copyDTO(TestTreeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestTreeDTO testTreeDTO = new TestTreeDTO();

        testTreeDTO.setId( dto.getId() );
        testTreeDTO.setCreateTime( dto.getCreateTime() );
        testTreeDTO.setCreateBy( dto.getCreateBy() );
        testTreeDTO.setUpdateTime( dto.getUpdateTime() );
        testTreeDTO.setUpdateBy( dto.getUpdateBy() );
        testTreeDTO.setDelFlag( dto.getDelFlag() );
        testTreeDTO.setParent( copyDTO( dto.getParent() ) );
        List<TestTreeDTO> list = dto.getChildren();
        if ( list != null ) {
            testTreeDTO.setChildren( new ArrayList<TestTreeDTO>( list ) );
        }
        testTreeDTO.setParentIds( dto.getParentIds() );
        testTreeDTO.setSort( dto.getSort() );
        testTreeDTO.setName( dto.getName() );
        testTreeDTO.setRemarks( dto.getRemarks() );

        return testTreeDTO;
    }

    @Override
    public DragNode<TestTree> toEntity(DragNode<TestTreeDTO> dDragDTO) {
        if ( dDragDTO == null ) {
            return null;
        }

        DragNode<TestTree> dragNode = new DragNode<TestTree>();

        dragNode.setDraggingNode( toEntity( dDragDTO.getDraggingNode() ) );
        dragNode.setDropNode( toEntity( dDragDTO.getDropNode() ) );
        dragNode.setDropType( dDragDTO.getDropType() );

        return dragNode;
    }

    private String dtoParentId(TestTreeDTO testTreeDTO) {
        if ( testTreeDTO == null ) {
            return null;
        }
        TestTreeDTO parent = testTreeDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestTreeDTO testTreeDTO) {
        if ( testTreeDTO == null ) {
            return null;
        }
        UserDTO createBy = testTreeDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestTreeDTO testTreeDTO) {
        if ( testTreeDTO == null ) {
            return null;
        }
        UserDTO updateBy = testTreeDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestTreeDTO testTreeToTestTreeDTO(TestTree testTree) {
        if ( testTree == null ) {
            return null;
        }

        TestTreeDTO testTreeDTO = new TestTreeDTO();

        testTreeDTO.setId( testTree.getParentId() );

        return testTreeDTO;
    }

    protected UserDTO testTreeToUserDTO(TestTree testTree) {
        if ( testTree == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testTree.getCreateById() );

        return userDTO;
    }

    protected UserDTO testTreeToUserDTO1(TestTree testTree) {
        if ( testTree == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testTree.getUpdateById() );

        return userDTO;
    }
}
