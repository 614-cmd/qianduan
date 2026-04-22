package com.jeeplus.test.shop.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.core.dto.DragNode;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.shop.domain.TestCategory;
import com.jeeplus.test.shop.service.dto.TestCategoryDTO;
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
public class TestCategoryWrapperImpl implements TestCategoryWrapper {

    @Override
    public List<TestCategory> toEntity(List<TestCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestCategory> list = new ArrayList<TestCategory>( dtoList.size() );
        for ( TestCategoryDTO testCategoryDTO : dtoList ) {
            list.add( toEntity( testCategoryDTO ) );
        }

        return list;
    }

    @Override
    public List<TestCategoryDTO> toDTO(List<TestCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestCategoryDTO> list = new ArrayList<TestCategoryDTO>( entityList.size() );
        for ( TestCategory testCategory : entityList ) {
            list.add( toDTO( testCategory ) );
        }

        return list;
    }

    @Override
    public Page<TestCategory> toEntity(Page<TestCategoryDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCategory> page1 = new Page<TestCategory>();

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
    public Page<TestCategoryDTO> toDTO(Page<TestCategory> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestCategoryDTO> page1 = new Page<TestCategoryDTO>();

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
    public TestCategory toEntity(TestCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestCategory testCategory = new TestCategory();

        testCategory.setParentId( dtoParentId( dto ) );
        testCategory.setCreateById( dtoCreateById( dto ) );
        testCategory.setUpdateById( dtoUpdateById( dto ) );
        testCategory.setId( dto.getId() );
        testCategory.setCreateTime( dto.getCreateTime() );
        testCategory.setUpdateTime( dto.getUpdateTime() );
        testCategory.setDelFlag( dto.getDelFlag() );
        testCategory.setParentIds( dto.getParentIds() );
        testCategory.setSort( dto.getSort() );
        testCategory.setChildren( toEntity( dto.getChildren() ) );
        testCategory.setName( dto.getName() );
        testCategory.setRemarks( dto.getRemarks() );

        return testCategory;
    }

    @Override
    public TestCategoryDTO toDTO(TestCategory entity) {
        if ( entity == null ) {
            return null;
        }

        TestCategoryDTO testCategoryDTO = new TestCategoryDTO();

        testCategoryDTO.setParent( testCategoryToTestCategoryDTO( entity ) );
        testCategoryDTO.setCreateBy( testCategoryToUserDTO( entity ) );
        testCategoryDTO.setUpdateBy( testCategoryToUserDTO1( entity ) );
        testCategoryDTO.setId( entity.getId() );
        testCategoryDTO.setCreateTime( entity.getCreateTime() );
        testCategoryDTO.setUpdateTime( entity.getUpdateTime() );
        testCategoryDTO.setDelFlag( entity.getDelFlag() );
        testCategoryDTO.setChildren( toDTO( entity.getChildren() ) );
        testCategoryDTO.setParentIds( entity.getParentIds() );
        testCategoryDTO.setSort( entity.getSort() );
        testCategoryDTO.setName( entity.getName() );
        testCategoryDTO.setRemarks( entity.getRemarks() );

        return testCategoryDTO;
    }

    @Override
    public TestCategory copyEntity(TestCategory entity) {
        if ( entity == null ) {
            return null;
        }

        TestCategory testCategory = new TestCategory();

        testCategory.setId( entity.getId() );
        testCategory.setCreateTime( entity.getCreateTime() );
        testCategory.setCreateById( entity.getCreateById() );
        testCategory.setUpdateTime( entity.getUpdateTime() );
        testCategory.setUpdateById( entity.getUpdateById() );
        testCategory.setDelFlag( entity.getDelFlag() );
        testCategory.setParentId( entity.getParentId() );
        testCategory.setParentIds( entity.getParentIds() );
        testCategory.setSort( entity.getSort() );
        List<TestCategory> list = entity.getChildren();
        if ( list != null ) {
            testCategory.setChildren( new ArrayList<TestCategory>( list ) );
        }
        testCategory.setName( entity.getName() );
        testCategory.setRemarks( entity.getRemarks() );

        return testCategory;
    }

    @Override
    public TestCategoryDTO copyDTO(TestCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestCategoryDTO testCategoryDTO = new TestCategoryDTO();

        testCategoryDTO.setId( dto.getId() );
        testCategoryDTO.setCreateTime( dto.getCreateTime() );
        testCategoryDTO.setCreateBy( dto.getCreateBy() );
        testCategoryDTO.setUpdateTime( dto.getUpdateTime() );
        testCategoryDTO.setUpdateBy( dto.getUpdateBy() );
        testCategoryDTO.setDelFlag( dto.getDelFlag() );
        testCategoryDTO.setParent( copyDTO( dto.getParent() ) );
        List<TestCategoryDTO> list = dto.getChildren();
        if ( list != null ) {
            testCategoryDTO.setChildren( new ArrayList<TestCategoryDTO>( list ) );
        }
        testCategoryDTO.setParentIds( dto.getParentIds() );
        testCategoryDTO.setSort( dto.getSort() );
        testCategoryDTO.setName( dto.getName() );
        testCategoryDTO.setRemarks( dto.getRemarks() );

        return testCategoryDTO;
    }

    @Override
    public DragNode<TestCategory> toEntity(DragNode<TestCategoryDTO> dDragDTO) {
        if ( dDragDTO == null ) {
            return null;
        }

        DragNode<TestCategory> dragNode = new DragNode<TestCategory>();

        dragNode.setDraggingNode( toEntity( dDragDTO.getDraggingNode() ) );
        dragNode.setDropNode( toEntity( dDragDTO.getDropNode() ) );
        dragNode.setDropType( dDragDTO.getDropType() );

        return dragNode;
    }

    private String dtoParentId(TestCategoryDTO testCategoryDTO) {
        if ( testCategoryDTO == null ) {
            return null;
        }
        TestCategoryDTO parent = testCategoryDTO.getParent();
        if ( parent == null ) {
            return null;
        }
        String id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestCategoryDTO testCategoryDTO) {
        if ( testCategoryDTO == null ) {
            return null;
        }
        UserDTO createBy = testCategoryDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestCategoryDTO testCategoryDTO) {
        if ( testCategoryDTO == null ) {
            return null;
        }
        UserDTO updateBy = testCategoryDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestCategoryDTO testCategoryToTestCategoryDTO(TestCategory testCategory) {
        if ( testCategory == null ) {
            return null;
        }

        TestCategoryDTO testCategoryDTO = new TestCategoryDTO();

        testCategoryDTO.setId( testCategory.getParentId() );

        return testCategoryDTO;
    }

    protected UserDTO testCategoryToUserDTO(TestCategory testCategory) {
        if ( testCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCategory.getCreateById() );

        return userDTO;
    }

    protected UserDTO testCategoryToUserDTO1(TestCategory testCategory) {
        if ( testCategory == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testCategory.getUpdateById() );

        return userDTO;
    }
}
