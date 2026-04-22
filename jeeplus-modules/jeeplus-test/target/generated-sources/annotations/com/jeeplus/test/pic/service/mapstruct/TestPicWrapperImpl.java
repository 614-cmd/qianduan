package com.jeeplus.test.pic.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.pic.domain.TestPic;
import com.jeeplus.test.pic.service.dto.TestPicDTO;
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
public class TestPicWrapperImpl implements TestPicWrapper {

    @Override
    public TestPic toEntity(TestPicDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestPic testPic = new TestPic();

        testPic.setCreateById( dtoCreateById( dto ) );
        testPic.setUpdateById( dtoUpdateById( dto ) );
        testPic.setId( dto.getId() );
        testPic.setCreateTime( dto.getCreateTime() );
        testPic.setUpdateTime( dto.getUpdateTime() );
        testPic.setDelFlag( dto.getDelFlag() );
        testPic.setTitle( dto.getTitle() );
        testPic.setPic( dto.getPic() );
        testPic.setFile( dto.getFile() );
        testPic.setRemarks( dto.getRemarks() );

        return testPic;
    }

    @Override
    public TestPicDTO toDTO(TestPic entity) {
        if ( entity == null ) {
            return null;
        }

        TestPicDTO testPicDTO = new TestPicDTO();

        testPicDTO.setCreateBy( testPicToUserDTO( entity ) );
        testPicDTO.setUpdateBy( testPicToUserDTO1( entity ) );
        testPicDTO.setId( entity.getId() );
        testPicDTO.setCreateTime( entity.getCreateTime() );
        testPicDTO.setUpdateTime( entity.getUpdateTime() );
        testPicDTO.setDelFlag( entity.getDelFlag() );
        testPicDTO.setTitle( entity.getTitle() );
        testPicDTO.setPic( entity.getPic() );
        testPicDTO.setFile( entity.getFile() );
        testPicDTO.setRemarks( entity.getRemarks() );

        return testPicDTO;
    }

    @Override
    public List<TestPic> toEntity(List<TestPicDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestPic> list = new ArrayList<TestPic>( dtoList.size() );
        for ( TestPicDTO testPicDTO : dtoList ) {
            list.add( toEntity( testPicDTO ) );
        }

        return list;
    }

    @Override
    public List<TestPicDTO> toDTO(List<TestPic> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestPicDTO> list = new ArrayList<TestPicDTO>( entityList.size() );
        for ( TestPic testPic : entityList ) {
            list.add( toDTO( testPic ) );
        }

        return list;
    }

    @Override
    public Page<TestPic> toEntity(Page<TestPicDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestPic> page1 = new Page<TestPic>();

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
    public Page<TestPicDTO> toDTO(Page<TestPic> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestPicDTO> page1 = new Page<TestPicDTO>();

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

    private String dtoCreateById(TestPicDTO testPicDTO) {
        if ( testPicDTO == null ) {
            return null;
        }
        UserDTO createBy = testPicDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestPicDTO testPicDTO) {
        if ( testPicDTO == null ) {
            return null;
        }
        UserDTO updateBy = testPicDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO testPicToUserDTO(TestPic testPic) {
        if ( testPic == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testPic.getCreateById() );

        return userDTO;
    }

    protected UserDTO testPicToUserDTO1(TestPic testPic) {
        if ( testPic == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testPic.getUpdateById() );

        return userDTO;
    }
}
