package com.jeeplus.test.note.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.test.note.domain.TestNote;
import com.jeeplus.test.note.service.dto.TestNoteDTO;
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
public class TestNoteWrapperImpl implements TestNoteWrapper {

    @Override
    public TestNote toEntity(TestNoteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TestNote testNote = new TestNote();

        testNote.setCreateById( dtoCreateById( dto ) );
        testNote.setUpdateById( dtoUpdateById( dto ) );
        testNote.setId( dto.getId() );
        testNote.setCreateTime( dto.getCreateTime() );
        testNote.setUpdateTime( dto.getUpdateTime() );
        testNote.setDelFlag( dto.getDelFlag() );
        testNote.setTitle( dto.getTitle() );
        testNote.setContents( dto.getContents() );
        testNote.setRemarks( dto.getRemarks() );

        return testNote;
    }

    @Override
    public TestNoteDTO toDTO(TestNote entity) {
        if ( entity == null ) {
            return null;
        }

        TestNoteDTO testNoteDTO = new TestNoteDTO();

        testNoteDTO.setCreateBy( testNoteToUserDTO( entity ) );
        testNoteDTO.setUpdateBy( testNoteToUserDTO1( entity ) );
        testNoteDTO.setId( entity.getId() );
        testNoteDTO.setCreateTime( entity.getCreateTime() );
        testNoteDTO.setUpdateTime( entity.getUpdateTime() );
        testNoteDTO.setDelFlag( entity.getDelFlag() );
        testNoteDTO.setTitle( entity.getTitle() );
        testNoteDTO.setContents( entity.getContents() );
        testNoteDTO.setRemarks( entity.getRemarks() );

        return testNoteDTO;
    }

    @Override
    public List<TestNote> toEntity(List<TestNoteDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TestNote> list = new ArrayList<TestNote>( dtoList.size() );
        for ( TestNoteDTO testNoteDTO : dtoList ) {
            list.add( toEntity( testNoteDTO ) );
        }

        return list;
    }

    @Override
    public List<TestNoteDTO> toDTO(List<TestNote> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TestNoteDTO> list = new ArrayList<TestNoteDTO>( entityList.size() );
        for ( TestNote testNote : entityList ) {
            list.add( toDTO( testNote ) );
        }

        return list;
    }

    @Override
    public Page<TestNote> toEntity(Page<TestNoteDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestNote> page1 = new Page<TestNote>();

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
    public Page<TestNoteDTO> toDTO(Page<TestNote> page) {
        if ( page == null ) {
            return null;
        }

        Page<TestNoteDTO> page1 = new Page<TestNoteDTO>();

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

    private String dtoCreateById(TestNoteDTO testNoteDTO) {
        if ( testNoteDTO == null ) {
            return null;
        }
        UserDTO createBy = testNoteDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(TestNoteDTO testNoteDTO) {
        if ( testNoteDTO == null ) {
            return null;
        }
        UserDTO updateBy = testNoteDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO testNoteToUserDTO(TestNote testNote) {
        if ( testNote == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testNote.getCreateById() );

        return userDTO;
    }

    protected UserDTO testNoteToUserDTO1(TestNote testNote) {
        if ( testNote == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( testNote.getUpdateById() );

        return userDTO;
    }
}
