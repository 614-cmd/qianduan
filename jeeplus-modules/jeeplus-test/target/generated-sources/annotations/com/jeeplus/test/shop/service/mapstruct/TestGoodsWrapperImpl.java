package com.jeeplus.test.shop.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.shop.domain.TestGoods;
import com.jeeplus.test.shop.service.dto.TestCategoryDTO;
import com.jeeplus.test.shop.service.dto.TestGoodsDTO;
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
public class TestGoodsWrapperImpl implements TestGoodsWrapper {

    @Override
    public List<TestGoods> toEntity(List<TestGoodsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestGoods> list = new ArrayList<TestGoods>( dtoList.size() );
        for ( TestGoodsDTO testGoodsDTO : dtoList ) {
            list.add( toEntity( testGoodsDTO ) );
        }

        return list;
    }

    @Override
    public List<TestGoodsDTO> toDTO(List<TestGoods> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestGoodsDTO> list = new ArrayList<TestGoodsDTO>( entityList.size() );
        for ( TestGoods testGoods : entityList ) {
            list.add( toDTO( testGoods ) );
        }

        return list;
    }

    @Override
    public Page<TestGoods> toEntity(Page<TestGoodsDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestGoods> page1 = new Page<TestGoods>();

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
    public Page<TestGoodsDTO> toDTO(Page<TestGoods> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestGoodsDTO> page1 = new Page<TestGoodsDTO>();

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
    public TestGoods toEntity(TestGoodsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestGoods testGoods = new TestGoods();

        testGoods.setCategoryId( dtoCategoryId( dto ) );
        testGoods.setCreateById( dtoCreateById( dto ) );
        testGoods.setUpdateById( dtoUpdateById( dto ) );
        testGoods.setId( dto.getId() );
        testGoods.setCreateTime( dto.getCreateTime() );
        testGoods.setUpdateTime( dto.getUpdateTime() );
        testGoods.setDelFlag( dto.getDelFlag() );
        testGoods.setName( dto.getName() );
        testGoods.setPrice( dto.getPrice() );
        testGoods.setCreateDate( dto.getCreateDate() );
        testGoods.setUpdateDate( dto.getUpdateDate() );
        testGoods.setRemarks( dto.getRemarks() );

        return testGoods;
    }

    @Override
    public TestGoodsDTO toDTO(TestGoods entity) {
        if ( entity == null ) {
            return null;
        }

        TestGoodsDTO testGoodsDTO = new TestGoodsDTO();

        testGoodsDTO.setCategory( testGoodsToTestCategoryDTO( entity ) );
        testGoodsDTO.setCreateBy( testGoodsToUserDTO( entity ) );
        testGoodsDTO.setUpdateBy( testGoodsToUserDTO1( entity ) );
        testGoodsDTO.setId( entity.getId() );
        testGoodsDTO.setCreateTime( entity.getCreateTime() );
        testGoodsDTO.setUpdateTime( entity.getUpdateTime() );
        testGoodsDTO.setDelFlag( entity.getDelFlag() );
        testGoodsDTO.setName( entity.getName() );
        testGoodsDTO.setPrice( entity.getPrice() );
        testGoodsDTO.setCreateDate( entity.getCreateDate() );
        testGoodsDTO.setUpdateDate( entity.getUpdateDate() );
        testGoodsDTO.setRemarks( entity.getRemarks() );

        return testGoodsDTO;
    }

    private String dtoCategoryId(TestGoodsDTO testGoodsDTO) {
        if ( testGoodsDTO == null ) {
            return null;
        }
        TestCategoryDTO category = testGoodsDTO.getCategory();
        if ( category == null ) {
            return null;
        }
        String id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(TestGoodsDTO testGoodsDTO) {
        if ( testGoodsDTO == null ) {
            return null;
        }
        UserDTO createBy = testGoodsDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestGoodsDTO testGoodsDTO) {
        if ( testGoodsDTO == null ) {
            return null;
        }
        UserDTO updateBy = testGoodsDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected TestCategoryDTO testGoodsToTestCategoryDTO(TestGoods testGoods) {
        if ( testGoods == null ) {
            return null;
        }

        TestCategoryDTO testCategoryDTO = new TestCategoryDTO();

        testCategoryDTO.setId( testGoods.getCategoryId() );

        return testCategoryDTO;
    }

    protected UserDTO testGoodsToUserDTO(TestGoods testGoods) {
        if ( testGoods == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testGoods.getCreateById() );

        return userDTO;
    }

    protected UserDTO testGoodsToUserDTO1(TestGoods testGoods) {
        if ( testGoods == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testGoods.getUpdateById() );

        return userDTO;
    }
}
